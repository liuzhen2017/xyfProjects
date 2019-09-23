package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProSkuMapper;
import com.ruoyi.domain.ProSku;
import com.ruoyi.service.IProSkuService;
import com.ruoyi.common.core.text.Convert;

/**
 * sku——商品库存量单位 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProSkuServiceImpl implements IProSkuService 
{
	@Autowired
	private ProSkuMapper proSkuMapper;

	/**
     * 查询sku——商品库存量单位信息
     * 
     * @param skuId sku——商品库存量单位ID
     * @return sku——商品库存量单位信息
     */
    @Override
	public ProSku selectProSkuById(Long skuId)
	{
	    return proSkuMapper.selectProSkuById(skuId);
	}
	
	/**
     * 查询sku——商品库存量单位列表
     * 
     * @param proSku sku——商品库存量单位信息
     * @return sku——商品库存量单位集合
     */
	@Override
	public List<ProSku> selectProSkuList(ProSku proSku)
	{
	    return proSkuMapper.selectProSkuList(proSku);
	}
	
    /**
     * 新增sku——商品库存量单位
     * 
     * @param proSku sku——商品库存量单位信息
     * @return 结果
     */
	@Override
	public int insertProSku(ProSku proSku)
	{
	    return proSkuMapper.insertProSku(proSku);
	}
	
	/**
     * 修改sku——商品库存量单位
     * 
     * @param proSku sku——商品库存量单位信息
     * @return 结果
     */
	@Override
	public int updateProSku(ProSku proSku)
	{
	    return proSkuMapper.updateProSku(proSku);
	}

	/**
     * 删除sku——商品库存量单位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProSkuByIds(String ids)
	{
		return proSkuMapper.deleteProSkuByIds(Convert.toStrArray(ids));
	}
	
}
