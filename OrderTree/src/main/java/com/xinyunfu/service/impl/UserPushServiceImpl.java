package com.xinyunfu.service.impl;

import com.xinyunfu.model.UserPush;
import com.xinyunfu.mapper.UserPushMapper;
import com.xinyunfu.service.IUserPushService;
import com.xinyunfu.utils.OrderUtils;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-07-04
 */
@Service
@Slf4j
public class UserPushServiceImpl extends ServiceImpl<UserPushMapper, UserPush> implements IUserPushService,InitializingBean{

	
	@Autowired
	UserPushMapper upMapper;
	
	@Autowired
	RedisCommonUtil redis;
	
	
	public void setPushLinked() {
		List<UserPush> ups = upMapper.findAll();
		for(UserPush up : ups) {
			log.info(up.getUserNo()+"============"+up.getPusherNoLinked());
			redis.setCache(OrderUtils.createPushlistKey(up.getUserNo()), up.getPusherNoLinked());
		}
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		this.setPushLinked();
	}


}
