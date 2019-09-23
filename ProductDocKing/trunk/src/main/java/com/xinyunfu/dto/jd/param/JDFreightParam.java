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
public class JDFreightParam {
      List<JDFreightSkuParam> skus;
      private Long province;
      private Long city;
      private Long county;
      private Long town;
}
