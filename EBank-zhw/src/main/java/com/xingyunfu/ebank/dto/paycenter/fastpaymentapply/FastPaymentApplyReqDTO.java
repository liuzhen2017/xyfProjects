package com.xingyunfu.ebank.dto.paycenter.fastpaymentapply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FastPaymentApplyReqDTO {
    private String merchantNo;          //商户号
    private Long timestamp;             //请求时间戳
    @JsonProperty("out_trade_no")
    private String outTradeNo;          //商户订单号
    @JsonProperty("card_holder")
    private String cardHolder;         //持卡人姓名
    @JsonProperty("bank_code")
    private String bankCode;            //银行简码
    @JsonProperty("bank_card_no")
    private String bankCardNo;          //银行卡号
    @JsonProperty("card_type")
    private String cardType;            //账户类型， 1 借记卡， 2 信用卡
    @JsonProperty("account_type")
    private String accountType;         //账户类型，1 私户， 2 共户
    @JsonProperty("mobile_no")
    private String mobileNo;            //银行卡预留手机号
    @JsonProperty("card_cvn")
    private String cardCvn;             //信用卡CVN码 -- N
    @JsonProperty("card_expire")
    private String cardExpire;          //卡片过期时间 -- N
    @JsonProperty("identity_type")
    private String identityType;        //证件类型，1 身份证
    @JsonProperty("identity_no")
    private String identityNo;          //证件号码

    public FastPaymentApplyReqDTO(EbankAccountDTO account, String ebankOrderNo){
        this.timestamp = new Date().getTime()/1000;
        this.outTradeNo = ebankOrderNo;
        this.cardHolder = account.getUserName();
        this.bankCode = account.getBankNo();
        this.bankCardNo = account.getBankCardNo();
        this.cardType = "1";
        this.accountType = "1";
        this.mobileNo = account.getPhone();

        this.identityType = "1";
        this.identityNo = account.getIdCardNo();
    }

}
