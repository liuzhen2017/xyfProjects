package com.ruoyi.service;

import com.ruoyi.domain.UserWallet;
import java.util.List;

/**
 * 用户钱包 服务层
 * 
 * @author ruoyi
 * @date 2019-08-20
 */
public interface IUserWalletService 
{
	/**
     * 查询用户钱包信息
     * 
     * @param id 用户钱包ID
     * @return 用户钱包信息
     */
	public UserWallet selectUserWalletById(Long id);
	
	/**
     * 查询用户钱包列表
     * 
     * @param userWallet 用户钱包信息
     * @return 用户钱包集合
     */
	public List<UserWallet> selectUserWalletList(UserWallet userWallet);
	
	/**
     * 新增用户钱包
     * 
     * @param userWallet 用户钱包信息
     * @return 结果
     */
	public int insertUserWallet(UserWallet userWallet);
	
	/**
     * 修改用户钱包
     * 
     * @param userWallet 用户钱包信息
     * @return 结果
     */
	public int updateUserWallet(UserWallet userWallet);
		
	/**
     * 删除用户钱包信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserWalletByIds(String ids);
	
}
