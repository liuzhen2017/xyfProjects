package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.UserWalletMapper;
import com.ruoyi.domain.UserWallet;
import com.ruoyi.service.IUserWalletService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户钱包 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-20
 */
@Service
public class UserWalletServiceImpl implements IUserWalletService 
{
	@Autowired
	private UserWalletMapper userWalletMapper;

	/**
     * 查询用户钱包信息
     * 
     * @param id 用户钱包ID
     * @return 用户钱包信息
     */
    @Override
	public UserWallet selectUserWalletById(Long id)
	{
	    return userWalletMapper.selectUserWalletById(id);
	}
	
	/**
     * 查询用户钱包列表
     * 
     * @param userWallet 用户钱包信息
     * @return 用户钱包集合
     */
	@Override
	public List<UserWallet> selectUserWalletList(UserWallet userWallet)
	{
	    return userWalletMapper.selectUserWalletList(userWallet);
	}
	
    /**
     * 新增用户钱包
     * 
     * @param userWallet 用户钱包信息
     * @return 结果
     */
	@Override
	public int insertUserWallet(UserWallet userWallet)
	{
	    return userWalletMapper.insertUserWallet(userWallet);
	}
	
	/**
     * 修改用户钱包
     * 
     * @param userWallet 用户钱包信息
     * @return 结果
     */
	@Override
	public int updateUserWallet(UserWallet userWallet)
	{
	    return userWalletMapper.updateUserWallet(userWallet);
	}

	/**
     * 删除用户钱包对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserWalletByIds(String ids)
	{
		return userWalletMapper.deleteUserWalletByIds(Convert.toStrArray(ids));
	}
	
}
