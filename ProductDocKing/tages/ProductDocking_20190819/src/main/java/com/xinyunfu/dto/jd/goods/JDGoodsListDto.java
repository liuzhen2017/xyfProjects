package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDGoodsListDto {
      private Integer total;
      private List<JDGoodsDto> data;

}
