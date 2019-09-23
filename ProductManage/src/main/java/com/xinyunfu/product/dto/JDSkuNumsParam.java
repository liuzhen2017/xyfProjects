package com.xinyunfu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author TYM
 * @date 2019/8/20
 * @Description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSkuNumsParam {

    private Long skuId;
    private Integer num;
}
