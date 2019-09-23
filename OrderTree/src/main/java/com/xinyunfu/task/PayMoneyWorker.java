package com.xinyunfu.task;


import com.xinyunfu.model.PayLog;
import com.xinyunfu.service.impl.PayLogServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayMoneyWorker implements Runnable{

	
	
	private PayLogServiceImpl payService;
	
    private PayLog pl;
    
	public PayMoneyWorker(PayLog pl,PayLogServiceImpl payService){
            this.pl = pl;
            this.payService = payService;
	}
	
	
	@Override
	public void run() {
		log.info("----------------------payWorker run 提交消耗万能卷  {} -------------------------------------",pl);
        payService.payMoney(pl);		
	}
	

}
