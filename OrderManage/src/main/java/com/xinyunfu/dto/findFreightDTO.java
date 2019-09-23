package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/20
 * @Description :
 */
@Data
public class findFreightDTO implements Serializable {

    /**
     * 省code
     */
    private String province;

    /**
     * 市code
     */
    private String city;


    /**
     * 省市区名字，使用-拼接
     */
    private String addres;


    /**
     * Map<sku_ID, 购买的数量>
     */
    private  Map<Long, Integer> map;
}
