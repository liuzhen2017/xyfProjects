package com.ruoyi.service;

import com.ruoyi.domain.SupplyChainAcc;
import java.util.List;

/**
 * 供应链账户 服务层
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
public interface ISupplyChainAccService 
{
	/**
     * 查询供应链账户信息
     * 
     * @param id 供应链账户ID
     * @return 供应链账户信息
     */
	public SupplyChainAcc selectSupplyChainAccById(Integer id);
	
	/**
     * 查询供应链账户列表
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 供应链账户集合
     */
	public List<SupplyChainAcc> selectSupplyChainAccList(SupplyChainAcc supplyChainAcc);
	
	/**
     * 新增供应链账户
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 结果
     */
	public int insertSupplyChainAcc(SupplyChainAcc supplyChainAcc);
	
	/**
     * 修改供应链账户
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 结果
     */
	public int updateSupplyChainAcc(SupplyChainAcc supplyChainAcc);
		
	/**
     * 删除供应链账户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSupplyChainAccByIds(String ids);
	
}
