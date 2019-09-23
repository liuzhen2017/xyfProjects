package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDSkuNumsParam {
      private long skuId;   //商品编号
      private int num;        //购买数量
}
