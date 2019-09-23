package com.ruoyi.mapper;

import com.ruoyi.domain.Product;
import java.util.List;	

/**
 * 商品 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProductMapper 
{
	/**
     * 查询商品信息
     * 
     * @param proId 商品ID
     * @return 商品信息
     */
	public Product selectProductById(Long proId);
	
	/**
     * 查询商品列表
     * 
     * @param product 商品信息
     * @return 商品集合
     */
	public List<Product> selectProductList(Product product);
	
	/**
     * 新增商品
     * 
     * @param product 商品信息
     * @return 结果
     */
	public int insertProduct(Product product);
	
	/**
     * 修改商品
     * 
     * @param product 商品信息
     * @return 结果
     */
	public int updateProduct(Product product);
	
	/**
     * 删除商品
     * 
     * @param proId 商品ID
     * @return 结果
     */
	public int deleteProductById(Long proId);
	
	/**
     * 批量删除商品
     * 
     * @param proIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductByIds(String[] proIds);

	/**
	 * 查询商品列表
	 *
	 * @param product 商品信息
	 * @return 商品集合
	 */
	public List<Product> selectInstockList(Integer status);

	public List<Product> selectReshelfList(Integer status);

	public List<Product> selectCheckList(Integer status);


}