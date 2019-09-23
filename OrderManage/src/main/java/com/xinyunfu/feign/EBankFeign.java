package com.xinyunfu.feign;

import com.xinyunfu.dto.ebank.ProductRecordAddDTO;
import com.xinyunfu.dto.ebank.ProductRecordAddRespDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description : 电子银行
 */
@FeignClient(name = "EBank",decode404 = true)
public interface EBankFeign {

    /**
     * 获取唤起的URL
     * @return
     */
    @PostMapping("/ebank/product")
    ResponseInfo<ProductRecordAddRespDTO> accountEntry(@RequestBody ProductRecordAddDTO vo);

    /**
     *  获取订单状态
     * @param orderNo
     * @return
     */
    @GetMapping("/ebank/product")
    ResponseInfo<Boolean> paySuccess(@RequestParam("orderNo") String orderNo);


    /**
     * 快捷支付的短信校验
     * @param orderNo      申请支付的订单号
     * @param verifyCode   验证码
     * @return
     */
    @GetMapping("/ebank/product/fast")
    ResponseInfo<Object> fastPaymentConfirm(@RequestParam("orderNo") String orderNo,
                            @RequestParam("verifyCode") String verifyCode);

}
