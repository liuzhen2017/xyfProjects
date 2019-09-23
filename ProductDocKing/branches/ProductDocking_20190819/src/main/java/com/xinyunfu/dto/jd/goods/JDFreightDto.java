package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDFreightDto {

      private BigDecimal freight;     //总运费
      private BigDecimal baseFreight;     //基础运费
      private BigDecimal remoteRegionFreight;      //偏远运费
      private String remoteSku;       //需收取偏远运费的sku
}
