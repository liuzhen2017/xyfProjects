package com.xinyunfu.task;

import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.service.impl.OrderTreeServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jace
 *	单个线程操作
 */
@Slf4j
public class Wroker implements Runnable{
	
	
	
	private UserPackageOrder upo = null;
	
	private OrderTreeServiceImpl service = null;
	
	
    public Wroker(UserPackageOrder upo,OrderTreeServiceImpl service) {
    	this.upo = upo;
    	this.service = service;
    }

    
	@Override
	public void run() {
	   log.info("开始执行订单树任务："+upo);
		try {
			service.excuteOrderTree(upo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
