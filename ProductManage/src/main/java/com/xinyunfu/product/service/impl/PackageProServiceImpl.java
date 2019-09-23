package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.mapper.ProPackageProMapper;
import com.xinyunfu.product.model.ProPackagePro;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IPackageProService;
import com.xinyunfu.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tym
 * 无效的服务实现类
 */
@Service
public class PackageProServiceImpl extends ServiceImpl<ProPackageProMapper, ProPackagePro> implements IPackageProService{
	
	@Autowired
	private ProPackageProMapper packageProMapper;
	@Autowired
	private IProductService productService;
	/*
	 * 新增套餐商品
	 */
	@Override
	public ResponseInfo<String> saveProPackagePro(ProPackagePro packagePro) {
		if (this.save(packagePro)) {
			return ResponseInfo.success("新增成功!");
		}else {
			return ResponseInfo.error("新增失败!");
		}
	}
//	/*
//	 * 删除套餐商品
//	 */
//	@Override
//	public ResponseInfo<String> deleteProPackagePro(Integer id,Integer status) {
//		if (this.removeById(id)) {
//			return ResponseInfo.success("删除成功!");
//		}else {
//			return ResponseInfo.error("删除失败!");
//		}
//	}
	/*
	 * 根据套餐id查询套餐商品
	 */
	@Override
	public ResponseInfo<List<Product>> findPackageProByPackageId(Integer packageId) {
		QueryWrapper<ProPackagePro> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("package_id", packageId);
		List<ProPackagePro> list=packageProMapper.selectList(queryWrapper);
		List<Product> proList=new ArrayList<>();
		for (ProPackagePro proPackagePro : list) {
			Product product=productService.findProductById(proPackagePro.getProId());
			if (proPackagePro.getStatus()==0) {
				proList.add(product);
			}
		}
		return ResponseInfo.success(proList);
	}
	@Override
	public ResponseInfo<String> updateStatus(Integer[] ids, int status) {
		ProPackagePro proPackagePro=new ProPackagePro();
		proPackagePro.setStatus(status);
		List<Integer> idList=Arrays.asList(ids);
		UpdateWrapper<ProPackagePro> updateWrapper=new UpdateWrapper<>();
		updateWrapper.in("id", idList);
		if (this.update(proPackagePro, updateWrapper)) {
			if (status==0) {
				return ResponseInfo.success("启用成功!");
			}else if (status==1) {
				return ResponseInfo.success("禁用成功!");
			} else{
				return ResponseInfo.success("删除成功!");
			}
		}else {
			if (status==0) {
				return ResponseInfo.success("启用失败!");
			}else if (status==1) {
				return ResponseInfo.success("禁用失败!");
			}else{
				return ResponseInfo.success("删除失败!");
			}
		}
	}

	@Override
	public ProductDTO findPackageProductDTOByProId(ProIdDTO proIdDTO) {

		return null;
	}

}
