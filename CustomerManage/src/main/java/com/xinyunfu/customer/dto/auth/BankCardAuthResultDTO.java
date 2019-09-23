package com.xinyunfu.customer.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BankCardAuthResultDTO {

    private String status;
    private String msg;
    private String idCard;
    private String accountNo;
    private String name;
    private String bank;
    private String cardName;
    private String cardType;
    private String sex;
    private String area;
    private String province;
    private String city;
    private String prefecture;
    private String birthday;
    private String addrCode;
    private String lastCode;
}
