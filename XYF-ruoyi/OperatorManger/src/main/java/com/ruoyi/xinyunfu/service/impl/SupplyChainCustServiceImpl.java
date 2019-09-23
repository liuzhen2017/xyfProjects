package com.ruoyi.xinyunfu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xinyunfu.mapper.SupplyChainCustMapper;
import com.ruoyi.xinyunfu.domain.SupplyChainCust;
import com.ruoyi.xinyunfu.service.ISupplyChainCustService;
import com.ruoyi.common.core.text.Convert;

/**
 * 供应链 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Service
public class SupplyChainCustServiceImpl implements ISupplyChainCustService 
{
	@Autowired
	private SupplyChainCustMapper supplyChainCustMapper;

	/**
     * 查询供应链信息
     * 
     * @param id 供应链ID
     * @return 供应链信息
     */
    @Override
	public SupplyChainCust selectSupplyChainCustById(Integer id)
	{
	    return supplyChainCustMapper.selectSupplyChainCustById(id);
	}
	
	/**
     * 查询供应链列表
     * 
     * @param supplyChainCust 供应链信息
     * @return 供应链集合
     */
	@Override
	public List<SupplyChainCust> selectSupplyChainCustList(SupplyChainCust supplyChainCust)
	{
	    return supplyChainCustMapper.selectSupplyChainCustList(supplyChainCust);
	}
	
    /**
     * 新增供应链
     * 
     * @param supplyChainCust 供应链信息
     * @return 结果
     */
	@Override
	public int insertSupplyChainCust(SupplyChainCust supplyChainCust)
	{
	    return supplyChainCustMapper.insertSupplyChainCust(supplyChainCust);
	}
	
	/**
     * 修改供应链
     * 
     * @param supplyChainCust 供应链信息
     * @return 结果
     */
	@Override
	public int updateSupplyChainCust(SupplyChainCust supplyChainCust)
	{
	    return supplyChainCustMapper.updateSupplyChainCust(supplyChainCust);
	}

	/**
     * 删除供应链对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyChainCustByIds(String ids)
	{
		return supplyChainCustMapper.deleteSupplyChainCustByIds(Convert.toStrArray(ids));
	}
	
}
