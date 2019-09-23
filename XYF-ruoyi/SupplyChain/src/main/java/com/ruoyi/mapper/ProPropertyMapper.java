package com.ruoyi.mapper;

import com.ruoyi.domain.ProProperty;
import java.util.List;	

/**
 * 商品属性 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProPropertyMapper 
{
	/**
     * 查询商品属性信息
     * 
     * @param propertyId 商品属性ID
     * @return 商品属性信息
     */
	public ProProperty selectProPropertyById(Long propertyId);
	
	/**
     * 查询商品属性列表
     * 
     * @param proProperty 商品属性信息
     * @return 商品属性集合
     */
	public List<ProProperty> selectProPropertyList(ProProperty proProperty);
	
	/**
     * 新增商品属性
     * 
     * @param proProperty 商品属性信息
     * @return 结果
     */
	public int insertProProperty(ProProperty proProperty);
	
	/**
     * 修改商品属性
     * 
     * @param proProperty 商品属性信息
     * @return 结果
     */
	public int updateProProperty(ProProperty proProperty);
	
	/**
     * 删除商品属性
     * 
     * @param propertyId 商品属性ID
     * @return 结果
     */
	public int deleteProPropertyById(Long propertyId);
	
	/**
     * 批量删除商品属性
     * 
     * @param propertyIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProPropertyByIds(String[] propertyIds);

    List<ProProperty> selectProPropertyByProId(Long proId);
}