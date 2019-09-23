package com.xinyunfu.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShippingAreaInfoDTO implements Serializable {
    private CustAreaDTO province;       //省
    private CustAreaDTO city;           //市
    private CustAreaDTO region;         //区/县
    private CustAreaDTO town;           //乡/镇
}
