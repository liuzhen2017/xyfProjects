package com.ruoyi.mapper;

import com.ruoyi.domain.ProSku;
import java.util.List;	

/**
 * sku——商品库存量单位 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProSkuMapper 
{
	/**
     * 查询sku——商品库存量单位信息
     * 
     * @param skuId sku——商品库存量单位ID
     * @return sku——商品库存量单位信息
     */
	public ProSku selectProSkuById(Long skuId);
	
	/**
     * 查询sku——商品库存量单位列表
     * 
     * @param proSku sku——商品库存量单位信息
     * @return sku——商品库存量单位集合
     */
	public List<ProSku> selectProSkuList(ProSku proSku);
	
	/**
     * 新增sku——商品库存量单位
     * 
     * @param proSku sku——商品库存量单位信息
     * @return 结果
     */
	public int insertProSku(ProSku proSku);
	
	/**
     * 修改sku——商品库存量单位
     * 
     * @param proSku sku——商品库存量单位信息
     * @return 结果
     */
	public int updateProSku(ProSku proSku);
	
	/**
     * 删除sku——商品库存量单位
     * 
     * @param skuId sku——商品库存量单位ID
     * @return 结果
     */
	public int deleteProSkuById(Long skuId);
	
	/**
     * 批量删除sku——商品库存量单位
     * 
     * @param skuIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProSkuByIds(String[] skuIds);
	
}