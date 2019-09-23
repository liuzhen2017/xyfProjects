package com.xinyunfu.customer.dto.address;

import com.xinyunfu.customer.domain.address.CustShippingAddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class ShippingAddressInfoDTO {
    private Long id;
    private Long userNo;                //收货地址所有人
    private Boolean defaultAddress;     //是否是默认地址
    private String phone;               //收件人手机号
    private String name;                //收件人手机号
    private ShippingAreaInfoDTO areaInfo; //区域地址信息
    private String address;             //详细地址
    private String postCode;            //邮政编码
    private Timestamp createTime;          //创建时间
    private Timestamp lastModifyTime;      //最后修改时间

    public ShippingAddressInfoDTO(CustShippingAddressDTO address){
        this.id = address.getId();
        this.userNo = address.getUserNo();
        this.defaultAddress = address.getDefaultAddress();
        this.phone = address.getPhone();
        this.name = address.getName();
        this.address = address.getAddress();
        this.postCode = address.getPostCode();
        this.createTime = address.getCreateTime();
        this.lastModifyTime = address.getLastModifyTime();
    }
}
