package com.ruoyi.service.impl;

import java.util.List;

import com.netflix.discovery.converters.Auto;
import com.ruoyi.domain.AccountAddDTO;
import com.ruoyi.feign.EbankFeign;
import com.ruoyi.feign.WalletAcctManageFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.EbankAccountMapper;
import com.ruoyi.domain.EbankAccount;
import com.ruoyi.service.IEbankAccountService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.utils.ResponseInfo;

/**
 * 每个转账账户的虚拟账号 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class EbankAccountServiceImpl implements IEbankAccountService 
{
	@Autowired
	private EbankAccountMapper ebankAccountMapper;

	@Autowired
	private WalletAcctManageFeign walletAcctManageFeign;

	@Autowired
	private EbankFeign eBankFeign;

	/**
     * 查询每个转账账户的虚拟账号信息
     * 
     * @param id 每个转账账户的虚拟账号ID
     * @return 每个转账账户的虚拟账号信息
     */
    @Override
	public EbankAccount selectEbankAccountById(Integer id)
	{
	    return ebankAccountMapper.selectEbankAccountById(id);
	}
	
	/**
     * 查询每个转账账户的虚拟账号列表
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 每个转账账户的虚拟账号集合
     */
	@Override
	public List<EbankAccount> selectEbankAccountList(EbankAccount ebankAccount)
	{
	    return ebankAccountMapper.selectEbankAccountList(ebankAccount);
	}
	
    /**
     * 新增每个转账账户的虚拟账号
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 结果
     */
	@Override
	public int insertEbankAccount(EbankAccount ebankAccount)
	{
		AccountAddDTO accountAdd = new AccountAddDTO();
		BeanUtils.copyProperties(ebankAccount,accountAdd);
		//入库之后，也要入库到电子钱包
		ResponseInfo<Object> response = walletAcctManageFeign.createAccount(ebankAccount.getUserNo().toString(),ebankAccount.getUserName(),"U02","T01");
		ResponseInfo result = eBankFeign.addAccount(accountAdd);
	    if(response.isSuccess()){
			return ebankAccountMapper.insertEbankAccount(ebankAccount);
		}
		return 0;
	}
	
	/**
     * 修改每个转账账户的虚拟账号
     * 
     * @param ebankAccount 每个转账账户的虚拟账号信息
     * @return 结果
     */
	@Override
	public int updateEbankAccount(EbankAccount ebankAccount)
	{
	    return ebankAccountMapper.updateEbankAccount(ebankAccount);
	}

	/**
     * 删除每个转账账户的虚拟账号对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEbankAccountByIds(String ids)
	{
		return ebankAccountMapper.deleteEbankAccountByIds(Convert.toStrArray(ids));
	}
	
}
