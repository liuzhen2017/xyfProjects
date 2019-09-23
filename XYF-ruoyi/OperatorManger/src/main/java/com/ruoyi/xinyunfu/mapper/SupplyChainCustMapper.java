package com.ruoyi.xinyunfu.mapper;

import com.ruoyi.xinyunfu.domain.SupplyChainCust;
import java.util.List;	

/**
 * 供应链 数据层
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public interface SupplyChainCustMapper 
{
	/**
     * 查询供应链信息
     * 
     * @param id 供应链ID
     * @return 供应链信息
     */
	public SupplyChainCust selectSupplyChainCustById(Integer id);
	
	/**
     * 查询供应链列表
     * 
     * @param supplyChainCust 供应链信息
     * @return 供应链集合
     */
	public List<SupplyChainCust> selectSupplyChainCustList(SupplyChainCust supplyChainCust);
	
	/**
     * 新增供应链
     * 
     * @param supplyChainCust 供应链信息
     * @return 结果
     */
	public int insertSupplyChainCust(SupplyChainCust supplyChainCust);
	
	/**
     * 修改供应链
     * 
     * @param supplyChainCust 供应链信息
     * @return 结果
     */
	public int updateSupplyChainCust(SupplyChainCust supplyChainCust);
	
	/**
     * 删除供应链
     * 
     * @param id 供应链ID
     * @return 结果
     */
	public int deleteSupplyChainCustById(Integer id);
	
	/**
     * 批量删除供应链
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyChainCustByIds(String[] ids);
	
}