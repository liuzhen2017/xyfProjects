package com.xinyunfu.service;


import com.xinyunfu.dto.jd.param.JDAreaLimitParam;
import com.xinyunfu.dto.jd.param.JDPostParam;
import com.xinyunfu.dto.jd.param.JDSaleSkusParam;
import com.xinyunfu.dto.jd.param.JDStockParam;
import com.xinyunfu.jace.utils.ResponseInfo;

public interface PreOrderService {

      ResponseInfo<String> queryFreight(JDPostParam param);

      ResponseInfo checkSale(JDSaleSkusParam skuIds);

      ResponseInfo queryAreaLimit(JDAreaLimitParam param);

      ResponseInfo queryStock(JDStockParam param);

}
