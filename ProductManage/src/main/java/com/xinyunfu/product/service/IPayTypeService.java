package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.product.model.PayType;

/**
 * @author TYM
 * @date 2019/8/7
 * @Description :
 */
public interface IPayTypeService extends IService<PayType> {
    String findPayTypeByPayTypeId(Long payTypeId);
}
