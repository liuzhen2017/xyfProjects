package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSkuDto implements Serializable {
      private int category;   //商品分类
      private int num;        //数量
      private BigDecimal price;     //价格
      private int tax;        //税种
      private long oid;       //父商品ID
      private String name;          //商品名称
      private BigDecimal taxPrice;        //税费
      private long skuId;           //商品编号
      private BigDecimal nakedPrice;      //裸价
      private int type;       //类别
}
