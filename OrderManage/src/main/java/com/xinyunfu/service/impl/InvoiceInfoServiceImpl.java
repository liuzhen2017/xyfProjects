package com.xinyunfu.service.impl;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.mapper.InvoiceInfoMapper;
import com.xinyunfu.model.InvoiceInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.model.OrderInvoice;
import com.xinyunfu.service.IInvoiceInfoService;
import com.xinyunfu.service.IOrderInvoiceService;
import com.xinyunfu.utils.RedisCommonUtil;
import com.xinyunfu.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 订单发票表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-01
 */
@Slf4j
@Service
public class InvoiceInfoServiceImpl extends ServiceImpl<InvoiceInfoMapper, InvoiceInfo> implements IInvoiceInfoService {

    @Autowired
    private IOrderInvoiceService iOrderInvoiceService;
    @Autowired
    private RedisCommonUtil redis;

    /**
     * 保存发票信息
     * 先放到redis缓存中（7）天，返回key
     *
     * @param currentUserId
     * @param invoice
     * @return
     */
    @Override
    public String saveInvoice(String currentUserId, InvoiceInfo invoice) {
        if(StringUtils.isEmpty(invoice.getEmail()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM_EMAIL);
        invoice.setId(String.valueOf(SnowFlake.nextId())); //生成唯一ID
        invoice.setCreatedBy(currentUserId);
        invoice.setUpdatedBy(currentUserId);
        //指定前缀 + 发票ID
        String key = Redis.KEY_INVOICE+invoice.getId();
        //放入缓存
        if (!redis.setCache(key,invoice, Redis.EXC_REDIS))
            throw new CustomException(ExecptionEnum.SAVE_INVOICE_INFO_FAIL);
        return invoice.getId();
    }

    /**
     * 持久化发票信息
     * @param itemNos
     * @param id
     */
    @Async
    @Override
    @Transactional
    public void insert(List<String> itemNos, String id,String currentUserId){
        String key = Redis.KEY_INVOICE+id;
        if(!redis.exists(key))
            throw new CustomException(ExecptionEnum.BY_INVOICEID_GET_REDIS_CACHE);
        //通过id获取redis信息
        InvoiceInfo invoice = (InvoiceInfo) redis.getCache(key);
        if(!super.save(invoice))
            throw new CustomException(ExecptionEnum.SAVE_INVOICE_INFO_FAIL);
        //保存发票关联信息
        itemNos.forEach( no -> {
            if(!iOrderInvoiceService.save(new OrderInvoice(no,id,currentUserId,currentUserId,invoice.getType()))){
                throw new CustomException(ExecptionEnum.SAVE_ORDER_INVOICE_INFO_FAIL);
            }
        });
        log.info("[订单服务]=========>保存发票信息成功！发票ID为：{}",id);
    }
}
