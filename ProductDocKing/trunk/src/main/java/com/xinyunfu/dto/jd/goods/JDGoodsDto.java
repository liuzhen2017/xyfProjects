package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDGoodsDto {
      private Long id;
      private String image_path;    //主图
      private Integer status;            // jd状态1为启用，平台状态:启用=0,禁用=1,删除=2
      private Long sku;           // jd商品skuId
      private String appintroduce;
      private String introduction;
      private String param;         //规格
      private String name;
      private String category;            //分类
      private BigDecimal jd_price;        // 商品京东价格
      private BigDecimal price;        // 协议价
      private BigDecimal rebate;         // 折扣；八五折=8.5
      private long update_time;     // jd类型为 number，平台类型为 Timestamp
      private Integer comment_summarys;        // 商品好评率，如 98
      private Integer pool_page_num;            // 商品池编号

}
