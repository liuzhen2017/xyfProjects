package com.xinyunfu.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.OrderStatusDTO;
import com.xinyunfu.dto.volume.CouponManger;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderCouponsMapper;
import com.xinyunfu.model.OrderCoupons;
import com.xinyunfu.service.IOrderCouponsService;
import com.xinyunfu.service.ITimeService;
import com.xinyunfu.utils.RedisCommonUtil;
import com.xinyunfu.utils.SnowFlake;
import com.xinyunfu.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/29
 * @Description :
 */
@Slf4j
@Service
public class OrderCouponsServiceImpl implements IOrderCouponsService {

    @Autowired
    private OrderCouponsMapper orderCouponsMapper;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private ITimeService iTimeService;

    /**
     * 根据订单ID判断订单状态
     *
     * @param id
     * @return
     */
    @Override
    public OrderStatusDTO getStatus(Long id) {
        OrderCoupons coupons = orderCouponsMapper.selectById(id);
        if( null == coupons)
            throw new CustomException(ExecptionEnum.ERROR_COUPONS_ORDER_IS_NOT);
        return new OrderStatusDTO(coupons.getEnable() == 1,coupons.getTotalPrice());
    }

    /**
     * 购买优惠券
     *
     * @param couponId 卷类型ID
     * @param num      购买的数量
     * @return 订单编号
     */
    @Override
    public String buyCoupons(String currentUserId,String couponId, Integer num,Integer payType) {
        //校验是否在指定时间内
        iTimeService.checkTime(Common.TIME_COUPON,currentUserId);
        //调用券集市获取 券信息
        ResponseInfo<CouponManger> res = volumeMarketFeign.queryById(Long.valueOf(couponId));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        CouponManger cm = res.getData();
        if(null == cm)
            throw new CustomException(ExecptionEnum.ERROR_REQUSET_VO_FAIL);
        //保存数据，生成订单号
        OrderCoupons oc = new OrderCoupons();
        oc.setId(SnowFlake.nextId());       //生成唯一ID
        oc.setCouponsId(couponId);
        oc.setImg(cm.getPicUrl());
        oc.setTatile(cm.getTitle());        //标题
        oc.setUsingRange(cm.getRules());    //使用范围
        oc.setPrice(cm.getSellPrice());     //单价
        oc.setTotalPrice(cm.getSellPrice().multiply(BigDecimal.valueOf(num))); //总计
        oc.setValueAmount(cm.getValueAmount());     //面值
        oc.setCount(Integer.valueOf(num));
        oc.setValidTime(TimeUtils.getNextTime());  //默认有效期为一年后
        oc.setPayType(payType);
        oc.setCreatedBy(currentUserId);
        oc.setUpdatedBy(currentUserId);
        oc.setEnable(Common.DISABLE);   //默认设置为失效/支付成功后为有效
        if (orderCouponsMapper.insert(oc) != 1)
            throw new CustomException(ExecptionEnum.CREATE_ORDER_COUPONS_FAIL);
        return oc.getId().toString();
    }

    /**
     * 通过id 获取 券订单信息
     *
     * @param id
     * @return
     */
    @Override
    public OrderCoupons getOrderCoupons(String id) {
        return orderCouponsMapper.selectById(id);
    }

    /**
     * 通过ID 修改记录的状态 （支付成功后的回调）
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean UpCoupons(String id) {
        //修改数据状态为 启用
        OrderCoupons orderCoupons = orderCouponsMapper.selectById(id);
        orderCoupons.setEnable(Common.ENABLE);
        if(orderCouponsMapper.updateById(orderCoupons) != 1)
            throw new CustomException(ExecptionEnum.UPDATE_ORDER_COUPONS_FAIL);
        //调用券集市购买优惠券
        Map<String,Object> map = new HashMap<>();
        map.put("userNo",orderCoupons.getCreatedBy());
        map.put("couponid",orderCoupons.getCouponsId());
        map.put("num",orderCoupons.getCount());
        ResponseInfo<String> res = volumeMarketFeign.byCoupon(new JSONObject(map));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
        redis.clear(Redis.KEY_COUPONS+orderCoupons.getCreatedBy());
        return true;
    }

    /**
     * 获取我的购买记录
     * @param currentUserId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public IPage<OrderCoupons> showHistory(String currentUserId,Integer page, Integer pageSize) {
        String key = Redis.KEY_COUPONS+currentUserId;
        String item = page+"_"+pageSize;
        if(redis.hexists(key,item))
            return (IPage<OrderCoupons>) redis.getHashCache(key,item);
        IPage<OrderCoupons> pageList = orderCouponsMapper.selectPage(new Page<>(page, pageSize), new LambdaQueryWrapper<OrderCoupons>()
                .eq(OrderCoupons::getCreatedBy, currentUserId)
                .eq(OrderCoupons::getEnable, Common.ENABLE)
                .orderByDesc(OrderCoupons::getCreatedDate));
        if(null != pageList && !redis.setHashCache(key,item,pageList,Redis.EXC_REDIS)){
            log.error("[订单服务]==========>优惠券购买记录放入缓存失败！");
        }
        return pageList;
    }
}
