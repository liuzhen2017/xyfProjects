package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.product.mapper.PayTypeMapper;
import com.xinyunfu.product.model.PayType;
import com.xinyunfu.product.service.IPayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TYM
 * @date 2019/8/7
 * @Description :
 */
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayType> implements IPayTypeService {

    @Autowired
    private PayTypeMapper payTypeMapper;


    @Override
    public String findPayTypeByPayTypeId(Long payTypeId) {
        return payTypeMapper.findPayTypeByPayTypeId(payTypeId);
    }
}
