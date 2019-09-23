package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.product.mapper.ProChannelTypeMapper;
import com.xinyunfu.product.model.ProChannelType;
import com.xinyunfu.product.service.IProChannelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 商品类型分类 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-30
 */
@Service
public class ProChannelTypeServiceImpl extends ServiceImpl<ProChannelTypeMapper,ProChannelType> implements IProChannelTypeService
{
	@Autowired
	private ProChannelTypeMapper proChannelTypeMapper;


    @Override
    public List<ProChannelType> findAll() {
        return proChannelTypeMapper.findAll();
    }
}
