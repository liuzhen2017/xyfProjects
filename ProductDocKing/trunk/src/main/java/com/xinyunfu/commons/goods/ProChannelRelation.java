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
	private String proName;
	private Long channelId;
	private String channelName;

}
