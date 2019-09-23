package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProDetailsMapper;
import com.ruoyi.domain.ProDetails;
import com.ruoyi.service.IProDetailsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品参数详情 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProDetailsServiceImpl implements IProDetailsService 
{
	@Autowired
	private ProDetailsMapper proDetailsMapper;

	/**
     * 查询商品参数详情信息
     * 
     * @param proId 商品参数详情ID
     * @return 商品参数详情信息
     */
    @Override
	public ProDetails selectProDetailsById(Long proId)
	{
	    return proDetailsMapper.selectProDetailsById(proId);
	}
	
	/**
     * 查询商品参数详情列表
     * 
     * @param proDetails 商品参数详情信息
     * @return 商品参数详情集合
     */
	@Override
	public List<ProDetails> selectProDetailsList(ProDetails proDetails)
	{
	    return proDetailsMapper.selectProDetailsList(proDetails);
	}
	
    /**
     * 新增商品参数详情
     * 
     * @param proDetails 商品参数详情信息
     * @return 结果
     */
	@Override
	public int insertProDetails(ProDetails proDetails)
	{
	    return proDetailsMapper.insertProDetails(proDetails);
	}
	
	/**
     * 修改商品参数详情
     * 
     * @param proDetails 商品参数详情信息
     * @return 结果
     */
	@Override
	public int updateProDetails(ProDetails proDetails)
	{
	    return proDetailsMapper.updateProDetails(proDetails);
	}

	/**
     * 删除商品参数详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProDetailsByIds(String ids)
	{
		return proDetailsMapper.deleteProDetailsByIds(Convert.toStrArray(ids));
	}
	
}
