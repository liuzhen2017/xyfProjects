package com.xinyunfu.rule;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.UserPackageOrder;

public interface EngineRule {

	
	
	public ResponseInfo<String> handler(UserPackageOrder upo) throws Exception;
}
