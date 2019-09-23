package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.mapper.OrderPricesMapper;
import com.xinyunfu.model.OrderCart;
import com.xinyunfu.model.OrderPrices;
import com.xinyunfu.service.IOrderPricesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单价格清单表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@Service
public class OrderPricesServiceImpl extends ServiceImpl<OrderPricesMapper, OrderPrices> implements IOrderPricesService {

    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private OrderPricesMapper orderPricesMapper;

    /**
     * 获取我的 价格清单
     *
     * @param currentUserId
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<OrderPrices> getMyOrderPrices(String currentUserId, Integer current, Integer size) {
        String key = Redis.KEY_ORDERPRICES+currentUserId;
        String item = current+"_"+size;
        if(redis.hexists(key,item)){
            return (IPage<OrderPrices>) redis.getHashCache(key,item);
        }
        IPage<OrderPrices> page = super.page(new Page<>(current, size),
                 new LambdaQueryWrapper<OrderPrices>()
                         .eq(OrderPrices::getUserId, currentUserId)
                         .eq(OrderPrices::getEnable,Common.ENABLE)
                         .orderByDesc(OrderPrices::getCreatedDate));
        if(!redis.setHashCache(key,item,page,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>价格清单放入缓存失败！");
        }
        return page;
    }

    /**
     * 根据主订单编号获取 价格清单
     *
     * @param orderNo
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<OrderPrices> getOrderPricesByMasterNo(String orderNo, Integer current, Integer size) {
        String key = Redis.KEY_ORDERPRICES+orderNo;
        String item = current+"_"+size;
        if(redis.hexists(key,item)){
            return (IPage<OrderPrices>) redis.getHashCache(key,item);
        }
        IPage<OrderPrices> page = super.page(new Page<>(current, size),
                 new LambdaQueryWrapper<OrderPrices>()
                         .eq(OrderPrices::getMasterNo, orderNo)
                         .eq(OrderPrices::getEnable,Common.ENABLE)
                         .orderByDesc(OrderPrices::getCreatedDate));
        if(!redis.setHashCache(key,item,page,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>价格清单放入缓存失败！");
        }
        return page;
    }

    /**
     * 根据子订单编号获取 价格清单
     *
     * @param orderNo
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<OrderPrices> getOrderPricesByItemNo(String orderNo, Integer current, Integer size) {
        String key = Redis.KEY_ORDERPRICES+orderNo;
        String item = current+"_"+size;
        if(redis.hexists(key,item)){
            return (IPage<OrderPrices>) redis.getHashCache(key,item);
        }
        IPage<OrderPrices> page = super.page(new Page<>(current, size),
                new LambdaQueryWrapper<OrderPrices>()
                        .eq(OrderPrices::getItemNo, orderNo)
                        .eq(OrderPrices::getEnable,Common.ENABLE)
                        .orderByDesc(OrderPrices::getCreatedDate));
        if(!redis.setHashCache(key,item,page,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>价格清单放入缓存失败！");
        }
        return page;
    }

}
