package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDStockParam {
      private String skuNums;     //商品和数量 [{"skuId": 2815801, "num":101}]
      private String area;          //格式：1_0_0_0 (分别代表一、二、三、四级地址，务必按照格式要求进行返回，如部分地区如北京、上海等地不存在4级地址，4级地址字段返回0)
}
