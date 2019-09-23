package com.xinyunfu.rule.impl;

import org.springframework.stereotype.Component;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.rule.EngineRule;

/**
 * @author jace
 *     
 *	推荐关系6缺1，再6缺1，推荐关系6缺6
 */

@Component
public class LessOnePriority implements EngineRule{
	
	
	

	@Override
	public ResponseInfo<String> handler(UserPackageOrder upo) throws Exception {
		
		return null;
	}

	
	
}
