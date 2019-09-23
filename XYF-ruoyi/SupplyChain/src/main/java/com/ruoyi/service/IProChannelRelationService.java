package com.ruoyi.service;

import com.ruoyi.domain.ProChannelRelation;
import java.util.List;

/**
 * 商品分类关系 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProChannelRelationService 
{
	/**
     * 查询商品分类关系信息
     * 
     * @param id 商品分类关系ID
     * @return 商品分类关系信息
     */
	public ProChannelRelation selectProChannelRelationById(Long id);
	
	/**
     * 查询商品分类关系列表
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 商品分类关系集合
     */
	public List<ProChannelRelation> selectProChannelRelationList(ProChannelRelation proChannelRelation);
	
	/**
     * 新增商品分类关系
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 结果
     */
	public int insertProChannelRelation(ProChannelRelation proChannelRelation);
	
	/**
     * 修改商品分类关系
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 结果
     */
	public int updateProChannelRelation(ProChannelRelation proChannelRelation);
		
	/**
     * 删除商品分类关系信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProChannelRelationByIds(String ids);
	
}
