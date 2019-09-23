package com.xinyunfu.customer.dto.address;

import com.xinyunfu.customer.constant.JSR303Constant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ShippingAddressUpdateDTO {
    @NotNull
    private Long id;
    private Boolean defaultAddress = false;     //是否是默认地址
    @Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;               //收件人手机号
    private String name;                //收件人手机号
    private Long regionId;              //区id
    private String address;             //详细地址
    private String postCode;            //邮政编码
}
