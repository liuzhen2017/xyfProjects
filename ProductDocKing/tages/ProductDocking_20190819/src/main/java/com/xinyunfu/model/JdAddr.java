package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("jd_addr")
public class JdAddr{
      private Integer id;
      private Long areaId;
      private String areaName;
      private Integer parentAreaId;
      private Integer level;
      private String areaCode;
}
