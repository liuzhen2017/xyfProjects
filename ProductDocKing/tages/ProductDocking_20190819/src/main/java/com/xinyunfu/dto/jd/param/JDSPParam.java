package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSPParam {
      private int id;   //位置id
      private String title;     //位置描述
      int father_id;      //父id
}
