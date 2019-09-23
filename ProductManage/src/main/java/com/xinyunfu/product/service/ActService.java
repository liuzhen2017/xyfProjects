package com.xinyunfu.product.service;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ActDTO;

public interface ActService {


	ResponseInfo<ActDTO> queryAct();
}
