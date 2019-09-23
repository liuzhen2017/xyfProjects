package com.xinyunfu.customer.dto.address;

import com.xinyunfu.customer.constant.JSR303Constant;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ShippingAddressAddDTO {

    private Boolean defaultAddress = false;     //是否是默认地址
    @NotEmpty@Pattern(regexp = "^[1][\\d]{10}$", message = JSR303Constant.phone)
    private String phone;               //收件人手机号
    @NotEmpty
    private String name;                //收件人姓名
    @NotNull
    private Long regionId;              //区id
    @NotEmpty
    private String address;             //详细地址
    private String postCode;            //邮政编码
}
