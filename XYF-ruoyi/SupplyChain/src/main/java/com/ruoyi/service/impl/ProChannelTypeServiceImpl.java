package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProChannelTypeMapper;
import com.ruoyi.domain.ProChannelType;
import com.ruoyi.service.IProChannelTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品类型分类 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-30
 */
@Service
public class ProChannelTypeServiceImpl implements IProChannelTypeService 
{
	@Autowired
	private ProChannelTypeMapper proChannelTypeMapper;

	/**
     * 查询商品类型分类信息
     * 
     * @param channelTypeId 商品类型分类ID
     * @return 商品类型分类信息
     */
    @Override
	public ProChannelType selectProChannelTypeById(Long channelTypeId)
	{
	    return proChannelTypeMapper.selectProChannelTypeById(channelTypeId);
	}
	
	/**
     * 查询商品类型分类列表
     * 
     * @param proChannelType 商品类型分类信息
     * @return 商品类型分类集合
     */
	@Override
	public List<ProChannelType> selectProChannelTypeList(ProChannelType proChannelType)
	{
	    return proChannelTypeMapper.selectProChannelTypeList(proChannelType);
	}
	
    /**
     * 新增商品类型分类
     * 
     * @param proChannelType 商品类型分类信息
     * @return 结果
     */
	@Override
	public int insertProChannelType(ProChannelType proChannelType)
	{
	    return proChannelTypeMapper.insertProChannelType(proChannelType);
	}
	
	/**
     * 修改商品类型分类
     * 
     * @param proChannelType 商品类型分类信息
     * @return 结果
     */
	@Override
	public int updateProChannelType(ProChannelType proChannelType)
	{
	    return proChannelTypeMapper.updateProChannelType(proChannelType);
	}

	/**
     * 删除商品类型分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProChannelTypeByIds(String ids)
	{
		return proChannelTypeMapper.deleteProChannelTypeByIds(Convert.toStrArray(ids));
	}
	
}
