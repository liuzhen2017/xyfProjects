package com.xinyunfu.dto.jd.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDShelfParam {
      private String title;    //位置描述
      private int father_id;     //父id，0表示第1级
}
