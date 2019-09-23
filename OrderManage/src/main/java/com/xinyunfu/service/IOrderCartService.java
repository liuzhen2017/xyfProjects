package com.xinyunfu.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.dto.*;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.model.OrderCart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface IOrderCartService extends IService<OrderCart> {


    /**
     * 加入购物车
     *
     * @param currentUserId 用户ID
     * @return
     */
    void addCart(String currentUserId, JSONObject object);

    /**
     * 获取我的购物车
     *
     * @param currentUserId 用户ID
     * @return
     */
    Object getMyCart(String currentUserId);


    /**
     * 获取确认订单信息
     * @param currentUserId
     * @param object
     * @return
     */
    ConfirmOrderDTO getConfirmOrderInfo(String currentUserId,JSONObject object);


    /**
     * 修改购物车
     * @param currentUserId
     * @param cartDTOS
     * @return
     */
    boolean modifyCart(String currentUserId,CartDTO cartDTOS);


    /**
     * 删除购物车 修改数据状态为禁用
     * @param ids 购物车ID集合
     * @param currentUserId  当前用的ID
     * @return
     */
    void delCommoditys(List<Long> ids,String currentUserId);

    /**
     * 获取指定商品支持的券
     * @param id    商品ID
     * @param currentUserId
     * @return
     */

     List<CouponList> getCoupon(Long id, String currentUserId);

    /**
     * 根据商品ID 数组 获取 支持的支付方式
     * @param ids 商品ID数组
     * @return
     */
    Set<String> getPayType(Long[] ids);

    /**
     * 通过购物车ID 获取商品数组
     * @param ids
     * @return
     */
    Long[] getCids(List<Long> ids);

    /**
     * 调用用商品服务 查询商品信息list
     *
     * @param list
     * @param pNo 省编号
     * @param cNo 市编号
     * @return List<FreightDTO>
     */
    List<FreightDTO> getProductInfo(List<ConfirmOrderInfoDTO> ids,String pNo,String cNo,String addres);

    /**
     *  判断是否为套餐 的SKUID，如果是则 调用订单树验证
     * @param skuMap Map<SKUID,购买的数量>
     * @param money  金额
     * @param currentUserId
     * @return
     */
    boolean checkPackage(Map<Long,Integer> skuMap, BigDecimal money, String currentUserId);


    /**
     * 获取优惠金额
     * @param list
     * @return  Map<SKUID,该款商品的优惠金额>
     */
    Map<Long,BigDecimal> getDiscountAmountList(List<ConfirmOrderInfoDTO> list,String currentUserId);

//    /**
//     * 调用用商品服务 将查询商品信息list
//     * 转为map
//     * @param list
//     * @return
//     */
//    Map<String, FreightDTO> getProductInfo(SubCartDTO sub);
}
