package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDFreightSkuParam {
      private long skuId;   //商品编号
      private int num;        //购买数量
}
