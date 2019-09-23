package com.ruoyi.xinyunfu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xinyunfu.mapper.CustUserMapper;
import com.ruoyi.xinyunfu.domain.CustUser;
import com.ruoyi.xinyunfu.service.ICustUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Service
public class CustUserServiceImpl implements ICustUserService 
{
	@Autowired
	private CustUserMapper custUserMapper;

	/**
     * 查询用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
	public CustUser selectCustUserById(Integer id)
	{
	    return custUserMapper.selectCustUserById(id);
	}
	
	/**
     * 查询用户列表
     * 
     * @param custUser 用户信息
     * @return 用户集合
     */
	@Override
	public List<CustUser> selectCustUserList(CustUser custUser)
	{
	    return custUserMapper.selectCustUserList(custUser);
	}
	
    /**
     * 新增用户
     * 
     * @param custUser 用户信息
     * @return 结果
     */
	@Override
	public int insertCustUser(CustUser custUser)
	{
	    return custUserMapper.insertCustUser(custUser);
	}
	
	/**
     * 修改用户
     * 
     * @param custUser 用户信息
     * @return 结果
     */
	@Override
	public int updateCustUser(CustUser custUser)
	{
	    return custUserMapper.updateCustUser(custUser);
	}

	/**
     * 删除用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCustUserByIds(String ids)
	{
		return custUserMapper.deleteCustUserByIds(Convert.toStrArray(ids));
	}
	
}
