package com.xingyunfu.ebank.service.transfer;

import com.xingyunfu.ebank.constant.FlowConstants;
import com.xingyunfu.ebank.constant.TransferConstant;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderReqDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderRespDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferMergeMapper;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import com.xingyunfu.ebank.service.flow.FlowMangerService;
import com.xingyunfu.ebank.service.paycenter.ApplyTransferService;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisLock;
import com.xingyunfu.ebank.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.xingyunfu.ebank.constant.FlowConstants.flowType_in;
import static com.xingyunfu.ebank.constant.FlowConstants.flowType_out;
import static com.xingyunfu.ebank.constant.InnerAccountConstant.pay_success_code;
import static com.xingyunfu.ebank.constant.InnerAccountConstant.system_user_no;

/**
 * 合并转账管理
 */
@Slf4j
@Service
public class TransferMergeMangerService {
    @Autowired private EbankTransferMergeMapper ebankTransferMergeMapper;
    @Autowired private TransferMangerService transferMangerService;
    @Autowired private RedisTemplate redisTemplate;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private ApplyTransferService applyTransferService;
    @Autowired private FlowMangerService flowMangerService;
    @Autowired private RabbitTemplate rabbitTemplate;

    /**
     * 创建合并转账申请 -- 针对普通用户
     * 申请支付成功直接将状态修改为支付成功，不需要等待回调
     * @param statTime 时间戳，单位秒, 操作startTime后的转账，startTime值为null，则表示不限制开始时间
     * @param endTime 时间戳，单位秒, 操作endTime前的转账
     * @param receiveAccountNo 转入账户
     */
    public void transferApply(String receiveAccountNo, Long statTime, Long endTime){
        log.info("Start transfer apply, receiveAccountNo: {}, startTime: {}, endTime: {}", receiveAccountNo, statTime, endTime);

        RedisLock redisLock = new RedisLock(redisTemplate, RedisKeyRules.transferRedisLock(receiveAccountNo));

        try {
            //合并订单号
            String ebankOrderNo = CommonUtil.ebankOrder();
            EbankAccountDTO receiveAccount = null;
            if(redisLock.lock()){
                //账户信息
                receiveAccount = accountMangerService.hardFindByAccountNo(receiveAccountNo);
                if(Objects.isNull(receiveAccount.getBankCardNo())){
                    log.warn("Account {} has no bank card info. Do not transfer apply!", receiveAccountNo);
                    return;
                }
                //锁单
                Integer lockNum = transferMangerService.lockOrder(ebankOrderNo, receiveAccountNo, statTime, endTime);
                log.info("Current account locked transfer number is {}", lockNum);
                if(lockNum.equals(0))  return;
            }
            //开始执行转帐
            this.startTransferApply(ebankOrderNo, receiveAccount, this.insert(ebankOrderNo, receiveAccount));
        } catch (Exception e) {
            if(e instanceof EBankException) {
                EBankException ce = (EBankException) e;
                log.error("error code: {}, error desc: {}", ce.getErrCode(), ce.getDesc());
            }
            log.error("transfer error: {}", e);
        } finally {
            redisLock.unlock();
        }
    }

    /**
     * 重试代付申请
     */
    public void transferApplyTry(EbankTransferMergeDTO transferMerge, EbankAccountDTO systemAccount){
        EbankAccountDTO receiveAccount = accountMangerService.hardFindByAccountNo(transferMerge.getReceiveAccountNo());

        String ebankOrderNo = transferMerge.getEbankOrderNo();
        BigDecimal amount = transferMerge.getAmount();
        TransferOrderRespDTO transferOrderResp;
        try {
            transferOrderResp = applyTransferService.applyTransfer(
                    new TransferOrderReqDTO(receiveAccount, payCenterConfig.getMerchantNo(), amount, ebankOrderNo));
            if(pay_success_code.equals(transferOrderResp.getResCode())){
                this.closeTransferApply(transferMerge, systemAccount, transferOrderResp.getVoucher());
            }
        } catch (Exception e) {
            log.error("transfer error: {}", e);
        }
    }

    /**
     * 关闭代付申请
     */
    public void closeTransferApply(EbankTransferMergeDTO transferMerge, EbankAccountDTO systemAccount, String voucher){
        String ebankOrderNo = transferMerge.getEbankOrderNo();

        EbankAccountDTO receiveAccount = accountMangerService.hardFindByAccountNo(transferMerge.getReceiveAccountNo());
        Integer changeNu = this.changeApplySuccessOrFailed(ebankOrderNo, voucher, true);   //关闭合并转帐
        transferMangerService.closeOrder(ebankOrderNo, true);           //关闭转帐
        flowMangerService.updateStatus(ebankOrderNo, true);             //关闭流水

        log.info("changeNu: {}", changeNu);
        if(changeNu>0) {
            //执行计算
            BigDecimal balance = transferMerge.getAmount();
            accountMangerService.operateBalance(systemAccount.getAccountNo(), balance, -1);
            accountMangerService.operateBalance(receiveAccount.getAccountNo(), balance, 1);

            //通知券集市
            this.noticeMarket(ebankOrderNo);
        }
    }

    /**
     * 创建合并转账
     */
    private EbankTransferMergeDTO insert(String ebankOrderNo, EbankAccountDTO receiveAccount){
        EbankTransferMergeDTO transferMerge = new EbankTransferMergeDTO(ebankOrderNo, receiveAccount);
        transferMerge.setAmount(transferMangerService.totalAmount(ebankOrderNo));

        transferMerge.setStatus(TransferConstant.status_applying);      //开始自动化处理

        if(transferMerge.getAmount().intValue() >= payCenterConfig.getCommonUserTransferUpper()){//超过一定金额，等待手工处理
            transferMerge.setStatus(TransferConstant.status_wait_handle);
            log.warn("Can not transfer, amount  {}", transferMerge.getAmount().intValue());
        }
        ebankTransferMergeMapper.insert(transferMerge);
        return transferMerge;
    }

    /**
     * 发起代付请求
     */
    private void startTransferApply(String ebankOrderNo, EbankAccountDTO receiveAccount, EbankTransferMergeDTO transferMerge) throws Exception {

        EbankAccountDTO systemAccount = accountMangerService.findByUserNo(system_user_no);

        BigDecimal amount = transferMerge.getAmount();
        if(!transferMerge.getStatus().equals(TransferConstant.status_applying)) {
            this.createHandleFlow(systemAccount, receiveAccount, amount, ebankOrderNo);
            return;
        }

        TransferOrderRespDTO transferOrderResp;
        try {
            transferOrderResp = applyTransferService.applyTransfer(
                    new TransferOrderReqDTO(receiveAccount, payCenterConfig.getMerchantNo(), amount, ebankOrderNo));
            log.info("transfer order resp: {}", transferOrderResp);
        }catch (Exception e){
            this.changeApplySuccessOrFailed(ebankOrderNo, null, false);
            this.createHandleFlow(systemAccount, receiveAccount, amount, ebankOrderNo);
            log.error("transfer error: {}", e);
            throw e;
        }

        Boolean success = pay_success_code.equals(transferOrderResp.getResCode());
        //申请成功失败后修改状态 -- 转账成功/申请失败
        this.changeApplySuccessOrFailed(ebankOrderNo, transferOrderResp.getVoucher(), success);
        transferMangerService.closeOrder(ebankOrderNo, pay_success_code.equals(transferOrderResp.getResCode()));

        //记录流水，流水状态改为支付成功/失败
        flowMangerService.add(systemAccount, transferOrderResp, flowType_out, ebankOrderNo, success);
        flowMangerService.add(receiveAccount, transferOrderResp, flowType_in, ebankOrderNo, success);

        if(success){
            //执行计算
            BigDecimal balance = new BigDecimal(transferOrderResp.getAmount()).divide(new BigDecimal(100));
            accountMangerService.operateBalance(systemAccount.getAccountNo(), balance, -1);
            accountMangerService.operateBalance(receiveAccount.getAccountNo(), balance, 1);
        }

        //通知券集市
        if(pay_success_code.equals(transferOrderResp.getResCode())){
            this.noticeMarket(ebankOrderNo);
        }
    }

    private Integer changeApplySuccessOrFailed(String ebankOrderNo, String voucher, Boolean success){
        EbankTransferMergeDTO transferMerge = new EbankTransferMergeDTO();
        transferMerge.setEbankOrderNo(ebankOrderNo);
        transferMerge.setClosed(success);       //如果支付成功，立即关闭
        transferMerge.setVoucher(voucher);
        transferMerge.setStatus(success?TransferConstant.status_success: TransferConstant.status_apply_failed);
        return ebankTransferMergeMapper.update(transferMerge);
    }

    private void createHandleFlow(EbankAccountDTO systemAccount, EbankAccountDTO receiveAccount, BigDecimal amount, String ebankOrderNo){
        //创建流水
        flowMangerService.add(receiveAccount, amount, FlowConstants.flowType_in, ebankOrderNo);
        flowMangerService.add(systemAccount, amount, FlowConstants.flowType_out, ebankOrderNo);
    }

    private void noticeMarket(String ebankOrderNo){
        String temp = "{\"orderNo\":\"%s\"}";
        transferMangerService.findOrderNoByEbankOrderNo(ebankOrderNo)
                .forEach(var -> rabbitTemplate.convertAndSend("Volume", "VolumeMarket_key", String.format(temp, var)));
    }
}
