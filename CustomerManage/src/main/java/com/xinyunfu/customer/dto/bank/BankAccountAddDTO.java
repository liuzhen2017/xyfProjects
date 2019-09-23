package com.xinyunfu.customer.dto.bank;

import com.xinyunfu.customer.constant.JSR303Constant;
import com.xinyunfu.customer.utils.FuzzyUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class BankAccountAddDTO {
    @NotNull
    private Long bankId;            //银行id
    @NotEmpty
    private String accountNo;          //银行卡号
    @NotNull
    private Long regionId;          //开户区id
    @NotEmpty
    private String address;         //具体地址
    @NotNull
    private Boolean defaultCard;    //是否是默认卡
    @NotEmpty@Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;           //手机号

    @Override
    public String toString() {
        return "BankAccountAddDTO{" +
                "bankId=" + bankId +
                ", accountNo='" + FuzzyUtil.fuzzy(accountNo) + '\'' +
                ", regionId=" + regionId +
                ", address='" + address + '\'' +
                ", defaultCard=" + defaultCard +
                ", phone='" + phone + '\'' +
                '}';
    }
}
