package com.xinyunfu.web;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IOrderCouponsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author XRZ
 * @date 2019/7/27
 * @Description :
 */
@RestController
@RequestMapping("/OrderCoupons")
public class OrderCouponsController {

    @Autowired
    private IOrderCouponsService iOrderCouponsService;

    /**
     * 购买优惠券 (立即付款)
     * @param currentUserId
     * @param object
     * @return  订单编号
     */
    @PostMapping("/buyCoupons")
    public ResponseInfo<Object> buyCoupons(@RequestHeader(Common.UID) String currentUserId,
                                           @RequestBody JSONObject object){
        String couponId = object.getString("couponId"); //优惠券的类型
        Integer num = object.getInteger("num");           //购买的数量
        Integer payType = object.getInteger("payType");   //支付类型
        if(StringUtils.isNotEmpty(couponId) && null != num && num > 0 && null != payType && payType > 0){
            String orderNo = iOrderCouponsService.buyCoupons(currentUserId, couponId, num, payType);
            return ResponseInfo.success(new HashMap<String,String>() {{ put("orderNo",orderNo); }});
        }
        throw new CustomException(ExecptionEnum.ERROR_PARAM);
    }

    /**
     * 获取我的购买记录
     * @param currentUserId
     * @param vo
     * @return
     */
    @PostMapping("/showHistory")
    public ResponseInfo<Object> showHistory(@RequestHeader(Common.UID) String currentUserId,
                                            @RequestBody OrderDTO vo){
        return  ResponseInfo.success(iOrderCouponsService.showHistory(currentUserId,vo.getPage(),vo.getPageSize()));
    }
}
