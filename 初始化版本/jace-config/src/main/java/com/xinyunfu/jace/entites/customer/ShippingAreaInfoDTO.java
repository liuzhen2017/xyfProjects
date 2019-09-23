package com.xinyunfu.jace.entites.customer;

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
}
