package com.xinyunfu.product.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ChannelPageDTO implements Serializable{

	private Long channelId;
	private Integer page;
	private Integer pageSize;
}
