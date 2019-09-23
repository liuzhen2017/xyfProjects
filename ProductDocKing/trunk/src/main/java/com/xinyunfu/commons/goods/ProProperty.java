package com.xinyunfu.commons.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProProperty extends BaseModel {

      private Long propertyId;
      private String propertyName;
      private Long proId;
}
