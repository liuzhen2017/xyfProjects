package com.xinyunfu.dto.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class ShippingAddressInfoDTO implements Serializable {
    private Long id;
    private Boolean defaultAddress;     //是否是默认地址
    private String phone;               //收件人手机号
    private String name;                //收件人手机号
    private ShippingAreaInfoDTO areaInfo; //区域地址信息
    private String address;             //详细地址
    private String postCode;            //邮政编码
    private String createTime;          //创建时间
    private String lastModifyTime;      //最后修改时间
    private Long userNo;
}
