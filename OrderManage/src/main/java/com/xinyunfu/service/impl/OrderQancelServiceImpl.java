package com.xinyunfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.CouponStatus;
import com.xinyunfu.constant.IStatus;
import com.xinyunfu.dto.infoCenter.MessageDto;
import com.xinyunfu.feign.EBankFeign;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.infoCenter.SendMessage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderQancelMapper;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.model.OrderQancel;
import com.xinyunfu.service.IOrderCommodityService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.IOrderQancelService;
import com.xinyunfu.timing.DelayProducer;
import com.xinyunfu.utils.JsonUtils;
import com.xinyunfu.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 订单取消记录表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Slf4j
@Service
public class OrderQancelServiceImpl extends ServiceImpl<OrderQancelMapper, OrderQancel> implements IOrderQancelService {

    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private DelayProducer delayProducer;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private EBankFeign eBankFeign;
    @Autowired
    private SendMessage sendMessage;

    /**
     * 取消订单
     * @param currentUserId
     * @param orderNo       子订单
     * @param type          取消的类型 默认为0 （自动取消）
     *                      （自动取消=0，我不想买了=1，信息填写错误=2，重新下单=3，其他原因=4）
     * @return
     */
    @Override
    @Transactional
    public void cancelOrder(String currentUserId, String itemNo, Integer type) {
        //校验订单状态
        Integer status = iOrderItemService.getStatus(itemNo);
        if(null == status)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        //取消订单时订单状态必须为待支付！
        if(status != IStatus.UNPAID) return;  //过滤已支付的
        //记录 取消的订单
        if (!super.save(new OrderQancel(itemNo,currentUserId,type))) {
            log.warn("[取消订单]==========>记录取消订单信息失败！");
        }
        //调用电子银行判断订单状态是否为未支付 传递主订单号！ TODO 此处损耗性能！
        OrderItem item = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo,itemNo));
        ResponseInfo<Boolean> res = eBankFeign.paySuccess(item.getMasterNo());
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,res.getCode(),res.getMessage());
        if(res.getData()) return; //过滤已支付的
        //修改订单状态为 已取消
        if(!iOrderItemService.updateStatus(currentUserId,itemNo, IStatus.QANCEL))
            throw new CustomException(ExecptionEnum.CHANGE_ORDER_STATUS_FAIL);
        //回滚订单
        this.backOrder(itemNo,currentUserId,item);
        //加入删除的订单队列
        delayProducer.delete(currentUserId,itemNo);
        log.info("[取消订单]=========>订单号：{}的订单已取消,取消的类型为：{}。已加入删除的订单队列！",itemNo,type);
    }


    /**
     * 回滚订单
     *      归还库存，释放券
     * @param orderNo
     * @param currentUserId
     */
    public void backOrder(String orderNo,String currentUserId,OrderItem orderItem){
        log.info("[取消订单]========>回滚订单开始！");
        List<String> couIds = new ArrayList<>(); //使用的券集合
        Map<Long,Integer> goodsMap = new HashMap<>(); //记录sku和购买的数量
        iOrderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo,orderNo)).forEach(item -> {
            iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>().eq(OrderCommodity::getItemNo,orderNo)).forEach(com -> {
                if(null == com) return;
                goodsMap.put(com.getSkuId(),com.getBuyCount());
                String[] cous = com.getCoupons().split(",");
                for (int i = 0; i < cous.length; i++) {
                    if(StringUtils.isEmpty(cous[i])) break;
                    couIds.add(cous[i]);
                }
            });
        });
        //调用券集市，修改券的状态为 未使用
       if(!CollectionUtils.isEmpty(couIds)){
//           delayProducer.release(currentUserId,StringUtils.join(couIds.toArray(),";"));
//           log.info("[取消订单]=========>调用券集市，释放锁定的券：{}，已入延迟队列处理！",couIds);
           ResponseInfo<String> res = volumeMarketFeign.updateCoupon(StringUtils.join(couIds.toArray(),";"), currentUserId, CouponStatus.EFFECTIVE);
           if(!res.isSuccess())
               throw new CustomException(ExecptionEnum.ERROR_TIMING_RETURN_VOUCHERS);
           log.info("[取消订单]=========>调用券集市释放券成功！券的ID为：{}",couIds);
           //发送消息
           sendMessage.returnCoupons(currentUserId,TimeUtils.getStr2(orderItem.getCreatedDate()));
       }
        // 通知商品服务，归还库存
        if(!productManageFeign.addStock(goodsMap)){
            log.info("[取消订单]=========>调用商品服务 归还库存失败！SKUID和数量为：{}",goodsMap);
            throw new CustomException(ExecptionEnum.REQUSET_GOODS_SERVICE_RETURN_STOCK_FAIL);
        }
        log.info("[取消订单]=========>通知商品服务，归还库存成功！商品SKUID和数量为：{}",goodsMap);
    }
}
