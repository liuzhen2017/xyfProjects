package com.xinyunfu.task;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.service.impl.OrderTreeServiceImpl;
import com.xinyunfu.service.impl.UserPackageOrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jace
 *    	         订单树
 */
@Component
@Slf4j
public class OrderTreeTasker implements InitializingBean{

	
	@Resource(name="singleWorker")
	private ExecutorService executorService;
	
	
	@Autowired
	private UserPackageOrderServiceImpl upoService;
	
	@Autowired
	private OrderTreeServiceImpl otService;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("*--------------------------------------------------------------------------------*");
		List<UserPackageOrder> list = upoService.findAll();
		
        for(UserPackageOrder upo : list) {
        	String userNo = upo.getUserNo();
        	
        	//套餐有剩余数量，同时不存在有效的节点数，才可以自动执行一次
        	if(upo.getVaildCount()!=0 && otService.getValidNode(upo)==0&&(null == otService.getVailNode(userNo))) {
        		log.info("带执行订单树业务 ==> upo:[{}]",upo);
        		executorService.execute(new Wroker(upo, otService));
        	}
        }
	}

	

}
