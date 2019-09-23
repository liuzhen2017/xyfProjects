package com.xingyunfu.ebank.domain.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 合并后的转账
 */
@Data
@NoArgsConstructor
public class EbankTransferMergeDTO {
    private Long id;
    private String ebankOrderNo;            //合并后的订单编号，合并订单时的订单编号
    private Boolean closed;                 //是否已关闭
    private String receiveAccountNo;        //资金转入用户
    private String receiveAccountType;      //转入用户类型
    private BigDecimal amount;              //付款金额
    private String status;                  //合并订单状态
    private String voucher;                 //交易凭证号
    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    public EbankTransferMergeDTO(String ebankOrderNo, EbankAccountDTO ebankAccountDTO){
        this.ebankOrderNo = ebankOrderNo;
        this.closed = false;
        this.receiveAccountNo = ebankAccountDTO.getAccountNo();
        this.receiveAccountType = ebankAccountDTO.getAccountType();
    }
}
