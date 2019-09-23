package com.xinyunfu.dto.jd.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSGWrapperDto {
      private int total;
      private List<JDShlefGoodsDto> data;
}
