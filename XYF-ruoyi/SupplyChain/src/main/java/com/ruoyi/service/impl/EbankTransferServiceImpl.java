package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.EbankTransferMapper;
import com.ruoyi.domain.EbankTransfer;
import com.ruoyi.service.IEbankTransferService;
import com.ruoyi.common.core.text.Convert;

/**
 * 向用户转账 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Service
public class EbankTransferServiceImpl implements IEbankTransferService 
{
	@Autowired
	private EbankTransferMapper ebankTransferMapper;

	/**
     * 查询向用户转账信息
     * 
     * @param id 向用户转账ID
     * @return 向用户转账信息
     */
    @Override
	public EbankTransfer selectEbankTransferById(Integer id)
	{
	    return ebankTransferMapper.selectEbankTransferById(id);
	}
	
	/**
     * 查询向用户转账列表
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 向用户转账集合
     */
	@Override
	public List<EbankTransfer> selectEbankTransferList(EbankTransfer ebankTransfer)
	{
	    return ebankTransferMapper.selectEbankTransferList(ebankTransfer);
	}
	
    /**
     * 新增向用户转账
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 结果
     */
	@Override
	public int insertEbankTransfer(EbankTransfer ebankTransfer)
	{
	    return ebankTransferMapper.insertEbankTransfer(ebankTransfer);
	}
	
	/**
     * 修改向用户转账
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 结果
     */
	@Override
	public int updateEbankTransfer(EbankTransfer ebankTransfer)
	{
	    return ebankTransferMapper.updateEbankTransfer(ebankTransfer);
	}

	/**
     * 删除向用户转账对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEbankTransferByIds(String ids)
	{
		return ebankTransferMapper.deleteEbankTransferByIds(Convert.toStrArray(ids));
	}
	
}
