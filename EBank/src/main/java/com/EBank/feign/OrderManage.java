package com.EBank.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@FeignClient(value = "OrderManage")
@RequestMapping("/OrderPay/")
public interface OrderManage {
    /**
     * 支付的回调
     * @param orderNo 订单编号
     * @param payNumber 支付交易号
     * @param payAmount 支付金额
     * @param status 是否成功
     * @return
     */
    @RequestMapping(value="/payCallback", method= RequestMethod.GET)
    ResponseInfo<Boolean>  payCallback(@RequestParam("orderNo") String orderNo,
                                     @RequestParam("payNumber") String payNumber,
                                     @RequestParam("payAmount") BigDecimal payAmount,
                                     @RequestParam("status") Boolean status, @RequestParam("orderType") int orderType);

}
