package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.EbankFlowMapper;
import com.ruoyi.domain.EbankFlow;
import com.ruoyi.service.IEbankFlowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 每笔转账的转入转出记录，不包括合并转账 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Service
public class EbankFlowServiceImpl implements IEbankFlowService 
{
	@Autowired
	private EbankFlowMapper ebankFlowMapper;

	/**
     * 查询每笔转账的转入转出记录，不包括合并转账信息
     * 
     * @param id 每笔转账的转入转出记录，不包括合并转账ID
     * @return 每笔转账的转入转出记录，不包括合并转账信息
     */
    @Override
	public EbankFlow selectEbankFlowById(Integer id)
	{
	    return ebankFlowMapper.selectEbankFlowById(id);
	}
	
	/**
     * 查询每笔转账的转入转出记录，不包括合并转账列表
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 每笔转账的转入转出记录，不包括合并转账集合
     */
	@Override
	public List<EbankFlow> selectEbankFlowList(EbankFlow ebankFlow)
	{
	    return ebankFlowMapper.selectEbankFlowList(ebankFlow);
	}
	
    /**
     * 新增每笔转账的转入转出记录，不包括合并转账
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 结果
     */
	@Override
	public int insertEbankFlow(EbankFlow ebankFlow)
	{
	    return ebankFlowMapper.insertEbankFlow(ebankFlow);
	}
	
	/**
     * 修改每笔转账的转入转出记录，不包括合并转账
     * 
     * @param ebankFlow 每笔转账的转入转出记录，不包括合并转账信息
     * @return 结果
     */
	@Override
	public int updateEbankFlow(EbankFlow ebankFlow)
	{
	    return ebankFlowMapper.updateEbankFlow(ebankFlow);
	}

	/**
     * 删除每笔转账的转入转出记录，不包括合并转账对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEbankFlowByIds(String ids)
	{
		return ebankFlowMapper.deleteEbankFlowByIds(Convert.toStrArray(ids));
	}
	
}
