package com.xinyunfu.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 平台和渠道类型关联表
 *
 * @author  lhpu
 * @since 2019-07-16
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("category_mapping")

public class CategoryMapping implements Serializable {

      private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private Integer channelId;          //平台分类id

      private String channelName;         //平台分类名称

      private Integer categoryId;         //渠道分类id

      private String categoryName;        //渠道分类名称

      private Integer source;       //渠道标识，0其他，1京东，2怡亚通，默认为0

      private Timestamp createdTime;     //创建时间

      private Timestamp updatedTime;     //修改时间

}
