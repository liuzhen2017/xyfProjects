package com.xingyunfu.ebank.feign;

import com.rnmg.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "OrderManage")
@RequestMapping("/OrderPay/")
public interface OrderManageFeign {
    /**
     * 支付的回调
     * @param orderNo 订单编号
     * @param payNumber 支付交易号
     * @param payAmount 支付金额
     * @param status 是否成功
     * @return
     */
    @GetMapping("/payCallback")
    ResponseInfo<Object> payCallback(@RequestParam("orderNo")String orderNo,
                                      @RequestParam("payNumber")String payNumber,
                                      @RequestParam("payAmount")BigDecimal payAmount,
                                      @RequestParam("status")Boolean status,
                                      @RequestParam("orderType")Integer orderType);

}
