package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.product.model.ProPayType;

/**
 * @author TYM
 * @date 2019/8/7
 * @Description :
 */
public interface IProPayTypeService extends IService<ProPayType> {

    Long findIdByPayType(String payType);

    ProPayType findProPayTypeByProId(Long proId);
}
