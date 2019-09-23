package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProPayTypeMapper;
import com.ruoyi.domain.ProPayType;
import com.ruoyi.service.IProPayTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品支付类型关联 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProPayTypeServiceImpl implements IProPayTypeService 
{
	@Autowired
	private ProPayTypeMapper proPayTypeMapper;

	/**
     * 查询商品支付类型关联信息
     * 
     * @param proId 商品支付类型关联ID
     * @return 商品支付类型关联信息
     */
    @Override
	public ProPayType selectProPayTypeById(Long proId)
	{
	    return proPayTypeMapper.selectProPayTypeById(proId);
	}
	
	/**
     * 查询商品支付类型关联列表
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 商品支付类型关联集合
     */
	@Override
	public List<ProPayType> selectProPayTypeList(ProPayType proPayType)
	{
	    return proPayTypeMapper.selectProPayTypeList(proPayType);
	}
	
    /**
     * 新增商品支付类型关联
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 结果
     */
	@Override
	public int insertProPayType(ProPayType proPayType)
	{
	    return proPayTypeMapper.insertProPayType(proPayType);
	}
	
	/**
     * 修改商品支付类型关联
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 结果
     */
	@Override
	public int updateProPayType(ProPayType proPayType)
	{
	    return proPayTypeMapper.updateProPayType(proPayType);
	}

	/**
     * 删除商品支付类型关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProPayTypeByIds(String ids)
	{
		return proPayTypeMapper.deleteProPayTypeByIds(Convert.toStrArray(ids));
	}
	
}
