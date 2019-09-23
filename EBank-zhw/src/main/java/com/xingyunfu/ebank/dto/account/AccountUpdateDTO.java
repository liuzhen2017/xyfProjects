package com.xingyunfu.ebank.dto.account;

import com.xingyunfu.ebank.constant.InnerAccountConstant;
import com.xingyunfu.ebank.dto.user.BankAccountInfoDTO;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AccountUpdateDTO {

    @NotNull
    private Long userNo;                    //用户编号
    private String userName;                //用户姓名
    private String phone;
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

    private String businessStatus;          //业务状态 not use
    private Boolean enable;                 //是否可用

    public AccountUpdateDTO(UserInfoDTO userInfo, BankAccountInfoDTO bankAccountInfo){
        this.userNo = userInfo.getUserNo();
        this.userName = userInfo.getName();
        this.phone = bankAccountInfo.getPhone();
        this.idCardNo = userInfo.getCardNo();
        this.accountName = userInfo.getNickName();
        this.accountType = userInfo.getLevel().equals(InnerAccountConstant.level_0)?
                        InnerAccountConstant.accountType_common: InnerAccountConstant.accountType_ambassador;

        this.bankCardNo = bankAccountInfo.getAccountNo();
        this.bankName = bankAccountInfo.getBankName();
        this.bankNo = bankAccountInfo.getBankCode();
        this.branchName = bankAccountInfo.getBranchName();
        this.branchNo = bankAccountInfo.getBranchNo();

        this.provinceName = bankAccountInfo.getProvinceName();
        this.cityName = bankAccountInfo.getCityName();

        this.enable = true;
    }
}
