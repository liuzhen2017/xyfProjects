package com.xinyunfu.product.service;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.dto.SecKillDTO;

import java.util.List;

public interface SeckillService {


	ResponseInfo<SecKillDTO> querySecKill();
//	List<ProDto> findProDtoByChannelId(Long channelId);
	void updateKillStatus();
}
