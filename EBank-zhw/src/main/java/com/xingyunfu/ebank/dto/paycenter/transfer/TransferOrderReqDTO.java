package com.xingyunfu.ebank.dto.paycenter.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提交付款申请请求对象
 * Y 表示必传，N表示非必传
 */
@Data
@NoArgsConstructor
public class TransferOrderReqDTO {
    private String merchantNo;      //商户号   -- Y
    private Long timestamp;         //请求时间戳     -- Y
    private String subject;         //交易主题      -- Y
    private Integer amount;         //交易金额，单位分  -- Y
    private String orderNo;         //订单号       -- Y
    private String bankName;        //银行名称      -- Y
    private String branchName;      //支行名称，部分通道为必传  -- N
    private String branchNo;        //支行联行号，部分通道为必传 -- N
    private String cardNo;          //银行卡号  -- Y
    private Integer channelType;    //通道类型  -- Y
    private String idCardNo;        //身份证号码，部分通道为必传 -- N
    private String userName;        //持卡人姓名     -- Y
    private String notifyUrl;       //通知地址      -- Y
    private String bankCode;        //银行简码，例如工商银行=ICBC，招商银行=CMB -- Y
    private String provinceName;    //省份名称，部分通道为必传  -- N
    private String cityName;        //城市名称，部分通道为必传  -- N
    private String mobileNo;        //用户手机号

    public TransferOrderReqDTO(EbankAccountDTO receiveAccount, String merchantNo, BigDecimal amount, String ebankOrderNo){
        this.merchantNo = merchantNo;
        this.timestamp = new Date().getTime()/1000;
        this.subject = "【星云福】合并结算入账";
        this.amount = amount.multiply(new BigDecimal(100)).intValue();
        this.orderNo = ebankOrderNo;
        this.bankName = receiveAccount.getBankName();
        this.branchName = receiveAccount.getBranchName();
        this.branchNo = receiveAccount.getBranchNo();
        this.cardNo = receiveAccount.getBankCardNo();
        this.idCardNo = receiveAccount.getIdCardNo();
        this.userName = receiveAccount.getUserName();
        this.bankCode = receiveAccount.getBankNo();
        this.provinceName = receiveAccount.getProvinceName();
        this.cityName = receiveAccount.getCityName();
        this.mobileNo = receiveAccount.getPhone();
    }
}
