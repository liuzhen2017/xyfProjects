package com.xinyunfu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author TYM
 * @date 2019/7/20
 * @Description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFreightDTO implements Serializable {

    private String province;

    private String city;

    private String addres;

    private  Map<String, Integer> map;
}
