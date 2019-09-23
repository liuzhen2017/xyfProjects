package com.xingyunfu.ebank.service.transfer;

import com.xingyunfu.ebank.constant.FlowConstants;
import com.xingyunfu.ebank.constant.TransferConstant;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeCreateDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergePageQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeResultDTO;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferMergeMapper;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import com.xingyunfu.ebank.service.flow.FlowMangerService;
import com.xingyunfu.ebank.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.system_user_no;

@Slf4j
@Service
public class TransferMergeOfflineService {

    @Autowired private EbankTransferMergeMapper ebankTransferMergeMapper;
    @Autowired private TransferMangerService transferMangerService;
    @Autowired private FlowMangerService flowMangerService;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private RabbitTemplate rabbitTemplate;

    /**
     * 创建合并转账
     */
    public void createMerge(TransferMergeCreateDTO create){
        List<EbankAccountDTO> accountNoList = transferMangerService.findAllNeedTransferAccount(
                create.getStartTime(), create.getEndTime(), create.getAccountType());

        EbankAccountDTO systemAccount = accountMangerService.findByUserNo(system_user_no);
        accountNoList.forEach(var -> this.createMerge(var, systemAccount, create.getStartTime(), create.getEndTime()));
    }

    /**
     * 关闭合并订单
     */
    public void closeMerge(List<String> ebankOrderNoList){
        EbankAccountDTO systemAccount = accountMangerService.findByUserNo(system_user_no);
        ebankOrderNoList.forEach(var -> this.closeMerge(var, systemAccount));
    }

    /**
     * 分页查询合并订单
     */
    public PageDTO<TransferMergeResultDTO> pageQuery(TransferMergePageQueryDTO pageQuery){
        Integer pageNo = pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize();

        pageQuery.setPageNo((pageNo-1) * pageSize);
        pageQuery.setPageSize(pageSize);

        List<EbankTransferMergeDTO> mergeList = ebankTransferMergeMapper.pageQuery(pageQuery);
        Map<String, EbankAccountDTO> accountMap = accountMangerService.listAccount(mergeList.stream()
                .map(EbankTransferMergeDTO::getReceiveAccountNo).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(EbankAccountDTO::getAccountNo, var-> var));

        return new PageDTO<>(pageNo, pageSize, ebankTransferMergeMapper.pageCount(pageQuery), mergeList.stream()
                .map(var -> new TransferMergeResultDTO(var, accountMap.get(var.getReceiveAccountNo())))
                .collect(Collectors.toList()));
    }

    /**
     * 查询所有合并订单
     */
    public List<TransferMergeResultDTO> allMerge(TransferMergeQueryDTO mergeQuery){
        List<EbankTransferMergeDTO> mergeList = ebankTransferMergeMapper.allMerge(mergeQuery);
        Map<String, EbankAccountDTO> accountMap = accountMangerService.listAccount(mergeList.stream()
                .map(EbankTransferMergeDTO::getReceiveAccountNo).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(EbankAccountDTO::getAccountNo, var-> var));

        return mergeList.stream().map(var -> new TransferMergeResultDTO(var, accountMap.get(var.getReceiveAccountNo())))
                .collect(Collectors.toList());
    }

    /**
     * 关闭单个订单
     */
    private void closeMerge(String ebankOrderNo, EbankAccountDTO systemAccount){

        EbankTransferMergeDTO transferMerge = ebankTransferMergeMapper.findByEbankOrderNo(ebankOrderNo);
        if(Objects.isNull(transferMerge)) return;   //合并订单不存在，直接结束
        if(this.update(ebankOrderNo)==0) return;    //未修改合并订单，直接结束

        flowMangerService.updateStatus(ebankOrderNo, true);

        BigDecimal amount = transferMerge.getAmount();
        accountMangerService.operateBalance(systemAccount.getAccountNo(), amount, -1);
        accountMangerService.operateBalance(transferMerge.getReceiveAccountNo(), amount, 1);

        //通知券集市
        String temp = "{\"orderNo\":\"%s\"}";
        transferMangerService.findOrderNoByEbankOrderNo(ebankOrderNo)
                .forEach(var -> rabbitTemplate.convertAndSend("Volume", "VolumeMarket_key", String.format(temp, var)));
    }

    /**
     * 关闭合并订单
     */
    private Integer update(String ebankOrderNo){
        EbankTransferMergeDTO merge = new EbankTransferMergeDTO();
        merge.setEbankOrderNo(ebankOrderNo);
        merge.setClosed(true);
        merge.setStatus(TransferConstant.status_handle_success);
        return ebankTransferMergeMapper.update(merge);
    }

    /**
     * 创建合并订单
     */
    private void createMerge(EbankAccountDTO receiveAccount, EbankAccountDTO systemAccount, Long startTime, Long endTime){
        String ebankOrderNo = CommonUtil.ebankOrder();
        receiveAccount = accountMangerService.hardFindByAccountNo(receiveAccount);
        String accountNo = receiveAccount.getAccountNo();
        if(Objects.isNull(receiveAccount)) {
            log.error("can not get receive  account. accountNo: {}", accountNo);
            return;
        }
        //锁单
        Integer locked = transferMangerService.lockOrder(ebankOrderNo, accountNo, startTime, endTime);
        log.info("receive account no {}, start time {}, end time {}, locked order number is {}",
                accountNo, startTime, endTime, locked);
        if(locked == 0) return;

        BigDecimal amount = transferMangerService.totalAmount(ebankOrderNo);
        //创建合并转账记录，状态待处理
        ebankTransferMergeMapper.insert(this.createMerge(receiveAccount, amount, ebankOrderNo));

        //创建流水
        flowMangerService.add(receiveAccount, amount, FlowConstants.flowType_in, ebankOrderNo);
        flowMangerService.add(systemAccount, amount, FlowConstants.flowType_out, ebankOrderNo);
    }

    private EbankTransferMergeDTO createMerge(EbankAccountDTO receiveAccount, BigDecimal amount, String ebankOrderNo){
        EbankTransferMergeDTO merge = new EbankTransferMergeDTO();

        merge.setEbankOrderNo(ebankOrderNo);
        merge.setClosed(false);

        merge.setReceiveAccountNo(receiveAccount.getAccountNo());
        merge.setReceiveAccountType(receiveAccount.getAccountType());

        merge.setAmount(amount);
        merge.setStatus(TransferConstant.status_wait_handle);

        return merge;
    }
}
