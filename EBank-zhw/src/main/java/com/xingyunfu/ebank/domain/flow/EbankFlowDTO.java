package com.xingyunfu.ebank.domain.flow;

import com.xingyunfu.ebank.constant.FlowConstants;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderRespDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class EbankFlowDTO {
    private Long id;
    private String ebankOrderNo;        //支付中心订单编号
    private String sysOrderNo;          //支付中心订单号
    private String voucher;             //交易凭证号
    private String accountNo;           //账号，系统生成
    private String accountType;         //账户类型

    private String flowType;            //流水类型，in转入，out转出
    private String flowSource;          //流水来源，商品/套餐购买 product, 用户转账 transfer

    private BigDecimal amount;          //流水金额
    private String status;              //流水状态,wait等待支付，success支付成功，failed支付失败

    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    //商品/套餐购买流水
    public EbankFlowDTO(EbankAccountDTO account, PlaceOrderRespDTO orderResp, String flowType, String flowSource, String ebankOrderNo){
        this.ebankOrderNo = ebankOrderNo;
        this.sysOrderNo = orderResp.getSysOrderNo();
        this.accountNo = account.getAccountNo();
        this.accountType = account.getAccountType();

        this.flowType = flowType;
        this.flowSource = flowSource;

        this.amount = new BigDecimal(orderResp.getAmount()).divide(new BigDecimal(100));    //分 -- 元
        this.status = FlowConstants.flowStatus_0;
    }

    //合并转账流水
    public EbankFlowDTO(EbankAccountDTO account, TransferOrderRespDTO orderResp, String flowType,
                        String flowSource, String ebankOrderNo, Boolean success){
        this.ebankOrderNo = ebankOrderNo;
        this.voucher = orderResp.getVoucher();
        this.accountNo = account.getAccountNo();
        this.accountType = account.getAccountType();

        this.flowType = flowType;
        this.flowSource = flowSource;

        this.amount = new BigDecimal(orderResp.getAmount()).divide(new BigDecimal(100));    //分 -- 元
        this.status = success?FlowConstants.flowStatus_1:FlowConstants.flowStatus_2;
    }

    //线下合并转帐流水
    public EbankFlowDTO(EbankAccountDTO account, BigDecimal amount, String flowType, String flowSource, String ebankOrderNo){
        this.ebankOrderNo = ebankOrderNo;
        this.accountNo = account.getAccountNo();
        this.accountType = account.getAccountType();

        this.flowType = flowType;
        this.flowSource = flowSource;

        this.amount = amount;
        this.status = FlowConstants.flowStatus_0;
    }
}
