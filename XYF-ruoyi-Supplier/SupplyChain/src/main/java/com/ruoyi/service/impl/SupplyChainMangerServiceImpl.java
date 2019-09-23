package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.SupplyChainMangerMapper;
import com.ruoyi.domain.SupplyChainManger;
import com.ruoyi.service.ISupplyChainMangerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 供应链 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Service
public class SupplyChainMangerServiceImpl implements ISupplyChainMangerService 
{
	@Autowired
	private SupplyChainMangerMapper supplyChainMangerMapper;

	/**
     * 查询供应链信息
     * 
     * @param id 供应链ID
     * @return 供应链信息
     */
    @Override
	public SupplyChainManger selectSupplyChainMangerById(Integer id)
	{
	    return supplyChainMangerMapper.selectSupplyChainMangerById(id);
	}
	
	/**
     * 查询供应链列表
     * 
     * @param supplyChainManger 供应链信息
     * @return 供应链集合
     */
	@Override
	public List<SupplyChainManger> selectSupplyChainMangerList(SupplyChainManger supplyChainManger)
	{
	    return supplyChainMangerMapper.selectSupplyChainMangerList(supplyChainManger);
	}
	
    /**
     * 新增供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	@Override
	public int insertSupplyChainManger(SupplyChainManger supplyChainManger)
	{
	    return supplyChainMangerMapper.insertSupplyChainManger(supplyChainManger);
	}
	
	/**
     * 修改供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	@Override
	public int updateSupplyChainManger(SupplyChainManger supplyChainManger)
	{
	    return supplyChainMangerMapper.updateSupplyChainManger(supplyChainManger);
	}

	/**
     * 删除供应链对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyChainMangerByIds(String ids)
	{
		return supplyChainMangerMapper.deleteSupplyChainMangerByIds(Convert.toStrArray(ids));
	}
	
}
