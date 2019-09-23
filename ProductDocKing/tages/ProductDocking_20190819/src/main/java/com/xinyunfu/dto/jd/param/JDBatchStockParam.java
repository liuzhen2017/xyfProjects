package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDBatchStockParam {
      private String skuNums;       //商品编号 批量以逗号分隔 (最高支持100个商品)，例如：2815801,5154306,3275310
      private String area;    //格式：1_0_0_0 (分别代表一、二、三、四级地址，务必按照格式要求进行返回，如部分地区如北京、上海等地不存在4级地址，4级地址字段返回0)
}
