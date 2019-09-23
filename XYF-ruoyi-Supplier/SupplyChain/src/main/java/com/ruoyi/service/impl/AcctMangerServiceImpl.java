package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.AcctMangerMapper;
import com.ruoyi.domain.AcctManger;
import com.ruoyi.service.IAcctMangerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 账户 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class AcctMangerServiceImpl implements IAcctMangerService 
{
	@Autowired
	private AcctMangerMapper acctMangerMapper;

	/**
     * 查询账户信息
     * 
     * @param id 账户ID
     * @return 账户信息
     */
    @Override
	public AcctManger selectAcctMangerById(Integer id)
	{
	    return acctMangerMapper.selectAcctMangerById(id);
	}
	
	/**
     * 查询账户列表
     * 
     * @param acctManger 账户信息
     * @return 账户集合
     */
	@Override
	public List<AcctManger> selectAcctMangerList(AcctManger acctManger)
	{
	    return acctMangerMapper.selectAcctMangerList(acctManger);
	}
	
    /**
     * 新增账户
     * 
     * @param acctManger 账户信息
     * @return 结果
     */
	@Override
	public int insertAcctManger(AcctManger acctManger)
	{
	    return acctMangerMapper.insertAcctManger(acctManger);
	}
	
	/**
     * 修改账户
     * 
     * @param acctManger 账户信息
     * @return 结果
     */
	@Override
	public int updateAcctManger(AcctManger acctManger)
	{
	    return acctMangerMapper.updateAcctManger(acctManger);
	}

	/**
     * 删除账户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAcctMangerByIds(String ids)
	{
		return acctMangerMapper.deleteAcctMangerByIds(Convert.toStrArray(ids));
	}
	
}
