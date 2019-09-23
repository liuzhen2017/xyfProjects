package com.ruoyi.mapper;

import com.ruoyi.domain.PayType;
import java.util.List;	

/**
 * 支付类型 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface PayTypeMapper 
{
	/**
     * 查询支付类型信息
     * 
     * @param id 支付类型ID
     * @return 支付类型信息
     */
	public PayType selectPayTypeById(Long id);
	
	/**
     * 查询支付类型列表
     * 
     * @param payType 支付类型信息
     * @return 支付类型集合
     */
	public List<PayType> selectPayTypeList(PayType payType);
	
	/**
     * 新增支付类型
     * 
     * @param payType 支付类型信息
     * @return 结果
     */
	public int insertPayType(PayType payType);
	
	/**
     * 修改支付类型
     * 
     * @param payType 支付类型信息
     * @return 结果
     */
	public int updatePayType(PayType payType);
	
	/**
     * 删除支付类型
     * 
     * @param id 支付类型ID
     * @return 结果
     */
	public int deletePayTypeById(Long id);
	
	/**
     * 批量删除支付类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePayTypeByIds(String[] ids);
	
}