package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.WalletAccFlowMapper;
import com.ruoyi.domain.WalletAccFlow;
import com.ruoyi.service.IWalletAccFlowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 每笔转账的转入转出记录 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-20
 */
@Service
public class WalletAccFlowServiceImpl implements IWalletAccFlowService 
{
	@Autowired
	private WalletAccFlowMapper walletAccFlowMapper;

	/**
     * 查询每笔转账的转入转出记录信息
     * 
     * @param id 每笔转账的转入转出记录ID
     * @return 每笔转账的转入转出记录信息
     */
    @Override
	public WalletAccFlow selectWalletAccFlowById(Integer id)
	{
	    return walletAccFlowMapper.selectWalletAccFlowById(id);
	}
	
	/**
     * 查询每笔转账的转入转出记录列表
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 每笔转账的转入转出记录集合
     */
	@Override
	public List<WalletAccFlow> selectWalletAccFlowList(WalletAccFlow walletAccFlow)
	{
	    return walletAccFlowMapper.selectWalletAccFlowList(walletAccFlow);
	}
	
    /**
     * 新增每笔转账的转入转出记录
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 结果
     */
	@Override
	public int insertWalletAccFlow(WalletAccFlow walletAccFlow)
	{
	    return walletAccFlowMapper.insertWalletAccFlow(walletAccFlow);
	}
	
	/**
     * 修改每笔转账的转入转出记录
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 结果
     */
	@Override
	public int updateWalletAccFlow(WalletAccFlow walletAccFlow)
	{
	    return walletAccFlowMapper.updateWalletAccFlow(walletAccFlow);
	}

	/**
     * 删除每笔转账的转入转出记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteWalletAccFlowByIds(String ids)
	{
		return walletAccFlowMapper.deleteWalletAccFlowByIds(Convert.toStrArray(ids));
	}
	
}
