package com.ruoyi.service;

import com.ruoyi.domain.Product;
import java.util.List;

/**
 * 商品 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProductService 
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
     * 删除商品信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductByIds(String ids);

	/**
	 * 查询上架商品列表
	 *
	 * @param product 商品信息
	 * @return 商品集合
	 */
	public List<Product> selectInstockList(Integer status);

	/**
	 * 查询下架商品列表
	 *
	 * @param product 商品信息
	 * @return 商品集合
	 */
	public List<Product> selectReshelfList(Integer status);

	/**
	 * 查询上架商品列表
	 *
	 * @param product 商品信息
	 * @return 商品集合
	 */
	public List<Product> selectCheckList(Integer status);
}
