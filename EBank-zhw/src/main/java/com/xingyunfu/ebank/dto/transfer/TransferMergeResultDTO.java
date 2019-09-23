package com.xingyunfu.ebank.dto.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TransferMergeResultDTO {

    private String ebankOrderNo;            //ebank合并订单号
    private String receiveAccountNo;        //收款人ebank账户
    private String receiveAccountType;      //收款人账户类型
    private BigDecimal amount;              //转帐金额
    private Boolean closed;                 //是否已关闭
    private String status;                  //合并订单状态

    private String userName;                //用户姓名
    private String phone;                   //银行卡绑定的手机号
    private String idCardNo;                //身份证编号

    private String bankCardNo;              //银行卡号
    private String bankName;                //银行名称
    private String bankNo;                  //银行编号
    private String branchName;              //分行名称
    private String branchNo;                //分行行号

    private String provinceName;            //省份
    private String cityName;                //城市

    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    public TransferMergeResultDTO(EbankTransferMergeDTO merge, EbankAccountDTO account){
        this.ebankOrderNo = merge.getEbankOrderNo();
        this.receiveAccountNo = merge.getReceiveAccountNo();
        this.receiveAccountType = merge.getReceiveAccountType();
        this.amount = merge.getAmount();
        this.closed = merge.getClosed();
        this.status = merge.getStatus();

        this.userName = account.getUserName();
        this.phone = account.getPhone();
        this.idCardNo = account.getIdCardNo();

        this.bankCardNo = account.getBankCardNo();
        this.bankName = account.getBankName();
        this.bankNo = account.getBankNo();
        this.branchName = account.getBranchName();
        this.branchNo = account.getBranchNo();

        this.provinceName = account.getProvinceName();
        this.cityName = account.getCityName();

        this.createTime = merge.getCreateTime();
        this.lastModifyTime = merge.getLastModifyTime();
    }
}
