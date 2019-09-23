package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.product.mapper.PayTypeMapper;
import com.xinyunfu.product.mapper.ProPayTypeMapper;
import com.xinyunfu.product.model.ProPayType;
import com.xinyunfu.product.service.IProPayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TYM
 * @date 2019/8/7
 * @Description :
 */
@Service
public class ProPayTypeServiceImpl extends ServiceImpl<ProPayTypeMapper, ProPayType> implements IProPayTypeService {

    @Autowired
    private ProPayTypeMapper proPayTypeMapper;
    @Autowired
    private PayTypeMapper payTypeMapper;

    @Override
    public Long findIdByPayType(String payType) {
        return payTypeMapper.findIdByPayType(payType);
    }

    @Override
    public ProPayType findProPayTypeByProId(Long proId) {
        return proPayTypeMapper.selectByProId(proId);
    }
}
