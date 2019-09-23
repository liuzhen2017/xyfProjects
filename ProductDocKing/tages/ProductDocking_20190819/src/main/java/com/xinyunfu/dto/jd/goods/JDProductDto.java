package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDProductDto {

      //商品
      private long skuId;
      private String name;
      private String unit;

      //商品详情
      private Double weight;
      @Builder.Default
      private Integer source = 1;		//商品来源,0其他,1京东,2怡亚通
      private String specs;			//商品规格，采用json格式保存		商品详情的 param
      private String proDetails;		//'商品详情，采用html格式保存	    商品详情的 introduction

      //商品价格
      private BigDecimal price;		//出售价格
      private BigDecimal costPrice;		//成本价

      //图片信息
      private String imgUrl;        //主图，需要在前面添加http://img13.360buyimg.com/n0/
      List<JDImage> imageList;      //图片详情

      //库存信息
      private Integer stock;		//当前库存
      private String state;         //库存状态编号 33,39,40,36;有货 现货-下单立即发货;有货 在途-正在内部配货，预计2 ~6天到达本仓库 40 有货 可配货-下单后从有货仓库配货;预订
      private Integer sellStock;		//当前售量

      //分类信息
      private String category;
      private List<JDCategoryDto> categoryList;       //当前商品对应的分类id

      //同类商品规格信息
      private List<JDSimilarGoodsDto> similarGoods;

}
