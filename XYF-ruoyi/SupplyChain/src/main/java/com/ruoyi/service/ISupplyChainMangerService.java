package com.ruoyi.service;

import com.ruoyi.domain.SupplyChainManger;
import java.util.List;

/**
 * 供应链 服务层
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
public interface ISupplyChainMangerService 
{
	/**
     * 查询供应链信息
     * 
     * @param id 供应链ID
     * @return 供应链信息
     */
	public SupplyChainManger selectSupplyChainMangerById(Integer id);
	
	/**
     * 查询供应链列表
     * 
     * @param supplyChainManger 供应链信息
     * @return 供应链集合
     */
	public List<SupplyChainManger> selectSupplyChainMangerList(SupplyChainManger supplyChainManger);
	
	/**
     * 新增供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	public int insertSupplyChainManger(SupplyChainManger supplyChainManger);
	
	/**
     * 修改供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	public int updateSupplyChainManger(SupplyChainManger supplyChainManger);
		
	/**
     * 删除供应链信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyChainMangerByIds(String ids);
	
}
