package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/18
 * @Description : 提交商家的的DTO
 */
@Data
public class SubStoreDTO implements Serializable {

    /**
     * 商家ID
     */
    private Long storeId;

    /**
     * 商家名字
     */
    private String storeName;

    /**
     * 合计
     */
    private BigDecimal amount = new BigDecimal(0);

    /**
     * 运费
     */
    private BigDecimal freight = new BigDecimal(0);


    /**
     * 商品集合
     */
    private List<SonListDTO> list = new ArrayList<>();

}
