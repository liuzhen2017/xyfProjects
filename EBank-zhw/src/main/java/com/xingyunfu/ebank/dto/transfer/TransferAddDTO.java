package com.xingyunfu.ebank.dto.transfer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferAddDTO {
    @NotEmpty
    private String orderNo;                 //转账订单编号，其它服务的订单编号
    @NotNull
    private Long sourceUserNo;              //资金转出用户
    @NotNull
    private Long receiveUserNo;             //资金转入用户
    @NotNull
    private BigDecimal amount;              //付款金额
    @NotEmpty
    private String serverName;              //发起支付请求的服务
}
