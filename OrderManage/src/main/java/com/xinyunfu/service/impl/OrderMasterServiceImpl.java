package com.xinyunfu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.IStatus;
import com.xinyunfu.constant.SkuType;
import com.xinyunfu.dto.AddOrderDTO;
import com.xinyunfu.dto.OrderStatusDTO;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderMasterMapper;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.model.OrderMaster;
import com.xinyunfu.service.IOrderCommodityService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.IOrderMasterService;
import com.xinyunfu.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 订单主表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements IOrderMasterService {

    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;

    /**
     *  生成主订单
     * @param vo
     * @return
     */
    @Override
    public OrderMaster getOrderMaster(AddOrderDTO vo, String currentUserId) {
        OrderMaster om = new OrderMaster();
        om.setOrderNo(SnowFlake.nextId()+"");
        om.setUserId(currentUserId);
        om.setCreatedBy(currentUserId);
        om.setUpdatedBy(currentUserId);
        om.setAddressId(vo.getAddressId()); //保存收货地址的ID (该地址信息可能有变)
        om.setOrderType(Common.COMMODITY); //默认购买商品
        //获取所有SKUID
        StringBuilder sb = new StringBuilder();
        vo.getGoodsVos().forEach( g -> {
            g.getCom().forEach( c -> {
                sb.append(c.getSkuId()+",");
            });
        });
        // 调用商品服务 通过SKUID 获取  商品类型 从而进行判断
        ResponseInfo<Map<Long, Integer>> res = productManageFeign.findProTypeBySkuId(sb.substring(0,sb.length()-1));
        if(!res.isSuccess()){
            throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
        }
        Integer type = SkuType.GOODS; //SKU类型  默认为普通商品
        Collection<Integer> values = res.getData().values();
        for (Integer value : values) {
            type = value;
        }
        if(type == SkuType.PACKAGE){ //如果SKU的类型为套餐
            om.setOrderType(Common.PACKAGE);
        }
        return om;
    }

    /**
     * 根据主订单号获取订单状态 即支付金额
     * @param orderNo
     * @return
     */
    @Override
    public OrderStatusDTO getStatus(Long orderNo) {
        List<OrderItem> list = iOrderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getMasterNo, orderNo));
        if(CollectionUtils.isEmpty(list))
            throw new CustomException(ExecptionEnum.ERROR_ORDERNO_IS_UNDEFINED);
        BigDecimal money = new BigDecimal(0);
        boolean res = true;
        for (OrderItem item : list) {
            money = money.add(item.getAmount());      //叠加累计金额
            if (item.getStatus() == IStatus.UNPAID) { //如果有一个未支付
                res = false;
            }
        }
        return new OrderStatusDTO(res,money);
    }

    /**
     * 获取所有待结算的订单 信息
     * @return
     */
    @Override
    public List<OrderItem> getOrderInfo() {
        return iOrderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getSettlement,Common.SETTLEMENT_YES));
    }

    /**
     * 结算订单  修改该订单的结算状态为 已结算
     *
     * @param itemNo
     * @return
     */
    @Override
    public boolean settlementOrder(String itemNo) {
        OrderItem item = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, itemNo));
        item.setSettlement(Common.SETTLEMENT_COMPLETED);
        return iOrderItemService.updateById(item);
    }


}
