package com.xingyunfu.ebank.dto.flow;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class FlowPageQueryDTO {
    @NotNull@Min(0)
    private Integer pageNo;
    @NotNull@Min(3)
    private Integer pageSize;
    @NotNull
    private Long startTime = 0L;
    @NotNull
    private Long endTime = Instant.now().getEpochSecond();

    private String ebankOrderNo;        //支付中心订单编号
    private String sysOrderNo;          //支付中心订单号
    private String voucher;             //交易凭证号
    private String accountNo;           //账号，系统生成
    private String accountType;         //账户类型

    private String flowType;            //流水类型，in转入，out转出
    private String flowSource;          //流水来源，商品/套餐购买 product, 用户转账 transfer

    private BigDecimal amount;          //流水金额
    private String status;              //流水状态,wait等待支付，success支付成功，failed支付失败
}
