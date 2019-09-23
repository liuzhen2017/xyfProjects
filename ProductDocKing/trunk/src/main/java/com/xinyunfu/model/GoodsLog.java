package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods_synclog")
@Builder
public class GoodsLog {

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;
      private String description;          //描述
      private Timestamp startTime;
      private Timestamp endTime;
      private Long runTime;       //以分钟为单位
      private String remark;

}
