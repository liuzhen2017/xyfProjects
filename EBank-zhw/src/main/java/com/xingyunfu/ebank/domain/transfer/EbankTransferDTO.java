package com.xingyunfu.ebank.domain.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAddDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 创建用户间转账
 */
@NoArgsConstructor
@Data
public class EbankTransferDTO {

    private Long id;
    private String orderNo;                 //转账订单编号，其它服务的订单编号
    private String ebankOrderNo;            //合并后的订单编号，合并订单是的订单编号

    private String sourceAccountNo;         //资金转出用户
    private String sourceAccountType;       //转出用户类型
    private String receiveAccountNo;        //资金转入用户
    private String receiveAccountType;      //转入用户类型

    private String personAccountNo;
    private String personAccountType;

    private BigDecimal amount;              //付款金额

    private Boolean closed;                 //是否已关闭
    private String serverName;              //发起支付请求的服务

    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    public EbankTransferDTO(TransferAddDTO transferAdd, EbankAccountDTO sourceAccount, EbankAccountDTO receiveAccount){
        this.orderNo = transferAdd.getOrderNo();

        this.sourceAccountNo = sourceAccount.getAccountNo();
        this.sourceAccountType = sourceAccount.getAccountType();
        this.receiveAccountNo = receiveAccount.getAccountNo();
        this.receiveAccountType = receiveAccount.getAccountType();

        this.amount = transferAdd.getAmount();

        this.closed = false;
        this.serverName = transferAdd.getServerName();
    }
    public EbankTransferDTO(TransferAddDTO transferAdd, EbankAccountDTO sourceAccount,
                            EbankAccountDTO receiveAccount, EbankAccountDTO personAccount){
        this.orderNo = transferAdd.getOrderNo();

        this.sourceAccountNo = sourceAccount.getAccountNo();
        this.sourceAccountType = sourceAccount.getAccountType();
        this.receiveAccountNo = receiveAccount.getAccountNo();
        this.receiveAccountType = receiveAccount.getAccountType();

        this.personAccountNo = personAccount.getAccountNo();
        this.personAccountType = personAccount.getAccountType();

        this.amount = transferAdd.getAmount();

        this.closed = false;
        this.serverName = transferAdd.getServerName();
    }
}
