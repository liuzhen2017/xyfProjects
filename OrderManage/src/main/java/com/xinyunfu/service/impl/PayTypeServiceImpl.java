package com.xinyunfu.service.impl;

import com.xinyunfu.model.PayType;
import com.xinyunfu.mapper.PayTypeMapper;
import com.xinyunfu.service.IPayTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方式表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-22
 */
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayType> implements IPayTypeService {

}
