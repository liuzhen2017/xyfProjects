package com.xingyunfu.ebank.domain.account;

import com.xingyunfu.ebank.dto.account.AccountAddDTO;
import com.xingyunfu.ebank.dto.account.AccountUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 每个转账账户的虚拟账号
 */
@NoArgsConstructor
@Data
public class EbankAccountDTO {
    private Long id;

    private String accountNo;               //账号
    private Long userNo;                    //用户编号
    private String userName;                //用户姓名
    private String phone;                   //银行卡绑定的手机号
    private String idCardNo;                //身份证编号
    private String accountName;             //账号名
    private String accountType;             //账户类型

    private String bankCardNo;              //银行卡号
    private String bankName;                //银行名称
    private String bankNo;                  //银行编号
    private String branchName;              //分行名称
    private String branchNo;                //分行行号

    private String provinceName;            //省份
    private String cityName;                //城市

    private BigDecimal balance;             //余额
    private BigDecimal frozenBalance;     //冻结余额
    private BigDecimal availableBalance;       //可用余额

    private String businessStatus;          //业务状态
    private Boolean enable;                 //是否可用

    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    public EbankAccountDTO(AccountAddDTO accountAdd){
        this.userNo = accountAdd.getUserNo();
        this.userName = accountAdd.getUserName();
        this.phone = accountAdd.getPhone();
        this.idCardNo = accountAdd.getIdCardNo();
        this.accountNo = "E" + accountAdd.getUserNo();
        this.accountName = accountAdd.getAccountName();
        this.accountType = accountAdd.getAccountType();

        this.balance = new BigDecimal("0.00");
        this.frozenBalance = new BigDecimal("0.00");
        this.availableBalance = new BigDecimal("0.00");

        this.enable = true;
    }

    public EbankAccountDTO(AccountUpdateDTO accountUpdate){
        this.userNo = accountUpdate.getUserNo();
        this.userName = accountUpdate.getUserName();
        this.phone = accountUpdate.getPhone();
        this.idCardNo = accountUpdate.getIdCardNo();
        this.accountName = accountUpdate.getAccountName();
        this.accountType = accountUpdate.getAccountType();

        this.bankCardNo = accountUpdate.getBankCardNo();
        this.bankName = accountUpdate.getBankName();
        this.bankNo = accountUpdate.getBankNo();
        this.branchName = accountUpdate.getBranchName();
        this.branchNo = accountUpdate.getBranchNo();

        this.provinceName = accountUpdate.getProvinceName();
        this.cityName = accountUpdate.getCityName();

        this.businessStatus = accountUpdate.getBusinessStatus();
        this.enable = accountUpdate.getEnable();
    }
}
