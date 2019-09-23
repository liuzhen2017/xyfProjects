package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDGoodsDetailParam {

      private String sku;     //jd商品编号，为8位时，返回图书音像类目商品
      @Builder.Default
      private String isShow = "true";        //false:查询商品基本信息；true:商品基本信息 + 商品售后信息 + 移动商品详情介绍信息。需要移动详情。
}
