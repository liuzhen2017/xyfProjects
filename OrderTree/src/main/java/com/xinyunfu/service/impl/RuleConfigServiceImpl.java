package com.xinyunfu.service.impl;

import com.xinyunfu.model.RuleConfig;
import com.xinyunfu.mapper.RuleConfigMapper;
import com.xinyunfu.service.IRuleConfigService;

import lombok.extern.slf4j.Slf4j;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-08-22
 */
@Service
@Slf4j
public class RuleConfigServiceImpl extends ServiceImpl<RuleConfigMapper, RuleConfig> implements IRuleConfigService {

	@Autowired
	private RuleConfigMapper ruleMapper;
	
	
	public RuleConfig getRuleBean() {
		return ruleMapper.getRuleBean();
	}


	
	@PostConstruct
	public void showRules() {
		log.info("------------------------rule config  begin--------------------------");
		ruleMapper.findAll().forEach(rule ->{
			log.info("rule == >:{}",rule);
		});
		log.info("------------------------rule config  end--------------------------");
	}
	
}
