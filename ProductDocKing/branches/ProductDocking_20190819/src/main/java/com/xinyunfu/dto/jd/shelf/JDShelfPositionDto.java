package com.xinyunfu.dto.jd.shelf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDShelfPositionDto {

      private int id;
      private long merchant_id;      //商户id
      private String title;         //位置描述
      private long create_time;         //添加时间
      private int father_id;        //父id
}
