package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.util.List;

import com.xinyunfu.product.model.ProChannel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ChannelDto implements Serializable{
	
	private ProChannel seconedChannel;
	private List<ProChannel> thirdChannel;
}
