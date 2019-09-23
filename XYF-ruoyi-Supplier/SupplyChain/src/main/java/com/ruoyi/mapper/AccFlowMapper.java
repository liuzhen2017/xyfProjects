package com.ruoyi.mapper;

import com.ruoyi.domain.AccFlow;
import java.util.List;	

/**
 * 账户流水 数据层
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public interface AccFlowMapper 
{
	/**
     * 查询账户流水信息
     * 
     * @param id 账户流水ID
     * @return 账户流水信息
     */
	public AccFlow selectAccFlowById(Integer id);
	
	/**
     * 查询账户流水列表
     * 
     * @param accFlow 账户流水信息
     * @return 账户流水集合
     */
	public List<AccFlow> selectAccFlowList(AccFlow accFlow);
	
	/**
     * 新增账户流水
     * 
     * @param accFlow 账户流水信息
     * @return 结果
     */
	public int insertAccFlow(AccFlow accFlow);
	
	/**
     * 修改账户流水
     * 
     * @param accFlow 账户流水信息
     * @return 结果
     */
	public int updateAccFlow(AccFlow accFlow);
	
	/**
     * 删除账户流水
     * 
     * @param id 账户流水ID
     * @return 结果
     */
	public int deleteAccFlowById(Integer id);
	
	/**
     * 批量删除账户流水
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAccFlowByIds(String[] ids);
	
}