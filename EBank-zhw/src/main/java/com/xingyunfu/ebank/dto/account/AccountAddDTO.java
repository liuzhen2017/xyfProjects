package com.xingyunfu.ebank.dto.account;

import com.xingyunfu.ebank.constant.InnerAccountConstant;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import com.xingyunfu.ebank.util.FuzzyUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class AccountAddDTO {

    @NotNull
    private Long userNo;                    //用户编号
    private String userName;                //用户姓名
    @NotEmpty
    private String phone;                   //手机号
    private String idCardNo;                //身份证编号
    private String accountName;             //账号名
    @NotEmpty
    private String accountType;             //账户类型

    public AccountAddDTO(UserInfoDTO userInfo){
        this.userNo = userInfo.getUserNo();
        this.userName = userInfo.getName();
        this.phone = userInfo.getPhone();
        this.idCardNo = userInfo.getCardNo();
        this.accountName = userInfo.getNickName();
        this.accountType = userInfo.getLevel().equals(InnerAccountConstant.level_0)?
                InnerAccountConstant.accountType_common: InnerAccountConstant.accountType_ambassador;
    }

    @Override
    public String toString() {
        return "AccountAddDTO{" +
                "userNo=" + userNo +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", idCardNo='" + FuzzyUtil.cardFuzzy(idCardNo) + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
