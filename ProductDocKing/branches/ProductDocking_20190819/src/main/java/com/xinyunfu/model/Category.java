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


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("pro_category")
public class Category implements Serializable {

      private static final long serialVersionUID = 1L;

      @TableId(type = IdType.AUTO)
      private Long channelId;         //分类id
      private String channelName;        //分类名称
      private Long fatherId;          //父级id
      private Integer sortNumber;        //排序号
      private Long bannerId;
      private String imageUrl;           //图片地址

}