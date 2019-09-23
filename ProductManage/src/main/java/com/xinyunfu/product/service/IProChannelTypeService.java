package com.xinyunfu.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.product.model.ProChannelType;

import java.util.List;

/**
 * 商品分类类型 服务层
 * 
 * @author TYM
 * @date 2019-08-30
 */
public interface IProChannelTypeService extends IService<ProChannelType>
{


    List<ProChannelType> findAll();
}
