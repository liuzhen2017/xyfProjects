package com.xinyunfu.customer.dto.address;

import com.xinyunfu.customer.domain.address.CustAreaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShippingAreaInfoDTO {
    private CustAreaDTO province;       //省
    private CustAreaDTO city;           //市
    private CustAreaDTO region;         //区/县
    private CustAreaDTO town;           //乡/镇
}
