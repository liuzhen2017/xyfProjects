package com.xinyunfu.customer.dto.imagetext;

import lombok.Data;

/**
 * 百度身份证认证结果
 */
@Data
public class IdCardRespDTO {
    private String address;
    private String number;
    private String birthday;
    private String name;
    private String sex;
    private String nation;
}
