package com.xinyunfu.commons.goods;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProChannelRelation extends BaseModel{
	
	private Long proId;
	private Integer channelId;
	private Integer status;		//状态，启用=0,禁用=1,删除=2

}
