package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDGoodsStyleDto {
      private String jsContent;     //js内容
      private String cssContent;       //css内容
      private long sku;                   //商品编号
      private String htmlContent;         //html内容
}
