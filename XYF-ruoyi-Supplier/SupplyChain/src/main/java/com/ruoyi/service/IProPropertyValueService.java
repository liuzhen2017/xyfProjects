package com.ruoyi.service;

import com.ruoyi.domain.ProPropertyValue;
import java.util.List;

/**
 * 商品属性值 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProPropertyValueService 
{
	/**
     * 查询商品属性值信息
     * 
     * @param valueId 商品属性值ID
     * @return 商品属性值信息
     */
	public ProPropertyValue selectProPropertyValueById(Long valueId);
	
	/**
     * 查询商品属性值列表
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 商品属性值集合
     */
	public List<ProPropertyValue> selectProPropertyValueList(ProPropertyValue proPropertyValue);
	
	/**
     * 新增商品属性值
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 结果
     */
	public int insertProPropertyValue(ProPropertyValue proPropertyValue);
	
	/**
     * 修改商品属性值
     * 
     * @param proPropertyValue 商品属性值信息
     * @return 结果
     */
	public int updateProPropertyValue(ProPropertyValue proPropertyValue);
		
	/**
     * 删除商品属性值信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProPropertyValueByIds(String ids);
	
}
