package com.xinyunfu.web;


import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.*;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IOrderCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 购物车表 前端控制器
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@RestController
@RequestMapping("/orderCart")
public class OrderCartController {

    @Autowired
    private IOrderCartService iOrderCartService;


    /**
     * 加入购物车
     *
     * @param
     * @param currentUserId 用户ID
     * @return
     */
    @PostMapping("/addCart")
    public ResponseInfo<String> addCart(@RequestHeader(Common.UID) String currentUserId,
                                        @RequestBody JSONObject object){
        iOrderCartService.addCart(currentUserId, object);
        return ResponseInfo.success(null);

    }

    /**
     * 获取我的购物车
     *
     * @param currentUserId 用户ID
     * @return
     */
    @PostMapping("/getMyCart")
    public ResponseInfo<Object> getMyCart(@RequestHeader(Common.UID) String currentUserId){
        return ResponseInfo.success(iOrderCartService.getMyCart(currentUserId));
    }



    /**
     * 删除购物车中的商品
     * @param cartIds [购物车ID,...]
     * @return 是否成功
     */
    @PostMapping("/delCommodity")
    public ResponseInfo<String> delCommoditys(@RequestHeader(Common.UID) String currentUserId,
                                              @RequestBody CartDTO cartDTO){
        List<Long> cartIds = Arrays.asList(cartDTO.getCartIds());
        if(cartIds.size() < 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        iOrderCartService.delCommoditys(cartIds,currentUserId);
        return ResponseInfo.success(null);
    }

    /**
     * 修改购物车
     * @param currentUserId
     * @param cartDTOS
     * @return
     */
    @PostMapping("/modifyCart")
    public ResponseInfo<String> modifyCart(@RequestHeader(Common.UID) String currentUserId,
                                           @RequestBody CartDTO cartDTOS){
        iOrderCartService.modifyCart(currentUserId, cartDTOS);
        return ResponseInfo.success(null);
    }

    /**
     * 获取确认订单 信息
     * @param currentUserId
     * @return
     */
    @PostMapping("/getConfirmOrderInfo")
    public ResponseInfo<ConfirmOrderDTO> getConfirmOrderInfo(@RequestHeader(Common.UID) String currentUserId,
                                                   @RequestBody JSONObject object){
        return ResponseInfo.success(iOrderCartService.getConfirmOrderInfo(currentUserId,object));
    }









}
