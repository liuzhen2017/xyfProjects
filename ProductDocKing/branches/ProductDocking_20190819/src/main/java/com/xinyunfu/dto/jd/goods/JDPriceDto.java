package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDPriceDto {
      private long skuId;     //商品编号
      private BigDecimal jdPrice;      //商品京东价格
      private BigDecimal Price;      //商品协议价格
}
