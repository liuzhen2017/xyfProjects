package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.domain.product.EbankProductRecordDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.notice.PayNoticeDataDTO;
import com.xingyunfu.ebank.dto.paycenter.notice.TransferNoticeDataDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.feign.OrderManageFeign;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import com.xingyunfu.ebank.service.flow.FlowMangerService;
import com.xingyunfu.ebank.service.product.ProductRecordMangerService;
import com.xingyunfu.ebank.util.JsonMapperUtil;
import com.xingyunfu.ebank.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.system_user_no;
import static com.xingyunfu.ebank.constant.PayCenterConstant.payStatus_1;
import static com.xingyunfu.ebank.constant.ProductRecordConstant.status_pay_failed;
import static com.xingyunfu.ebank.constant.ProductRecordConstant.status_pay_success;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.PRODUCT_ADD_VERIFY_ERROR;

/**
 * 支付通知支付回调，针对用户接口
 */
@Slf4j
@Service
public class PaymentNoticeService {
    @Autowired
    private PayCenterConfig payCenterConfig;     //配置
    @Autowired
    private AccountMangerService accountMangerService;   //账户
    @Autowired
    private ProductRecordMangerService productRecordMangerService;   //商品/套餐购买记录
    @Autowired
    private FlowMangerService flowMangerService;
    @Autowired
    private OrderManageFeign orderManageFeign;

    private static String charSet = "utf-8";

    /**
     * 商品/套餐支付通知
     */
    public void payNotice(String reqBaseStr) throws Exception {

        ObjectMapper mapper = JsonMapperUtil.getMapper();
        PayCenterReqBaseDTO reqBase = mapper.readValue(reqBaseStr, PayCenterReqBaseDTO.class);
        //解码data
        String respData = RSAUtil.decrypt(reqBase.getData(), payCenterConfig.getPayPrivateKey(), charSet);
        log.info("decrypt reqData: {}", respData);

        //验证签证，验证失败抛出异常
        if (!RSAUtil.verify(respData, reqBase.getSign(), payCenterConfig.getPayPublicKey(), charSet)) {
            throw new EBankException(PRODUCT_ADD_VERIFY_ERROR);
        }

        PayNoticeDataDTO noticeData = mapper.readValue(respData, PayNoticeDataDTO.class);
        this.payNotice(noticeData);
    }

    public void payNotice(PayNoticeDataDTO noticeData) {
        //ebank订单号
        String ebankOrderNo = noticeData.getOutTradeNo();
        //转账记录
        EbankProductRecordDTO productRecord = productRecordMangerService.findByEbankOrderNo(ebankOrderNo);
        log.debug("product record: {}", productRecord);
        if(Objects.isNull(productRecord)) return;
        //更新账户余额
        BigDecimal amount = new BigDecimal(noticeData.getAmount()).divide(new BigDecimal(100));
        //防止重复回调
        if (productRecord.getStatus().equals(status_pay_success) || productRecord.getStatus().equals(status_pay_failed)) {
            log.error("Repeated callback! ebankOrderNo: {}, productRecord status: {}", ebankOrderNo, productRecord.getStatus());
            //通知订单管理
            orderManageFeign.payCallback(productRecord.getOrderNo(), productRecord.getSysOrderNo(), amount,
                    noticeData.getStatus().equals(payStatus_1), productRecord.getOrderType());
            return;
        }

        //更新商品购买记录
        Integer changeNu = productRecordMangerService.paySuccessOrFailed(ebankOrderNo, noticeData.getStatus().equals(payStatus_1));
        log.debug("changeNumber: {}", changeNu);
        if (changeNu == 0) return;   //如果未修改，直接结束，防止重复请求
        //更新流水
        flowMangerService.updateStatus(ebankOrderNo, noticeData.getStatus().equals(payStatus_1));
        if (noticeData.getStatus().equals(payStatus_1)) {
            log.info("pay notice success! bankOrderNo :{}", ebankOrderNo);
            //系统账号
            String accountNoSystem = accountMangerService.findByUserNo(system_user_no).getAccountNo();
            //支付账号
            String accountNo = productRecord.getAccountNo();
            //执行计算
            accountMangerService.operateBalance(accountNoSystem, amount, 1);
            accountMangerService.operateBalance(accountNo, amount, -1);
        }
        //通知订单管理
        orderManageFeign.payCallback(productRecord.getOrderNo(), productRecord.getSysOrderNo(), amount,
                noticeData.getStatus().equals(payStatus_1), productRecord.getOrderType());
    }

    /**
     * 转账结果通知
     */
    public void transferNotice(String reqBaseStr) throws Exception {

        ObjectMapper mapper = JsonMapperUtil.getMapper();
        PayCenterReqBaseDTO reqBase = mapper.readValue(reqBaseStr, PayCenterReqBaseDTO.class);

        //解码data
        String respData = RSAUtil.decrypt(reqBase.getData(), payCenterConfig.getPayPrivateKey(), charSet);
        log.info("reqData: {}", respData);

        //验证签证，验证失败抛出异常
        if (!RSAUtil.verify(respData, reqBase.getSign(), payCenterConfig.getPayPublicKey(), charSet)) {
            throw new EBankException(PRODUCT_ADD_VERIFY_ERROR);
        }

        TransferNoticeDataDTO noticeData = mapper.readValue(respData, TransferNoticeDataDTO.class);
        this.transferNotice(noticeData);
    }

    public void transferNotice(TransferNoticeDataDTO noticeData) {

    }
}
