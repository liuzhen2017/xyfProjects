package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProductMapper;
import com.ruoyi.domain.Product;
import com.ruoyi.service.IProductService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProductServiceImpl implements IProductService 
{
	@Autowired
	private ProductMapper productMapper;

	/**
     * 查询商品信息
     * 
     * @param proId 商品ID
     * @return 商品信息
     */
    @Override
	public Product selectProductById(Long proId)
	{
	    return productMapper.selectProductById(proId);
	}
	
	/**
     * 查询商品列表
     * 
     * @param product 商品信息
     * @return 商品集合
     */
	@Override
	public List<Product> selectProductList(Product product)
	{
	    return productMapper.selectProductList(product);
	}
	
    /**
     * 新增商品
     * 
     * @param product 商品信息
     * @return 结果
     */
	@Override
	public int insertProduct(Product product)
	{
	    return productMapper.insertProduct(product);
	}
	
	/**
     * 修改商品
     * 
     * @param product 商品信息
     * @return 结果
     */
	@Override
	public int updateProduct(Product product)
	{
	    return productMapper.updateProduct(product);
	}

	/**
     * 删除商品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductByIds(String ids)
	{
		return productMapper.deleteProductByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<Product> selectInstockList(Integer status) {
		return productMapper.selectInstockList(status);
	}

	@Override
	public List<Product> selectReshelfList(Integer status) {
		return productMapper.selectReshelfList(status);
	}

	@Override
	public List<Product> selectCheckList(Integer status) {
		return productMapper.selectCheckList(status);
	}

}
