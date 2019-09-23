package com.xinyunfu.product.service.impl;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.ChannelId;
import com.xinyunfu.product.dto.ChannelPageDTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.RecommendDTO;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.service.RecommendService;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	private IProductService productService;
	@Autowired
	private IChannelService channelService;
	
	public List<ProDto> queryProDtoByRecommend(){
		long channelId=10058L;
		return productService.queryProDtosListByChannelId(channelId);
	}

//	@Override
//	public ResponseInfo<> queryRecommend(ChannelPageDTO channelPageDTO) {
//		long channelId= ChannelId.RECOMMEND;
//		List<ProDto> list = productService.queryProDtosListByChannelId(channelId);
//		PageVO<ProDto> page=new PageVO<>(list, channelPageDTO.getPageSize());
//		page.setCurrent_page(channelPageDTO.getPage());
//		page.setSize(channelPageDTO.getPageSize());
//		List<ProDto> currentPageData = page.currentPageData();
//		return ResponseInfo.success(new RecommendDTO(list));
//	}





}
