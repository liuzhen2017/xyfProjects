package com.xinyunfu.customer.domain.address;

import com.xinyunfu.customer.dto.address.ShippingAddressAddDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 收货地址
 */
@NoArgsConstructor
@Data
public class CustShippingAddressDTO {

    private Long id;
    private Long userNo;                //收货地址所有人
    private Boolean defaultAddress;     //是否是默认地址
    private String phone;               //收件人手机号
    private String name;                //收件人手机号
    private Long regionId;              //区id
    private String address;             //详细地址
    private String postCode;            //邮政编码
    private Timestamp createTime;          //创建时间
    private Timestamp lastModifyTime;      //最后修改时间

    public CustShippingAddressDTO(Long userNo, ShippingAddressAddDTO addressAdd){
        this.userNo = userNo;
        this.defaultAddress = addressAdd.getDefaultAddress();
        this.phone = addressAdd.getPhone();
        this.name = addressAdd.getName();
        this.regionId = addressAdd.getRegionId();
        this.address = addressAdd.getAddress();
        this.postCode = addressAdd.getPostCode();
    }

    public CustShippingAddressDTO(Long userNo, ShippingAddressUpdateDTO addressUpdate){
        this.id = addressUpdate.getId();
        this.userNo = userNo;
        this.defaultAddress = addressUpdate.getDefaultAddress();
        this.phone = addressUpdate.getPhone();
        this.name = addressUpdate.getName();
        this.regionId = addressUpdate.getRegionId();
        this.address = addressUpdate.getAddress();
        this.postCode = addressUpdate.getPostCode();
    }
}
