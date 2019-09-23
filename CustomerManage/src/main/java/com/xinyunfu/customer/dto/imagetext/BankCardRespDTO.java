package com.xinyunfu.customer.dto.imagetext;

import lombok.Data;

@Data
public class BankCardRespDTO {

    private String cardNo;
    private String validDate;
    private Integer cardType;
    private String bankName;
}
