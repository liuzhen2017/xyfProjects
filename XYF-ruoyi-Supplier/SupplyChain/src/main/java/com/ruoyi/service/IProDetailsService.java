package com.ruoyi.service;

import com.ruoyi.domain.ProDetails;
import java.util.List;

/**
 * 商品参数详情 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProDetailsService 
{
	/**
     * 查询商品参数详情信息
     * 
     * @param proId 商品参数详情ID
     * @return 商品参数详情信息
     */
	public ProDetails selectProDetailsById(Long proId);
	
	/**
     * 查询商品参数详情列表
     * 
     * @param proDetails 商品参数详情信息
     * @return 商品参数详情集合
     */
	public List<ProDetails> selectProDetailsList(ProDetails proDetails);
	
	/**
     * 新增商品参数详情
     * 
     * @param proDetails 商品参数详情信息
     * @return 结果
     */
	public int insertProDetails(ProDetails proDetails);
	
	/**
     * 修改商品参数详情
     * 
     * @param proDetails 商品参数详情信息
     * @return 结果
     */
	public int updateProDetails(ProDetails proDetails);
		
	/**
     * 删除商品参数详情信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProDetailsByIds(String ids);
	
}
