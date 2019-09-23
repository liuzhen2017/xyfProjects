package com.xinyunfu.feign;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.dto.volume.CouponManger;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description : 券集市服务
 */
@FeignClient("VolumeMarket")
public interface VolumeMarketFeign {

    /**
     * 获取我的优惠券
     * @param userNo 用户编号
     * @return ResponseInfo<List<CouponList>>
     */
    @GetMapping("/couponList/queryListByOrder")
    ResponseInfo<List<CouponList>> queryListByOrder(@RequestParam("userNo") String userNo);

    /**
     * 购买套餐后调用 券集市 生成18张券
     *
     * @param userNo     用户编号
     * @param couponNode 节点信息 注册regist,推荐recommend
     * @return
     */
    @GetMapping("/couponManger/distriCouonByOrderNo")
    ResponseInfo<String> distriCouonByOrderNo(@RequestParam("orderNo") String orderNo,
                                    @RequestParam("userNo") String userNo,
                                    @RequestParam("nums") String nums);

    /**
     * 修改优惠券状态
     * @param ids 多个id 使用;拼接
     * @param userNo 用户编号
     * @param couponStatus 优惠券状态 未使用=00，已使用=01 锁定中=08
     * @return
     */
    @GetMapping("/couponList/updateCoupon")
    ResponseInfo<String> updateCoupon(@RequestParam("ids") String ids,
                                    @RequestParam("userNo") String userNo,
                                    @RequestParam("couponStatus") String couponStatus);

    /**
     * 根据优惠券类型ID获取优惠券详细信息
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    @GetMapping("/couponManger/queryById")
    ResponseInfo<CouponManger> queryById(@RequestParam("id") Long id);


    /**
     * 购买优惠券
     * @param object
     * @return
     */
    @PostMapping("/couponManger/buyCoupon")
    ResponseInfo<String> byCoupon(@RequestBody JSONObject object);


    /**
     * 查询是否可以购买
     * @param currentUserId
     * @return
     */
    @GetMapping("/couponMarket/queryIsCanBuyByService")
    ResponseInfo<String> queryIsCanBuyByService(@RequestParam("userNo") String userNo);
}
