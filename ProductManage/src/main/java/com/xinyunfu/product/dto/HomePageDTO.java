package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.util.List;

import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.model.ProChannel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class HomePageDTO implements Serializable{
	
	
	private List<ProBanner> banners;
	private List<ProChannel> channels;
	private ActDTO actDTO;
	private SecKillDTO secKillDTO;
}
