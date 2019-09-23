package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDPostParam {
      List<JDSkuNumsParam> skuNums;
      private String provinceName;
      private String cityName;
      private String countyName;
      private String townName;
}
