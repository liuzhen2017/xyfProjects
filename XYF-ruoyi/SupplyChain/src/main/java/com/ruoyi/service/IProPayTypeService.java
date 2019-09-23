package com.ruoyi.service;

import com.ruoyi.domain.ProPayType;
import java.util.List;

/**
 * 商品支付类型关联 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProPayTypeService 
{
	/**
     * 查询商品支付类型关联信息
     * 
     * @param proId 商品支付类型关联ID
     * @return 商品支付类型关联信息
     */
	public ProPayType selectProPayTypeById(Long proId);
	
	/**
     * 查询商品支付类型关联列表
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 商品支付类型关联集合
     */
	public List<ProPayType> selectProPayTypeList(ProPayType proPayType);
	
	/**
     * 新增商品支付类型关联
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 结果
     */
	public int insertProPayType(ProPayType proPayType);
	
	/**
     * 修改商品支付类型关联
     * 
     * @param proPayType 商品支付类型关联信息
     * @return 结果
     */
	public int updateProPayType(ProPayType proPayType);
		
	/**
     * 删除商品支付类型关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProPayTypeByIds(String ids);
	
}
