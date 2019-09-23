package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 *  jd商品可售验证返回值
 */
public class JDCheckDto {

      private Long skuId;       //商品编号
      private String name;
      private Integer saleState;          //是否可售，1：是，0：否
      private Integer isCanVAT;          //是否可开增票，1：支持，0：不支持
      private Integer is7ToReturn;       //是否支持7天退货，1：是，0：不支持
}
