package com.ruoyi.service;

import com.ruoyi.domain.User;
import com.ruoyi.dto.PushMsgDto;

import java.util.List;

/**
 * 用户 服务层
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
public interface IUserService 
{
	/**
     * 查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
	public User selectUserById(Long userId);
	
	/**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
	public List<User> selectUserList(User user);
	
	/**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	public int insertUser(PushMsgDto msgDto);
	
	/**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	public int updateUser(User user);
		
	/**
     * 删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserByIds(String ids);
	
}
