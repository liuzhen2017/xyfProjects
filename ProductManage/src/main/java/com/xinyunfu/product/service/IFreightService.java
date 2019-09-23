package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.product.dto.FindFreightDTO;
import com.xinyunfu.product.model.Freight;

import java.math.BigDecimal;

public interface IFreightService extends IService<Freight>{

	BigDecimal queryPostage(Long proId);

	BigDecimal queryPostageByIdAndCount(String province,String city,long proId,long skuId,int count);

	
}
