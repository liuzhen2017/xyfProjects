package com.xinyunfu.service.impl;

import com.xinyunfu.model.PayLog;
import com.xinyunfu.sao.VolumeMarketSao;
import com.xinyunfu.mapper.PayLogMapper;
import com.xinyunfu.service.IPayLogService;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-07-08
 */
@Service
@Slf4j
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements IPayLogService {
	
	
	@Autowired
	public VolumeMarketSao sao;
	
	
	public static final String applicationName = "orderTree"; 
	
	/**
	 *	 提交卷集市，消耗万能卷
	 * @param userNo
	 * @param money
	 */
	public void payMoney(PayLog pl){

		if(0==pl.getIsSubmit()) { //提交了转让
			try {
			  String str = sao.payUser(pl.getUserNo(),applicationName,pl.getSourceUser(),pl.getPayNo());
			  log.info("feign call custom volume market result ===>>{}",str);
			  str = str.length()>100?str.substring(0, 99):str;
			  pl.setResultJson(str);
			  this.updateById(pl);
			}catch (Exception e) {
				pl.setResultJson("exception:"+e.getMessage());
				log.info("==>> submit VolumeMarket fail !!!!  request params:{}",pl);
			}
		}
	}

}
