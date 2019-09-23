package com.ruoyi.service;

import com.ruoyi.domain.ProFreight;
import java.util.List;

/**
 * 店铺邮费模板 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProFreightService 
{
	/**
     * 查询店铺邮费模板信息
     * 
     * @param freightId 店铺邮费模板ID
     * @return 店铺邮费模板信息
     */
	public ProFreight selectProFreightById(Long freightId);
	
	/**
     * 查询店铺邮费模板列表
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 店铺邮费模板集合
     */
	public List<ProFreight> selectProFreightList(ProFreight proFreight);
	
	/**
     * 新增店铺邮费模板
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 结果
     */
	public int insertProFreight(ProFreight proFreight);
	
	/**
     * 修改店铺邮费模板
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 结果
     */
	public int updateProFreight(ProFreight proFreight);
		
	/**
     * 删除店铺邮费模板信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProFreightByIds(String ids);
	
}
