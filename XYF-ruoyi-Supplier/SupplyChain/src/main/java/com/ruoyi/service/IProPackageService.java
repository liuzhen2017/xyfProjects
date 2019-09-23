package com.ruoyi.service;

import com.ruoyi.domain.ProPackage;
import java.util.List;

/**
 * 套餐 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProPackageService 
{
	/**
     * 查询套餐信息
     * 
     * @param packageId 套餐ID
     * @return 套餐信息
     */
	public ProPackage selectProPackageById(Long packageId);
	
	/**
     * 查询套餐列表
     * 
     * @param proPackage 套餐信息
     * @return 套餐集合
     */
	public List<ProPackage> selectProPackageList(ProPackage proPackage);
	
	/**
     * 新增套餐
     * 
     * @param proPackage 套餐信息
     * @return 结果
     */
	public int insertProPackage(ProPackage proPackage);
	
	/**
     * 修改套餐
     * 
     * @param proPackage 套餐信息
     * @return 结果
     */
	public int updateProPackage(ProPackage proPackage);
		
	/**
     * 删除套餐信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProPackageByIds(String ids);
	
}
