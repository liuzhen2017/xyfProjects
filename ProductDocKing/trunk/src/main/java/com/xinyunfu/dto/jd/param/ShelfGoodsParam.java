package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfGoodsParam {
      private int shelf_id;   //上架位置id
      private long sku;       //商品编号
      private BigDecimal price;      //上架价格
      private int base_sale;        //基础销量
      private int sort;       //排序，暂时未开放，可以不传

}
