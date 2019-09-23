package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

      private String channelId;         //分类id
      private String channelName;        //分类名称
      private String fatherId;          //父级id
      private String imageUrl;           //图片地址
      private String status;		//状态，启用=0,禁用=1,删除=2
}
