package com.xinyunfu.product.service.impl;

import com.xinyunfu.product.constant.ChannelId;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.service.HomePageChannelsService;
import com.xinyunfu.product.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HomePageChannelServiceImpl implements HomePageChannelsService{

	@Autowired
	private IChannelService channelService;
	@Override
	public List<ProChannel> queryProChannel() {
		long parentId= ChannelId.HOME_PAGE_CHANNEL;
		List<ProChannel> list = channelService.findProChannelList(parentId);
		if (list==null || list.isEmpty())
		    throw new RuntimeException("暂无分类");
		else
		    return list;
	}

}
