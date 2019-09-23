package com.xinyunfu.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSaleSkusParam {

      private String skuIds;  //skuIds 商品编号，支持批量，以【,】分隔 (最高支持100个商品)	 示例：2815801,5154306,3275310
}
