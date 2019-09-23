package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.Store;
/**
 * <p>
 * 服务类
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface StoreService extends IService<Store>{

	ResponseInfo<String> updateStore(Store store);

	ResponseInfo<String> updateStatus(Long[] ids, Integer status);

	ResponseInfo<IPage<Store>> queryByPage(Store store,Integer page,Integer pageSize);

	ResponseInfo<String> saveStore(Store store);

	Store findByProId(Long proId);

    Store findStoreByProId(Long proId);

    Long findStoreIdByOwnerId(String ownerId);

    String findStoreNameByOwnerId(String ownerId);
}
