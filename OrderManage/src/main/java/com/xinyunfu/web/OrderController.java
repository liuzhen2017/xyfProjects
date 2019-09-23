package com.xinyunfu.web;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.IStatus;
import com.xinyunfu.constant.QancelType;
import com.xinyunfu.dto.OrderDetailsDTO;
import com.xinyunfu.dto.customer.PageCountDTO;
import com.xinyunfu.dto.customer.UserPageCountDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.*;
import com.xinyunfu.service.*;
import com.xinyunfu.dto.AddOrderDTO;
import com.xinyunfu.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单 接口
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private IOrderMasterService iOrderMasterService;
    @Autowired
    private IOrderCartService iOrderCartService;
    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;
    @Autowired
    private IOrderQancelService iOrderQancelService;


    /**
     * 提交订单
     * @param currentUserId 当前请求中的ID
     * @param AddOrderVo  下单对象
     * @return 返回 订单号
     */
    @PostMapping("/addOrder")
    public ResponseInfo<Object> addOrder(@RequestHeader(Common.UID) String currentUserId,
                                         @RequestBody AddOrderDTO vo){
        String orderNo = iOrderItemService.addOrder(currentUserId, vo);
        return ResponseInfo.success(new HashMap<String,String>() {{ put("orderNo",orderNo); }});
    }


    /**
     * 根据用户ID 和 订单状态 获取订单列表
     * @param currentUserId 用户ID
     * @param status （ 全部 = -1，待付款 = 0，待发货 = 1，待收货 = 2，待点评 = 4 ）
     * @param current
     * @param pageSize
     * @return
     */
    @PostMapping("/getAllOrderInfoByUserId")
    public ResponseInfo<IPage<OrderItem>> getAllOrderInfoByUserId(@RequestHeader(Common.UID) String currentUserId,
                                                                  @RequestBody OrderDTO vo){
        return ResponseInfo.success(iOrderItemService.getAllOrderInfoByUserId(currentUserId, vo));
    }

    /**
     * 根据子订单编号获取 该订单的所有详情
     * @param orderNo
     * @return
     */
    @PostMapping("/getOrderDetailByItemId")
    public ResponseInfo<OrderDetailsDTO> getOrderDetailByItemId(@RequestHeader(Common.UID) String currentUserId,
                                                                @RequestBody OrderDTO vo){
        if (StringUtils.isNotEmpty(vo.getOrderNo())) {
            return ResponseInfo.success(iOrderItemService.getOrderDetailByItemId(currentUserId, vo));
        }
        throw new CustomException(ExecptionEnum.ERROR_PARAM);
    }

    /**
     * 根据子订单编号或者商品名称 搜索订单
     * @param currentUserId 用户ID
     * @param key 搜索的关键字
     * @return
     */
    @PostMapping("/getOrderInfoByOrderNo")
    public ResponseInfo<PageInfo<OrderItem>> getOrderInfoByOrderNo(@RequestHeader(Common.UID) String currentUserId,
                                                                   @RequestBody OrderDTO vo){
        if (StringUtils.isNotEmpty(vo.getKey())) {
            return ResponseInfo.success(iOrderItemService.getOrderInfoByOrderNo(currentUserId, vo));
        }
        throw new CustomException(ExecptionEnum.ERROR_PARAM);
    }

//    /**
//     * 取消订单
//     * @param currentUserId
//     * @param orderNo
//     * @param type 取消的类型 默认为0 （自动取消）
//     *              （自动取消=0，我不想买了=1，信息填写错误=2，重新下单=3，其他原因=4）
//     * @return
//     */
//    @PostMapping("/cancelOrder")
//    public ResponseInfo<String> cancelOrder(@RequestHeader(Common.UID) String currentUserId,
//                                            @RequestBody OrderDTO vo){
//        if(StringUtils.isNotEmpty(vo.getOrderNo())){
//            iOrderQancelService.cancelOrder(currentUserId, vo.getOrderNo(), vo.getType());
//            return ResponseInfo.success(null);
//        }
//        throw new CustomException(ExecptionEnum.ERROR_PARAM);
//    }

    /**
     * 根据子订单编号 删除订单 （修改订单数据为 禁用）
     * @param orderNo
     * @return
     */
    @PostMapping("/delOrder")
    public ResponseInfo<String> delOrder(@RequestHeader(Common.UID) String currentUserId,
                                         @RequestBody OrderDTO vo){
        if(StringUtils.isNotEmpty(vo.getOrderNo())){
            if(iOrderItemService.delOrder(currentUserId,vo.getOrderNo())){
                return ResponseInfo.success(null);
            }
            throw new CustomException(ExecptionEnum.ERROR_DELETE_ORDER);
        }
        throw new CustomException(ExecptionEnum.ERROR_PARAM);
    }


    /**
     * 确认收货
     * @param currentUserId
     * @param orderNo
     * @return
     */
    @PostMapping("/confirmGoods")
    public ResponseInfo<String> confirmGoods(@RequestHeader(Common.UID) String currentUserId,
                                             @RequestBody OrderDTO vo){
        if(StringUtils.isEmpty(vo.getOrderNo()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        if(!iOrderItemService.confirmGoods(currentUserId,vo.getOrderNo()))
            throw new CustomException(ExecptionEnum.CONFIRM_GOODS_FAIL);
        return ResponseInfo.success(null);
    }


    /**
     *  获取取消订单的类型
     * @return
     */
    @PostMapping("/getQancelType")
    public ResponseInfo<Object> getQancelType(@RequestHeader(Common.UID) String currentUserId){
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < QancelType.VAL.length; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("key",i);
            map.put("value",QancelType.VAL[i]);
            list.add(map);
        }
        return ResponseInfo.success(list);
    }

    /**
     * 获取指定商品可用的优惠券
     * @param currentUserId 用户编号
     * @param object
     * @return
     */
    @PostMapping("/getCoup")
    public ResponseInfo<Object> getCoup(@RequestHeader(Common.UID) String currentUserId,
                                        @RequestBody JSONObject object){
        Long id = object.getLong("id");
        if( null == id )
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return ResponseInfo.success(iOrderCartService.getCoupon(id, currentUserId));
    }



    /**
     * 获取所有待结算 的订单信息
     * @return
     */
    @GetMapping("/getOrderInfo/{a}")
    public ResponseInfo<List<OrderItem>> getOrderInfo(@PathVariable("a") String a){
        return ResponseInfo.success(iOrderMasterService.getOrderInfo());
    }

    /**
     * 结算订单  修改该订单的结算状态为 已结算
     * @return
     */
    @GetMapping("/settlementOrder/{itemNo}")
    public ResponseInfo<Object> settlementOrder(@PathVariable("itemNo") String itemNo){
        if(!iOrderMasterService.settlementOrder(itemNo))
            return ResponseInfo.error(null);
        return ResponseInfo.success(null);
    }


    /**
     * 获取用户购买套餐的数量
     * @param vo
     * @return
     */
    @PostMapping("/getUserPageCount")
    public ResponseInfo<List<PageCountDTO>> getUserPageCount(@RequestBody UserPageCountDTO vo){
        if(CollectionUtils.isEmpty(vo.getUserNos()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return ResponseInfo.success(iOrderItemService.getUserPageCount(vo.getUserNos()));
    }






}
