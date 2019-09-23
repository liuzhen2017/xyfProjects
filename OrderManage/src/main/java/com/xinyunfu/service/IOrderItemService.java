package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageInfo;
import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.dto.OrderDetailsDTO;
import com.xinyunfu.dto.SubCartDTO;
import com.xinyunfu.dto.back.BackOrderDTO;
import com.xinyunfu.dto.back.SelectOrderDTO;
import com.xinyunfu.dto.customer.PageCountDTO;
import com.xinyunfu.dto.customer.UserPageCountDTO;
import com.xinyunfu.dto.docking.JDOrderDto;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.dto.AddOrderDTO;
import com.xinyunfu.model.OrderMaster;
import com.xinyunfu.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单子表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface IOrderItemService extends IService<OrderItem> {


    /**
     * 提交订单
     * @param currentUserId 当前请求中的ID
     * @param AddOrderVo  下单对象
     * @return 返回订单号
     */
    String addOrder(String currentUserId, AddOrderDTO vo);

    /**
     * 根据用户ID 和 订单状态 获取 子订单列表
     * @param currentUserId 用户ID
     * @param vo
     * @return
     */
    IPage<OrderItem> getAllOrderInfoByUserId(String currentUserId, OrderDTO vo);

    /**
     * 根据子订单编号获取 该订单的所有详情
     * @param orderNo
     * @return
     */
    OrderDetailsDTO getOrderDetailByItemId(String currentUserId, OrderDTO vo);

    /**
     * 根据订单编号删除订单
     * @param currentUserId 用户ID
     * @param orderNo
     * @return
     */
    boolean delOrder(String currentUserId,String orderNo);


    /**
     * 根据子订单编号或者商品名称 搜索订单
     * @param currentUserId 用户ID
     * @param key 搜索的关键字
     * @return
     */
    PageInfo<OrderItem> getOrderInfoByOrderNo(String currentUserId,OrderDTO vo);

    /**
     * 修改订单状态
     * @param currentUserId
     * @param orderNo 子订单编号
     * @param status 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9）默认为0
     * @return
     */
    boolean updateStatus(String currentUserId,String orderNo,Integer status);

    /**
     * 根据订单编号获取订单状态
     * @param orderNo
     * @return
     */
    int getStatus(String orderNo);

    /**
     * 通过SKUID、数量 获取商品信息集合
     * @param goodsVos
     * @return  Map<SKUID,FreightDTO>
     */
    Map<Long, FreightDTO> getList(AddOrderDTO vo);


    /**
     * 获取优惠券信息
     * @param vo
     * @return  Map<优惠券ID, 优惠券对象>
     */
    Map<String, CouponList> getCouponList(String currentUserId);


    /**
     * 确认发货
     * @param currentUserId
     * @param orderNo           订单编号
     * @param shippingCompName  快递公司名称
     * @param shippingSn        快递单号
     * @return
     */
    void confirmDelivery(String currentUserId,String orderNo,String shippingCompName,String shippingSn);


    /**
     * 根据用户编号获取购买套餐的数量
     * @param list 用户编号集合
     * @return
     */
    List<PageCountDTO>  getUserPageCount(List<String> list);


    /**
     * 根据用户编号获取购买套餐的数量
     * @param userNo 用户编号
     * @return
     */
    PageCountDTO getUserPageCount (String userNo);


    /**
     * 后台供应商 订单列表的查询
     * @param vo
     * @return
     */
    Page<BackOrderDTO> findOrderItemByChainId(SelectOrderDTO vo, Integer page, Integer pageSize);


    /**
     * 确认收货
     * @param currentUserId 用户编号
     * @param itemNo 订单号
     * @return
     */
    boolean confirmGoods(String currentUserId,String itemNo);


//    /**
//     *  下单时候 校验金额
//     *
//     * 根据地址，商品信息 ，调用商品服务 获取运费，保底金额，单价
//     * 获取选中券的ID，调用券集市计算券的金额
//     * 判断  后台计算的最低金额 + 优惠券金额 大于或者等于 商品总额 防止优惠券溢出
//     * 判断
//     * @param vo
//     * @return
//     */
//    boolean reconciliation(AddOrderDTO vo);


}
