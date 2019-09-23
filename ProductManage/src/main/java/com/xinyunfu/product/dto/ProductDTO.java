package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProImage;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.model.ProPropertyValue;
import com.xinyunfu.product.model.ProSku;
import com.xinyunfu.product.model.Product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Product product;
    private ProDetails proDetails;
    private List<ProImage> imageList;
    private BigDecimal postage;
    private Integer allSellStock;         //总销量
    private Integer allSeckillSellStock;  //秒杀总销量
    private BigDecimal price;             //原价
    private BigDecimal minPrice;          //最低售价
    private BigDecimal seckillPrice;      //秒杀价
    private Integer allSeckillStock;      //秒杀总库存


}
