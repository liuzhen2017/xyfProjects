package com.ruoyi.xinyunfu.mapper;

import com.ruoyi.xinyunfu.domain.AcctManger;
import java.util.List;	

/**
 * 账户 数据层
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public interface AcctMangerMapper 
{
	/**
     * 查询账户信息
     * 
     * @param id 账户ID
     * @return 账户信息
     */
	public AcctManger selectAcctMangerById(Integer id);
	
	/**
     * 查询账户列表
     * 
     * @param acctManger 账户信息
     * @return 账户集合
     */
	public List<AcctManger> selectAcctMangerList(AcctManger acctManger);
	
	/**
     * 新增账户
     * 
     * @param acctManger 账户信息
     * @return 结果
     */
	public int insertAcctManger(AcctManger acctManger);
	
	/**
     * 修改账户
     * 
     * @param acctManger 账户信息
     * @return 结果
     */
	public int updateAcctManger(AcctManger acctManger);
	
	/**
     * 删除账户
     * 
     * @param id 账户ID
     * @return 结果
     */
	public int deleteAcctMangerById(Integer id);
	
	/**
     * 批量删除账户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAcctMangerByIds(String[] ids);
	
}