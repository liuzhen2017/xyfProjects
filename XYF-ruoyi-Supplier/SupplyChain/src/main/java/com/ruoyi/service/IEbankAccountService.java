package com.ruoyi.service;

import com.ruoyi.domain.EbankAccount;
import java.util.List;

/**
 * 每个转账账户的虚拟账号 服务层
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public interface IEbankAccountService 
{
	/**
     * 查询每个转账账户的虚拟账号信息
     * 
     * @param id 每个转账账户的虚拟账号ID
     * @return 每个转账账户的虚拟账号信息
     */
	public EbankAccount selectEbankAccountById(Integer id);
	
	/**
     * 查询每个转账账户的虚拟账号列表
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 每个转账账户的虚拟账号集合
     */
	public List<EbankAccount> selectEbankAccountList(EbankAccount ebankAccount);
	
	/**
     * 新增每个转账账户的虚拟账号
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 结果
     */
	public int insertEbankAccount(EbankAccount ebankAccount);
	
	/**
     * 修改每个转账账户的虚拟账号
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 结果
     */
	public int updateEbankAccount(EbankAccount ebankAccount);
		
	/**
     * 删除每个转账账户的虚拟账号信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEbankAccountByIds(String ids);
	
}
