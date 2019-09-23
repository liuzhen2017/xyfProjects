package com.xinyunfu.dto.jd.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDShlefGoodsDto {
      private String image_path;     //图片
      private int state;      //状态，1为启用
      private long sku;
      private String appintroduce;        //移动商品详情介绍信息
      private String name;
      private String introduction;        //PC商品详情介绍信息
      private String sku_type;
      private String param;         //规格
      private String category;         //三级分类
      private BigDecimal jd_price;        //商品京东价格
      private BigDecimal price;           //协议价
      private int shelf_id;         //上架位置的id
      private BigDecimal shelf_price;      //上架价格
      private int sale;       //销量
      private int base_sale;        //基础销量

}
