package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 *  kpl查看区域限制返回值
 */
public class JDAreaLimitDto {

      private Long skuId;
      private boolean isAreaRestrict;
}
