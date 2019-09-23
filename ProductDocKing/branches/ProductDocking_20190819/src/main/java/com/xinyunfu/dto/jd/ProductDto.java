package com.xinyunfu.dto.jd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
      private BigDecimal price;           //暂时只需实时查询价格

}
