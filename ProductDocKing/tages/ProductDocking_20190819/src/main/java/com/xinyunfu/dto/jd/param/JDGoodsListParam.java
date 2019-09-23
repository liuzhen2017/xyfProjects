package com.xinyunfu.dto.jd.param;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JDGoodsListParam {

      @Builder.Default
      private Integer page = 0;         //当前页码，0开始
      @Builder.Default
      private Integer pagesize = 30;           //每页展示条数
      @Builder.Default
      private Integer admin_status = 1;       //0表示下架，1表示上架
      @Builder.Default
      private BigDecimal start_rebate = new BigDecimal(0.0).setScale(1);         //折扣，开始值(不含)，传该值不传end_rebate时，表示end_rebate=10
      @Builder.Default
      private BigDecimal end_rebate = new BigDecimal(7.0).setScale(1);           //折扣，结束值（含），传该值不传start_rebate时，表示start_rebate=0
      @Builder.Default
      private Integer pool_page_num = 0;           //商品池编号，0表示所有
      @Builder.Default
      private Long skuId = 0L;           //商品id，0表示所有，对应平台的 proId
      @Builder.Default
      private String name = " ";          //商品名称,不传或空字符（带空格）串表示所有 name=
}
