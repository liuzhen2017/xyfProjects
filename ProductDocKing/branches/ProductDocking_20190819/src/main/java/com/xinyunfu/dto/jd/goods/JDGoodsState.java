package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDGoodsState {
      private Long sku;           // jd商品skuId
      private Integer state;            //1：上架 0：下架
}
