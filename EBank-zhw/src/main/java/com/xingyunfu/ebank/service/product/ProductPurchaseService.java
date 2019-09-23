package com.xingyunfu.ebank.service.product;

import com.rnmg.jace.utils.ResponseInfo;
import com.xingyunfu.ebank.constant.ChannelConstants;
import com.xingyunfu.ebank.constant.WalletConstant;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.domain.product.EbankProductRecordDTO;
import com.xingyunfu.ebank.dto.paycenter.fastpaymentapply.FastPaymentApplyReqDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderReqDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderRespDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddRespDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddV2DTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.feign.WalletAcctManageFeign;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import com.xingyunfu.ebank.service.channel.ChannelMangerService;
import com.xingyunfu.ebank.service.flow.FlowMangerService;
import com.xingyunfu.ebank.service.paycenter.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.xingyunfu.ebank.constant.FlowConstants.flowType_in;
import static com.xingyunfu.ebank.constant.FlowConstants.flowType_out;
import static com.xingyunfu.ebank.constant.InnerAccountConstant.*;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.*;

@Slf4j
@Service
public class ProductPurchaseService {
    @Autowired private ProductRecordMangerService productRecordMangerService;
    @Autowired private PlaceOrderService placeOrderService;
    @Autowired private ChannelMangerService channelMangerService;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private FlowMangerService flowMangerService;
    @Autowired private OrderQueryService orderQueryService;
    @Autowired private WalletAcctManageFeign walletAcctManageFeign;
    @Autowired private AsyncCallBackOrderService asyncCallBackOrderService;
    @Autowired private FastPaymentConfirmService fastPaymentConfirmService;
    @Autowired private FastPaymentApplyService fastPaymentApplyService;

    public ProductRecordAddRespDTO purchase(ProductRecordAddV2DTO productRecordAdd) throws Exception {
        //支付渠道
        EbankChannelDTO channel = channelMangerService.findByEbankPayTypeAndClientType(
                productRecordAdd.getEbankPayType(), productRecordAdd.getClientType());
        log.debug("get channel: {}", channel);
        if(Objects.isNull(channel)) throw new EBankException(PRODUCT_CHANNEL_ERROR);

        if(channel.getPayType().equals(ChannelConstants.payType_5)){//内部钱包支付
            return this.purchaseWallet(productRecordAdd, channel);
        }else{//外部通道支付
            return this.purchase(productRecordAdd, channel);
        }
    }

    /**
     * 向支付钱包发起支付
     */
    public ProductRecordAddRespDTO purchaseWallet(ProductRecordAddV2DTO productRecordAdd, EbankChannelDTO channel) throws EBankException, InterruptedException {
        //支付账号
        EbankAccountDTO ebankAccount = accountMangerService.hardFindByUserNo(productRecordAdd.getUserNo());
        log.debug("get ebank account: {}", ebankAccount);
        //系统账号
        EbankAccountDTO ebankAccountSystem = accountMangerService.findByUserNo(system_user_no);
        //购买记录
        log.debug("save product record");
        String ebankOrderNo = productRecordMangerService.add(productRecordAdd, channel, ebankAccount);

        ResponseInfo<Object> resp = walletAcctManageFeign.consume(ebankAccount.getUserNo().toString(),
                productRecordAdd.getOrderNo(), productRecordAdd.getAmount());
        log.info("wallet response: {}", resp);

        Boolean success = resp.getCode().equals(WalletConstant.wallet_success);

        //支付成功/失败后，更新订单状态，设置为支付成功 /支付失败
        productRecordMangerService.walletSuccessOrFailed(ebankOrderNo, success);

        if(!success) throw new EBankException(PRODUCT_ADD_RESPONSE_ERROR.setDesc(resp.getMessage()));
        if (success) {
            //支付成功，创建流水记录
            flowMangerService.addSuccess(ebankAccount, productRecordAdd.getAmount(), flowType_out, ebankOrderNo); //出账流水
            flowMangerService.addSuccess(ebankAccountSystem, productRecordAdd.getAmount(), flowType_in, ebankOrderNo); //入账流水

            //支付成功，记录账户
            BigDecimal amount = productRecordAdd.getAmount();
            accountMangerService.operateBalance(ebankAccountSystem.getAccountNo(), amount, 1);
            accountMangerService.operateBalance(ebankAccount.getAccountNo(), amount, -1);

            //异步通知订单服务，等待3秒
            asyncCallBackOrderService.callBackOrder(productRecordAdd.getOrderNo(), "-1",
                    amount, true, productRecordAdd.getOrderType());
        }

        ProductRecordAddRespDTO result =  new ProductRecordAddRespDTO();
        result.setPayNo("-1");
        result.setPayLoad("-1");
        result.setOrderNo(productRecordAdd.getOrderNo());
        result.setOpenType(channel.getTradeType());
        return result;
    }

    /**
     * 购买产品，立即支付, 调用支付中心，获取支付URL
     */
    private ProductRecordAddRespDTO purchase(ProductRecordAddV2DTO productRecordAdd, EbankChannelDTO channel) throws Exception {
        //支付账号
        EbankAccountDTO ebankAccount = accountMangerService.hardFindByUserNo(productRecordAdd.getUserNo());
        log.debug("get ebank account: {}", ebankAccount);

        //如果是快捷支付，判断是否绑定银行卡
        if(Objects.isNull(ebankAccount.getBankCardNo())
                && channel.getPayType().equals(ChannelConstants.payType_3))
            throw new EBankException(PRODUCT_FAST_PAYMENT_CARD);

        //系统账号
        EbankAccountDTO ebankAccountSystem = accountMangerService.findByUserNo(system_user_no);
        //购买记录
        log.debug("save product record");
        String ebankOrderNo = productRecordMangerService.add(productRecordAdd, channel, ebankAccount);

        PlaceOrderRespDTO placeOrderResp;
        try{
            //向支付中心开始支付
            log.info("Start apply apy.");
            placeOrderResp = placeOrderService.applyPay(
                    new PlaceOrderReqDTO(productRecordAdd, ebankAccount, channel, payCenterConfig.getMerchantNo(), ebankOrderNo));
            log.info("place order resp: {}", placeOrderResp);
        }catch (Exception e){
            productRecordMangerService.applySuccessOrFailed(ebankOrderNo, false, null);
            if(e instanceof EBankException) {
                EBankException ce = (EBankException) e;
                log.error("error code: {}, error desc: {}", ce.getErrCode(), ce.getDesc());
            }
            log.error("exception: {}", e);
            throw e;
        }

        //申请成功/失败后，更新订单状态，设置为申请支付中/申请支付失败，同时设置支付中心订单号
        productRecordMangerService.applySuccessOrFailed(ebankOrderNo, pay_success_code.equals(placeOrderResp.getResCode()),
                placeOrderResp.getSysOrderNo());

        //申请成功，创建流水记录
        if (pay_success_code.equals(placeOrderResp.getResCode())) {
            flowMangerService.add(ebankAccount, placeOrderResp, flowType_out, ebankOrderNo); //出账流水
            flowMangerService.add(ebankAccountSystem, placeOrderResp, flowType_in, ebankOrderNo); //入账流水

            //如果是快捷支付，异步发送请求，申请短信码发送
            if(channel.getPayType().equals(ChannelConstants.payType_3))
                fastPaymentApplyService.fastPaymentApply(new FastPaymentApplyReqDTO(ebankAccount, ebankOrderNo));
        }

        ProductRecordAddRespDTO resp =  new ProductRecordAddRespDTO(placeOrderResp, channel);
        resp.setOrderNo(productRecordAdd.getOrderNo());
        return resp;
    }

    /**
     * 支付中心发送请求，判断订单是否成功
     */
    public Boolean purchase(String orderNo) throws Exception {
        EbankProductRecordDTO product = productRecordMangerService.findByOrderNo(orderNo).stream()
                .max((var0, var1) -> (int) (var0.getId() - var1.getId())).orElse(null);
        if(Objects.isNull(product)) return false;
        log.debug("right product: {}", product);

        return orderQueryService.orderQuery(product.getEbankOrderNo(), product.getSysOrderNo());
    }

    /**
     * 快捷支付使用授权码确认支付
     */
    public void fastPaymentConfirm(String orderNo, String verifyCode) throws Exception {
        EbankProductRecordDTO product = productRecordMangerService.findByOrderNo(orderNo).stream()
                .max((var0, var1) -> (int) (var0.getId() - var1.getId())).orElse(null);
        if(Objects.isNull(product)) throw new EBankException(PRODUCT_ORDER_NONE);

        EbankAccountDTO account = accountMangerService.hardFindByAccountNo(product.getAccountNo());
        fastPaymentConfirmService.fastPaymentConfirm(product.getEbankOrderNo(), account.getPhone(), verifyCode);
    }
}
