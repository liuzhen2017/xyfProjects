package com.ruoyi.service;

import com.ruoyi.domain.EbankTransferMerge;
import java.util.List;

/**
 * 合并后向用户转账 服务层
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
public interface IEbankTransferMergeService 
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
     * 删除合并后向用户转账信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEbankTransferMergeByIds(String ids);
	
}
