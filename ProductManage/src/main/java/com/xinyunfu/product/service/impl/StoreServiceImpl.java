package com.xinyunfu.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.StoreMapper;
import com.xinyunfu.product.model.Store;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
/**
 * <p>
 * 服务实现类
 * </p>
 * 
 *@author tym
 *@since  2019/7/10
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService{

	@Autowired
	private IProductService productService;
	@Autowired
    private StoreMapper storeMapper;
	/*
	 * 修改
	 */
	@Override
	public ResponseInfo<String> updateStore(@RequestBody Store store) {
		if (updateById(store)) {
			return ResponseInfo.success("修改成功!");
		}else {
			return ResponseInfo.error("修改失败!");
		}
	}
	/*
	 * 修改状态
	 */
	@Override
	public ResponseInfo<String> updateStatus(Long[] ids, Integer status) {
		Store store=new Store();
		store.setStatus(status);
		List<Long> idList=Arrays.asList(ids);
		UpdateWrapper<Store> updateWrapper=new UpdateWrapper<>();
		updateWrapper.in("store_id", idList);
		if (this.update(store, updateWrapper)) {
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
	/*
	 * 分页查询
	 */
	@Override
	public ResponseInfo<IPage<Store>> queryByPage(Store store,Integer page,Integer pageSize) {
		Page<Store> pages = new Page<Store>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
		QueryWrapper<Store> queryWrapper =  new QueryWrapper<>();
		IPage<Store> page2 = this.page(pages, queryWrapper);
		return ResponseInfo.success(page2) ;
	}
	/*
	 * 新增
	 */
	@Override
	public ResponseInfo<String> saveStore(Store store) {
		if (this.save(store)) {
			return ResponseInfo.success("新增成功!");
		}else {
			return ResponseInfo.error("新增失败!");
		}
	}

	@Override
	public Store findByProId(Long proId) {
		Long storeId=productService.getById(proId).getStoreId();
		return this.getById(storeId);
	}

    @Override
    public Store findStoreByProId(Long proId) {
	    QueryWrapper<Store> queryWrapper=new QueryWrapper<>();
	    queryWrapper.eq("pro_id",proId);
        Store store=storeMapper.selectOne(queryWrapper);
        return store;
    }

    @Override
    public Long findStoreIdByOwnerId(String ownerId) {
	    Long storeId=storeMapper.findStoreIdByOwnerId(ownerId);
        return storeId;
    }

    @Override
    public String findStoreNameByOwnerId(String ownerId) {
        return storeMapper.findStoreNameByOwnerId(ownerId);
    }
//	/*
//	 * 删除
//	 */
//	@Override
//	public ResponseInfo<String> deleteStore(Long[] ids, Integer status) {
//		try {
//			this.updateStatus(ids, status);
//			return ResponseInfo.success("删除成功!");
//		} catch (Exception e) {
//			return ResponseInfo.error("删除失败!");
//		}
//	}

}
