package com.xinyunfu.feign;

import com.xinyunfu.dto.order.OrderItem;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/21
 * @Description :
 */
@FeignClient("OrderManage")
public interface OrderManageFeign {

    /**
     * 获取所有待结算的 订单信息
     * @return
     */
    @GetMapping("/order/getOrderInfo/{a}")
    public ResponseInfo<List<OrderItem>> getOrderInfo(@PathVariable("a") String a);



    /**
     * 结算订单  修改该订单的结算状态为 已结算
     * @return
     */
    @GetMapping("/order/settlementOrder/{itemNo}")
    public ResponseInfo<Object> settlementOrder(@PathVariable("itemNo") String itemNo);

}
