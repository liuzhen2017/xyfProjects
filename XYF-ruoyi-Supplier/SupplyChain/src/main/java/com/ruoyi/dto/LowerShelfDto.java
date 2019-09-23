package com.ruoyi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LowerShelfDto {
      private String skuIds;
      private int shelf_id;
}
