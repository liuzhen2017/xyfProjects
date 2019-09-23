package com.ruoyi.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.domain.Store;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.SupplyChainMangerMapper;
import com.ruoyi.domain.SupplyChainManger;
import com.ruoyi.service.ISupplyChainMangerService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.utils.ResponseInfo;

/**
 * 供应链 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Service
public class SupplyChainMangerServiceImpl implements ISupplyChainMangerService 
{
	@Autowired
	private SupplyChainMangerMapper supplyChainMangerMapper;

	@Autowired
	private ProductManageFeign productManageFeign;
	/**
     * 查询供应链信息
     * 
     * @param id 供应链ID
     * @return 供应链信息
     */
    @Override
	public SupplyChainManger selectSupplyChainMangerById(Integer id)
	{
	    return supplyChainMangerMapper.selectSupplyChainMangerById(id);
	}
	
	/**
     * 查询供应链列表
     * 
     * @param supplyChainManger 供应链信息
     * @return 供应链集合
     */
	@Override
	public List<SupplyChainManger> selectSupplyChainMangerList(SupplyChainManger supplyChainManger)
	{
	    return supplyChainMangerMapper.selectSupplyChainMangerList(supplyChainManger);
	}
	
    /**
     * 新增供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	@Override
	public int insertSupplyChainManger(SupplyChainManger supplyChainManger)
	{
		// 商户先入库，入库后提供 id给商品服务
		supplyChainManger.setBusiStatus("00");
		supplyChainManger.setCreateDate(new Date());
		supplyChainManger.setUpdatdDate(new Date());
		int i =supplyChainMangerMapper.insertSupplyChainManger(supplyChainManger);
		if(i>0){
			//写入商品服务
			Store store = new Store();
			store.setStoreName(supplyChainManger.getCustName());
			store.setOwnerId(supplyChainManger.getId());
			store.setTel(supplyChainManger.getContactTel());
			ResponseInfo<String> response = productManageFeign.add(store);
			if(response.isSuccess()){
				return i;
			}
		}
	    return 0;
	}
	
	/**
     * 修改供应链
     * 
     * @param supplyChainManger 供应链信息
     * @return 结果
     */
	@Override
	public int updateSupplyChainManger(SupplyChainManger supplyChainManger)
	{
		supplyChainManger.setUpdatdDate(new Date());
	    return supplyChainMangerMapper.updateSupplyChainManger(supplyChainManger);
	}

	/**
     * 删除供应链对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyChainMangerByIds(String ids)
	{
		return supplyChainMangerMapper.deleteSupplyChainMangerByIds(Convert.toStrArray(ids));
	}
	
}
