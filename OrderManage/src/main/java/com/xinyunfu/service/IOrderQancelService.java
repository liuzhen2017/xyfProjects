package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.OrderQancel;

/**
 * <p>
 * 订单取消记录表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
public interface IOrderQancelService extends IService<OrderQancel> {


    /**
     * 取消订单
     * @param currentUserId
     * @param orderNo           子订单号
     * @param type 取消的类型 默认为0 （自动取消）
     *              （自动取消=0，我不想买了=1，信息填写错误=2，重新下单=3，其他原因=4）
     * @return
     */
    void cancelOrder(String currentUserId,String itemNo,Integer type);

}
