package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.dto.OrderStatusDTO;
import com.xinyunfu.model.OrderCoupons;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/29
 * @Description :
 */
public interface IOrderCouponsService {


    /**
     * 根据订单ID判断订单状态
     * @param id
     * @return
     */
    OrderStatusDTO getStatus(Long id);

    /**
     * 购买优惠券
     * @param couponId 卷类型ID
     * @param num      购买的数量
     * @return 订单编号
     */
    String buyCoupons(String currentUserId,String couponId,Integer num,Integer payType);


    /**
     * 通过id 获取 券订单信息
     * @param id
     * @return
     */
    OrderCoupons getOrderCoupons(String id);

    /**
     * 通过ID 修改记录的状态 （支付成功后的回调）
     * @param id
     * @return
     */
    boolean UpCoupons(String id);

    /**
     * 获取我的购买记录
     * @param currentUserId
     * @param page
     * @param pageSize
     * @return
     */
    IPage<OrderCoupons> showHistory(String currentUserId,Integer page, Integer pageSize);
}
