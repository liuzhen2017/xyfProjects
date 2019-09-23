package com.xinyunfu.customer.dto.ebank;

import com.xinyunfu.customer.constant.InnerAccountConstant;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountAddDTO {

    private Long userNo;                    //用户编号
    private String userName;                //用户姓名
    private String phone;
    private String idCardNo;                //身份证编号
    private String accountName;             //账号名
    private String accountType;             //账户类型

    public AccountAddDTO(CustUserDTO userInfo){
        this.userNo = userInfo.getUserNo();
        this.userName = userInfo.getName();
        this.phone = userInfo.getPhone();
        this.idCardNo = userInfo.getCardNo();
        this.accountName = userInfo.getNickName();
        this.accountType = userInfo.getLevel().equals(InnerAccountConstant.level_0)?
                InnerAccountConstant.accountType_common: InnerAccountConstant.accountType_ambassador;
    }
}
