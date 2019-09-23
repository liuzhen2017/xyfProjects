package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.SupplyChainAccMapper;
import com.ruoyi.domain.SupplyChainAcc;
import com.ruoyi.service.ISupplyChainAccService;
import com.ruoyi.common.core.text.Convert;

/**
 * 供应链账户 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Service
public class SupplyChainAccServiceImpl implements ISupplyChainAccService 
{
	@Autowired
	private SupplyChainAccMapper supplyChainAccMapper;

	/**
     * 查询供应链账户信息
     * 
     * @param id 供应链账户ID
     * @return 供应链账户信息
     */
    @Override
	public SupplyChainAcc selectSupplyChainAccById(Integer id)
	{
	    return supplyChainAccMapper.selectSupplyChainAccById(id);
	}
	
	/**
     * 查询供应链账户列表
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 供应链账户集合
     */
	@Override
	public List<SupplyChainAcc> selectSupplyChainAccList(SupplyChainAcc supplyChainAcc)
	{
	    return supplyChainAccMapper.selectSupplyChainAccList(supplyChainAcc);
	}
	
    /**
     * 新增供应链账户
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 结果
     */
	@Override
	public int insertSupplyChainAcc(SupplyChainAcc supplyChainAcc)
	{
	    return supplyChainAccMapper.insertSupplyChainAcc(supplyChainAcc);
	}
	
	/**
     * 修改供应链账户
     * 
     * @param supplyChainAcc 供应链账户信息
     * @return 结果
     */
	@Override
	public int updateSupplyChainAcc(SupplyChainAcc supplyChainAcc)
	{
	    return supplyChainAccMapper.updateSupplyChainAcc(supplyChainAcc);
	}

	/**
     * 删除供应链账户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyChainAccByIds(String ids)
	{
		return supplyChainAccMapper.deleteSupplyChainAccByIds(Convert.toStrArray(ids));
	}
	
}
