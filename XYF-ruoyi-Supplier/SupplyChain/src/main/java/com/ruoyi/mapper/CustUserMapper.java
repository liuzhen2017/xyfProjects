package com.ruoyi.mapper;

import com.ruoyi.domain.CustUser;
import java.util.List;	

/**
 * 用户 数据层
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
public interface CustUserMapper 
{
	/**
     * 查询用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
	public CustUser selectCustUserById(Integer id);
	
	/**
     * 查询用户列表
     * 
     * @param custUser 用户信息
     * @return 用户集合
     */
	public List<CustUser> selectCustUserList(CustUser custUser);
	
	/**
     * 新增用户
     * 
     * @param custUser 用户信息
     * @return 结果
     */
	public int insertCustUser(CustUser custUser);
	
	/**
     * 修改用户
     * 
     * @param custUser 用户信息
     * @return 结果
     */
	public int updateCustUser(CustUser custUser);
	
	/**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 结果
     */
	public int deleteCustUserById(Integer id);
	
	/**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCustUserByIds(String[] ids);
	
}