package com.ruoyi.mapper;

import com.ruoyi.domain.EbankTransfer;
import java.util.List;	

/**
 * 向用户转账 数据层
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public interface EbankTransferMapper 
{
	/**
     * 查询向用户转账信息
     * 
     * @param id 向用户转账ID
     * @return 向用户转账信息
     */
	public EbankTransfer selectEbankTransferById(Integer id);
	
	/**
     * 查询向用户转账列表
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 向用户转账集合
     */
	public List<EbankTransfer> selectEbankTransferList(EbankTransfer ebankTransfer);
	
	/**
     * 新增向用户转账
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 结果
     */
	public int insertEbankTransfer(EbankTransfer ebankTransfer);
	
	/**
     * 修改向用户转账
     * 
     * @param ebankTransfer 向用户转账信息
     * @return 结果
     */
	public int updateEbankTransfer(EbankTransfer ebankTransfer);
	
	/**
     * 删除向用户转账
     * 
     * @param id 向用户转账ID
     * @return 结果
     */
	public int deleteEbankTransferById(Integer id);
	
	/**
     * 批量删除向用户转账
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEbankTransferByIds(String[] ids);
	
}