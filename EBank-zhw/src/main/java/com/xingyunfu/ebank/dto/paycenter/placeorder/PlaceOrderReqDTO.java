package com.xingyunfu.ebank.dto.paycenter.placeorder;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddV2DTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统一订单支付明文请求对象
 * Y 表示必传，N表示非必传
 */
@Data
public class PlaceOrderReqDTO {
    private String merchantNo;      //商户号，系统分配  -- Y
    private Long timestamp;         //请求时间戳  -- Y
    private String orderNo;         //保证商户系统唯一  -- Y
    private String userCode;        //商户系统交易用户账号，系统只做记录，不做验证  -- Y
    private String userName;        //商户系统交易用户名称  -- N
    private Integer amount;         //交易金额，单位分 -- Y
    private String subject;         //交易主题      -- Y
    private Integer payType;        //支付方式，见附录 PayType  -- Y
    private String returnUrl;       //前端返回地址，部分支付方式必传   --N
    private String notifyUrl;       //异步通知地址            -- Y
    private String deathTime;       //订单过期时间，格式 yyyy-MM-dd HH:mm:ss     -- N
    private String privateValue;    //商户私有信息，通知时原样返回    -- N
    private String subChannel;      //交易子通道代码   -- N
    private Integer tradeType;      //交易类型，见附录 TradeType    -- N
    private String authCode;        //授权码，付款码扫描结果字符串    -- N
    private String openid;          //微信公众号支付时必传(JSAPI)     -- N
    private String clientIp;        //客户端IP -- N

    public PlaceOrderReqDTO(ProductRecordAddV2DTO productRecordAdd, EbankAccountDTO ebankAccount,
                            EbankChannelDTO channel, String merchantNo, String ebankOrderNo){
        this.merchantNo = merchantNo;
        this.timestamp = new Date().getTime()/1000;
        this.orderNo = ebankOrderNo;
//        this.userCode = ebankAccount.getAccountNo();
        this.userCode = ebankAccount.getPhone();        //根据要求，用户编号传值改成用户手机号
        this.userName = ebankAccount.getUserName();
        this.amount = productRecordAdd.getAmount().multiply(new BigDecimal(100)).intValue();
        this.subject = productRecordAdd.getSubject();
        this.payType = channel.getPayType();
        this.returnUrl = productRecordAdd.getReturnUrl();

        this.deathTime = productRecordAdd.getPayTimeOut();

        this.tradeType = channel.getTradeType();
        this.clientIp = productRecordAdd.getClientIp();
    }
}
