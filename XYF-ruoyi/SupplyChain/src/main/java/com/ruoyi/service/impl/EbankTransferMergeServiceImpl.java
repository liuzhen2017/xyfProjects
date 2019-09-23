package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.EbankTransferMergeMapper;
import com.ruoyi.domain.EbankTransferMerge;
import com.ruoyi.service.IEbankTransferMergeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 合并后向用户转账 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Service
public class EbankTransferMergeServiceImpl implements IEbankTransferMergeService 
{
	@Autowired
	private EbankTransferMergeMapper ebankTransferMergeMapper;

	/**
     * 查询合并后向用户转账信息
     * 
     * @param id 合并后向用户转账ID
     * @return 合并后向用户转账信息
     */
    @Override
	public EbankTransferMerge selectEbankTransferMergeById(Integer id)
	{
	    return ebankTransferMergeMapper.selectEbankTransferMergeById(id);
	}
	
	/**
     * 查询合并后向用户转账列表
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 合并后向用户转账集合
     */
	@Override
	public List<EbankTransferMerge> selectEbankTransferMergeList(EbankTransferMerge ebankTransferMerge)
	{
	    return ebankTransferMergeMapper.selectEbankTransferMergeList(ebankTransferMerge);
	}
	
    /**
     * 新增合并后向用户转账
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 结果
     */
	@Override
	public int insertEbankTransferMerge(EbankTransferMerge ebankTransferMerge)
	{
	    return ebankTransferMergeMapper.insertEbankTransferMerge(ebankTransferMerge);
	}
	
	/**
     * 修改合并后向用户转账
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 结果
     */
	@Override
	public int updateEbankTransferMerge(EbankTransferMerge ebankTransferMerge)
	{
	    return ebankTransferMergeMapper.updateEbankTransferMerge(ebankTransferMerge);
	}

	/**
     * 删除合并后向用户转账对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEbankTransferMergeByIds(String ids)
	{
		return ebankTransferMergeMapper.deleteEbankTransferMergeByIds(Convert.toStrArray(ids));
	}
	
}
