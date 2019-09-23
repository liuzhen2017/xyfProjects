package com.xinyunfu.commons.goods;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class ProChannel extends BaseModel{

	private Long channelId;         //分类id
	private String channelName;        //分类名称
	private Long fatherId;          //父级id
	private String imageUrl;           //图片地址
	private Integer status;		//状态，启用=0,禁用=1,删除=2
	private Long channelTypeId;
	private Long bannerId;


}
