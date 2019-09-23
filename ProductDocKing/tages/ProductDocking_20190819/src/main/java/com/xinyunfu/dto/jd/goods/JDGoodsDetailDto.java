package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDGoodsDetailDto {

      private long sku;       //商品编号
      private double weight;
      private String imagePath;     //主图地址，需要在前面添加 http://img13.360buyimg.com/n0/
      private Integer state;            //上下架状态；1表示上架
      private String brandName;     //品牌
      private String name;         //商品名称
      private String productArea;         //产地
      private String saleUnit;      //销售单位
      private String category;     //类别
      private String introduction;        //pc端商品详情介绍，暂时用不着
      private String param;        //规格与包装
      private String wareQD;       //wareQD
      private String shouhou;       //售后信息
      private String appintroduce;        //移动端商品详情介绍信息

}
