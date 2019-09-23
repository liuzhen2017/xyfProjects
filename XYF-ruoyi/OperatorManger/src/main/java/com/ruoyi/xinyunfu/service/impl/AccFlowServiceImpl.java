package com.ruoyi.xinyunfu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xinyunfu.mapper.AccFlowMapper;
import com.ruoyi.xinyunfu.domain.AccFlow;
import com.ruoyi.xinyunfu.service.IAccFlowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 账户流水 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Service
public class AccFlowServiceImpl implements IAccFlowService 
{
	@Autowired
	private AccFlowMapper accFlowMapper;

	/**
     * 查询账户流水信息
     * 
     * @param id 账户流水ID
     * @return 账户流水信息
     */
    @Override
	public AccFlow selectAccFlowById(Integer id)
	{
	    return accFlowMapper.selectAccFlowById(id);
	}
	
	/**
     * 查询账户流水列表
     * 
     * @param accFlow 账户流水信息
     * @return 账户流水集合
     */
	@Override
	public List<AccFlow> selectAccFlowList(AccFlow accFlow)
	{
	    return accFlowMapper.selectAccFlowList(accFlow);
	}
	
    /**
     * 新增账户流水
     * 
     * @param accFlow 账户流水信息
     * @return 结果
     */
	@Override
	public int insertAccFlow(AccFlow accFlow)
	{
	    return accFlowMapper.insertAccFlow(accFlow);
	}
	
	/**
     * 修改账户流水
     * 
     * @param accFlow 账户流水信息
     * @return 结果
     */
	@Override
	public int updateAccFlow(AccFlow accFlow)
	{
	    return accFlowMapper.updateAccFlow(accFlow);
	}

	/**
     * 删除账户流水对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAccFlowByIds(String ids)
	{
		return accFlowMapper.deleteAccFlowByIds(Convert.toStrArray(ids));
	}
	
}
