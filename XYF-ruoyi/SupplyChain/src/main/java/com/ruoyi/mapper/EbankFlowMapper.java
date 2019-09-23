package com.ruoyi.mapper;

import com.ruoyi.domain.EbankFlow;
import java.util.List;	

/**
 * 每笔转账的转入转出记录，不包括合并转账 数据层
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public interface EbankFlowMapper 
{
	/**
     * 查询每笔转账的转入转出记录，不包括合并转账信息
     * 
     * @param id 每笔转账的转入转出记录，不包括合并转账ID
     * @return 每笔转账的转入转出记录，不包括合并转账信息
     */
	public EbankFlow selectEbankFlowById(Integer id);
	
	/**
     * 查询每笔转账的转入转出记录，不包括合并转账列表
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 每笔转账的转入转出记录，不包括合并转账集合
     */
	public List<EbankFlow> selectEbankFlowList(EbankFlow ebankFlow);
	
	/**
     * 新增每笔转账的转入转出记录，不包括合并转账
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 结果
     */
	public int insertEbankFlow(EbankFlow ebankFlow);
	
	/**
     * 修改每笔转账的转入转出记录，不包括合并转账
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 结果
     */
	public int updateEbankFlow(EbankFlow ebankFlow);
	
	/**
     * 删除每笔转账的转入转出记录，不包括合并转账
     * 
     * @param id 每笔转账的转入转出记录，不包括合并转账ID
     * @return 结果
     */
	public int deleteEbankFlowById(Integer id);
	
	/**
     * 批量删除每笔转账的转入转出记录，不包括合并转账
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEbankFlowByIds(String[] ids);
	
}