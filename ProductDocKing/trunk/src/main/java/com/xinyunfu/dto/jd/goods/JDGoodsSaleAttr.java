package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDGoodsSaleAttr {
      private String imagePath;
      private String saleValue;       //标签名称
      private String[] skuIds;      //当前标签下的同类商品skuId
}
