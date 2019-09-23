package com.ruoyi.service;

import com.ruoyi.domain.WalletAccFlow;
import java.util.List;

/**
 * 每笔转账的转入转出记录 服务层
 * 
 * @author ruoyi
 * @date 2019-08-20
 */
public interface IWalletAccFlowService 
{
	/**
     * 查询每笔转账的转入转出记录信息
     * 
     * @param id 每笔转账的转入转出记录ID
     * @return 每笔转账的转入转出记录信息
     */
	public WalletAccFlow selectWalletAccFlowById(Integer id);
	
	/**
     * 查询每笔转账的转入转出记录列表
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 每笔转账的转入转出记录集合
     */
	public List<WalletAccFlow> selectWalletAccFlowList(WalletAccFlow walletAccFlow);
	
	/**
     * 新增每笔转账的转入转出记录
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 结果
     */
	public int insertWalletAccFlow(WalletAccFlow walletAccFlow);
	
	/**
     * 修改每笔转账的转入转出记录
     * 
     * @param walletAccFlow 每笔转账的转入转出记录信息
     * @return 结果
     */
	public int updateWalletAccFlow(WalletAccFlow walletAccFlow);
		
	/**
     * 删除每笔转账的转入转出记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWalletAccFlowByIds(String ids);
	
}
