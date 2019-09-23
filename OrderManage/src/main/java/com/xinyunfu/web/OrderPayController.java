package com.xinyunfu.web;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.PayCheckDTO;
import com.xinyunfu.dto.PayDTO;
import com.xinyunfu.dto.ReturnPayDTO;
import com.xinyunfu.feign.EBankFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.pay.PayCallbackService;
import com.xinyunfu.service.pay.PayRequstService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/7/24
 * @Description : 订单支付接口
 */
@RestController
@RequestMapping("/OrderPay")
public class OrderPayController {

    @Autowired
    private PayCallbackService payCallbackService;
    @Autowired
    private PayRequstService payRequstService;
    @Autowired
    private EBankFeign eBankFeign;

    /**
     * 获取唤起支付的的URL （普通购物车结算）
     * @param currentUserId 用户ID
     * @param request
     * @return
     */
    @PostMapping("/payment")
    public ResponseInfo<ReturnPayDTO>  payment(@RequestHeader(Common.UID) String currentUserId,
                                               @RequestHeader("X-Real-IP") String ip,
                                               @RequestBody PayDTO vo, HttpServletRequest request){
        vo.setIpAddr(ip);
        return ResponseInfo.success(payRequstService.payment(currentUserId, vo, request));
    }

//    /**
//     * （未付款中调用）去付款
//     * @param currentUserId 用户ID
//     * @param request
//     * @return
//     */
//    @PostMapping("/toPay")
//    public ResponseInfo<ReturnPayDTO> toPay(@RequestHeader(Common.UID) String currentUserId,
//                                            @RequestHeader("X-Real-IP") String ip,
//                                            @RequestBody PayDTO vo, HttpServletRequest request){
//        vo.setIpAddr(ip);
//        return ResponseInfo.success(payRequstService.toPay(currentUserId, vo, request));
//    }

    /**
     * 支付购买优惠券
     * @param currentUserId 用户ID
     * @param request
     * @return
     */
    @PostMapping("/payCoupons")
    public ResponseInfo<ReturnPayDTO> payCoupons(@RequestHeader(Common.UID) String currentUserId,
                                                 @RequestHeader("X-Real-IP")String ip,
                                                 @RequestBody PayDTO vo, HttpServletRequest request){
        vo.setIpAddr(ip);
        return ResponseInfo.success(payRequstService.payCoupons(currentUserId, vo, request));
    }

    /**
     * 电子银行的回调
     * @param orderNo   订单编号
     * @param payNumber 支付交易号
     * @param payAmount 支付金额
     * @param status    是否成功
     * @param orderType 原值返回即可
     * @return
     */
    @GetMapping("/payCallback")
    public ResponseInfo<Boolean> payCallback(@RequestParam("orderNo") String orderNo,
                                             @RequestParam("payNumber") String payNumber,
                                             @RequestParam("payAmount") BigDecimal payAmount,
                                             @RequestParam("status") Boolean status,
                                             @RequestParam("orderType") int orderType){
        return ResponseInfo.success(payCallbackService.paySuccess(orderNo, payNumber, payAmount,status,orderType));
    }

    /**
     * 快捷支付的短信校验
     * @param orderNo      申请支付的订单号
     * @param verifyCode   验证码
     * @return
     */
    @PostMapping("/verifyCode")
    public ResponseInfo<Object> fastPaymentConfirm(@RequestBody PayCheckDTO vo){
        if(StringUtils.isEmpty(vo.getOrderNo()) || StringUtils.isEmpty(vo.getVerifyCode()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return  eBankFeign.fastPaymentConfirm(vo.getOrderNo(), vo.getVerifyCode());
    }




}
