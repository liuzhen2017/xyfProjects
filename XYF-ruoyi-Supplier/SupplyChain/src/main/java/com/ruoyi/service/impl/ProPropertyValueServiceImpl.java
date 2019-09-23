package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProPropertyValueMapper;
import com.ruoyi.domain.ProPropertyValue;
import com.ruoyi.service.IProPropertyValueService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品属性值 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProPropertyValueServiceImpl implements IProPropertyValueService 
{
	@Autowired
	private ProPropertyValueMapper proPropertyValueMapper;

	/**
     * 查询商品属性值信息
     * 
     * @param valueId 商品属性值ID
     * @return 商品属性值信息
     */
    @Override
	public ProPropertyValue selectProPropertyValueById(Long valueId)
	{
	    return proPropertyValueMapper.selectProPropertyValueById(valueId);
	}
	
	/**
     * 查询商品属性值列表
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 商品属性值集合
     */
	@Override
	public List<ProPropertyValue> selectProPropertyValueList(ProPropertyValue proPropertyValue)
	{
	    return proPropertyValueMapper.selectProPropertyValueList(proPropertyValue);
	}
	
    /**
     * 新增商品属性值
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 结果
     */
	@Override
	public int insertProPropertyValue(ProPropertyValue proPropertyValue)
	{
	    return proPropertyValueMapper.insertProPropertyValue(proPropertyValue);
	}
	
	/**
     * 修改商品属性值
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 结果
     */
	@Override
	public int updateProPropertyValue(ProPropertyValue proPropertyValue)
	{
	    return proPropertyValueMapper.updateProPropertyValue(proPropertyValue);
	}

	/**
     * 删除商品属性值对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProPropertyValueByIds(String ids)
	{
		return proPropertyValueMapper.deleteProPropertyValueByIds(Convert.toStrArray(ids));
	}
	
}
