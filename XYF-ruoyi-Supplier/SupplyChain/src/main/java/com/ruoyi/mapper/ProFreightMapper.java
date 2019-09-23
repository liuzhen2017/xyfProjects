package com.ruoyi.mapper;

import com.ruoyi.domain.ProFreight;
import java.util.List;	

/**
 * 店铺邮费模板 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProFreightMapper 
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
     * 删除店铺邮费模板
     * 
     * @param freightId 店铺邮费模板ID
     * @return 结果
     */
	public int deleteProFreightById(Long freightId);
	
	/**
     * 批量删除店铺邮费模板
     * 
     * @param freightIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProFreightByIds(String[] freightIds);
	
}