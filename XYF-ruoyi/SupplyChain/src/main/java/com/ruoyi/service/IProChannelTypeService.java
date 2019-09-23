package com.ruoyi.service;

import com.ruoyi.domain.ProChannelType;
import java.util.List;

/**
 * 商品类型分类 服务层
 * 
 * @author ruoyi
 * @date 2019-08-30
 */
public interface IProChannelTypeService 
{
	/**
     * 查询商品类型分类信息
     * 
     * @param channelTypeId 商品类型分类ID
     * @return 商品类型分类信息
     */
	public ProChannelType selectProChannelTypeById(Long channelTypeId);
	
	/**
     * 查询商品类型分类列表
     * 
     * @param proChannelType 商品类型分类信息
     * @return 商品类型分类集合
     */
	public List<ProChannelType> selectProChannelTypeList(ProChannelType proChannelType);
	
	/**
     * 新增商品类型分类
     * 
     * @param proChannelType 商品类型分类信息
     * @return 结果
     */
	public int insertProChannelType(ProChannelType proChannelType);
	
	/**
     * 修改商品类型分类
     * 
     * @param proChannelType 商品类型分类信息
     * @return 结果
     */
	public int updateProChannelType(ProChannelType proChannelType);
		
	/**
     * 删除商品类型分类信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProChannelTypeByIds(String ids);
	
}
