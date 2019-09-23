package com.xinyunfu.dto.jd.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSGStatusDto {
      private int state;       //状态，1为启用
      private long sku;       //
      private int shelf_id;

}
