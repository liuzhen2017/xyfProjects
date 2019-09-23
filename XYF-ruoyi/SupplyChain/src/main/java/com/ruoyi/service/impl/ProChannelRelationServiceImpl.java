package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProChannelRelationMapper;
import com.ruoyi.domain.ProChannelRelation;
import com.ruoyi.service.IProChannelRelationService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品分类关系 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProChannelRelationServiceImpl implements IProChannelRelationService 
{
	@Autowired
	private ProChannelRelationMapper proChannelRelationMapper;

	/**
     * 查询商品分类关系信息
     * 
     * @param id 商品分类关系ID
     * @return 商品分类关系信息
     */
    @Override
	public ProChannelRelation selectProChannelRelationById(Long id)
	{
	    return proChannelRelationMapper.selectProChannelRelationById(id);
	}
	
	/**
     * 查询商品分类关系列表
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 商品分类关系集合
     */
	@Override
	public List<ProChannelRelation> selectProChannelRelationList(ProChannelRelation proChannelRelation)
	{
	    return proChannelRelationMapper.selectProChannelRelationList(proChannelRelation);
	}
	
    /**
     * 新增商品分类关系
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 结果
     */
	@Override
	public int insertProChannelRelation(ProChannelRelation proChannelRelation)
	{
	    return proChannelRelationMapper.insertProChannelRelation(proChannelRelation);
	}
	
	/**
     * 修改商品分类关系
     * 
     * @param proChannelRelation 商品分类关系信息
     * @return 结果
     */
	@Override
	public int updateProChannelRelation(ProChannelRelation proChannelRelation)
	{
	    return proChannelRelationMapper.updateProChannelRelation(proChannelRelation);
	}

	/**
     * 删除商品分类关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProChannelRelationByIds(String ids)
	{
		return proChannelRelationMapper.deleteProChannelRelationByIds(Convert.toStrArray(ids));
	}
	
}
