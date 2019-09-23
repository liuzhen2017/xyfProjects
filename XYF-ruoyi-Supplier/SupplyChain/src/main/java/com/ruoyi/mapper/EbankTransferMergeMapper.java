package com.ruoyi.mapper;

import com.ruoyi.domain.EbankTransferMerge;
import java.util.List;	

/**
 * 合并后向用户转账 数据层
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public interface EbankTransferMergeMapper 
{
	/**
     * 查询合并后向用户转账信息
     * 
     * @param id 合并后向用户转账ID
     * @return 合并后向用户转账信息
     */
	public EbankTransferMerge selectEbankTransferMergeById(Integer id);
	
	/**
     * 查询合并后向用户转账列表
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 合并后向用户转账集合
     */
	public List<EbankTransferMerge> selectEbankTransferMergeList(EbankTransferMerge ebankTransferMerge);
	
	/**
     * 新增合并后向用户转账
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 结果
     */
	public int insertEbankTransferMerge(EbankTransferMerge ebankTransferMerge);
	
	/**
     * 修改合并后向用户转账
     * 
     * @param ebankTransferMerge 合并后向用户转账信息
     * @return 结果
     */
	public int updateEbankTransferMerge(EbankTransferMerge ebankTransferMerge);
	
	/**
     * 删除合并后向用户转账
     * 
     * @param id 合并后向用户转账ID
     * @return 结果
     */
	public int deleteEbankTransferMergeById(Integer id);
	
	/**
     * 批量删除合并后向用户转账
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEbankTransferMergeByIds(String[] ids);
	
}