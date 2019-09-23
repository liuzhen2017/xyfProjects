package com.xinyunfu.product.service.impl;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.ChannelId;
import com.xinyunfu.product.dto.ActDTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.service.ActService;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActServiceImpl implements ActService{

	@Autowired
	private IProductService productService;
	@Autowired
	private IChannelService channelService;


	@Override
	public ResponseInfo<ActDTO> queryAct() {
		long channelId= ChannelId.ACT;
		List<ProChannel> list=channelService.findProChannelList(channelId);
		return ResponseInfo.success(new ActDTO(list));
//		ActDTO actDTO=new ActDTO();
//		ProChannel proChannel = channelService.findChannelById(channelId).getData();
//		List<ProDto> list = productService.queryProDtosListByChannelId(channelId);
//		if (list!=null) {
//            actDTO.setProChannel(proChannel);
//            actDTO.setList(list);
//            return ResponseInfo.success(actDTO);
//        }else
//            throw new RuntimeException("暂无活动商品");
	}
}
