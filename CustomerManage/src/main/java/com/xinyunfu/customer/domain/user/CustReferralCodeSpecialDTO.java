package com.xinyunfu.customer.domain.user;

import lombok.Data;

@Data
public class CustReferralCodeSpecialDTO {
    private Long id;
    private String referralCode;
    private Boolean used;
    private String remark;
}
