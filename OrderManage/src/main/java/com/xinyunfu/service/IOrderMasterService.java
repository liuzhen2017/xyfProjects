package com.xinyunfu.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.dto.OrderStatusDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.model.OrderMaster;
import com.xinyunfu.dto.AddOrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单主表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface IOrderMasterService extends IService<OrderMaster> {

    /**
     * 生成主订单
     * @param vo
     * @param currentUserId
     * @return
     */
    OrderMaster getOrderMaster(AddOrderDTO vo, String currentUserId);


    /**
     * 根据主订单号获取订单状态 及信息
     * @param orderNo
     * @return
     */
    OrderStatusDTO getStatus(Long orderNo);


    /**
     * 获取所有待结算的订单 信息
     * @return
     */
    List<OrderItem> getOrderInfo();


    /**
     * 结算订单  修改该订单的结算状态为 已结算
     * @param itemNo
     * @return
     */
    boolean settlementOrder(String itemNo);

}
