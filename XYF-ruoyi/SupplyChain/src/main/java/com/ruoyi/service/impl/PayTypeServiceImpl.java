package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.PayTypeMapper;
import com.ruoyi.domain.PayType;
import com.ruoyi.service.IPayTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 支付类型 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class PayTypeServiceImpl implements IPayTypeService 
{
	@Autowired
	private PayTypeMapper payTypeMapper;

	/**
     * 查询支付类型信息
     * 
     * @param id 支付类型ID
     * @return 支付类型信息
     */
    @Override
	public PayType selectPayTypeById(Long id)
	{
	    return payTypeMapper.selectPayTypeById(id);
	}
	
	/**
     * 查询支付类型列表
     * 
     * @param payType 支付类型信息
     * @return 支付类型集合
     */
	@Override
	public List<PayType> selectPayTypeList(PayType payType)
	{
	    return payTypeMapper.selectPayTypeList(payType);
	}
	
    /**
     * 新增支付类型
     * 
     * @param payType 支付类型信息
     * @return 结果
     */
	@Override
	public int insertPayType(PayType payType)
	{
	    return payTypeMapper.insertPayType(payType);
	}
	
	/**
     * 修改支付类型
     * 
     * @param payType 支付类型信息
     * @return 结果
     */
	@Override
	public int updatePayType(PayType payType)
	{
	    return payTypeMapper.updatePayType(payType);
	}

	/**
     * 删除支付类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePayTypeByIds(String ids)
	{
		return payTypeMapper.deletePayTypeByIds(Convert.toStrArray(ids));
	}
	
}
