package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDAreaLimitParam {
      private String skuIds;
      private String provinceName;
      private String cityName;
      private String countyName;
}
