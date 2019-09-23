package com.xinyunfu.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.constant.GoodsSource;
import com.xinyunfu.constant.IStatus;
import com.xinyunfu.dto.docking.JDOrderDto;
import com.xinyunfu.dto.docking.JDThirdOrderDto;
import com.xinyunfu.feign.ProductDocKingFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.service.IOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @author XRZ
 * @date 2019-05-28
 * @Description : 定时任务 定时同步京东订单
 *
 */
@Component
@Slf4j
public class OrderScheduler {

    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private ProductDocKingFeign productDocKingFeign;

    /**
     * 间隔时间 1小时
     */
    static final long TIME = 1000*60*5;


    /**
     * 定时同步京东物流订单
     */
    @Scheduled(cron = "0 0/5 8-23 * * ?")
    public void checkOrder() {
        log.info("[定时任务]============>定时同步京东物流订单开始~！");
        //查询所有京东订单（已付款的）
        List<OrderItem> list = iOrderItemService.list(new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderSource, GoodsSource.JD) //商品来源为京东的
                .eq(OrderItem::getStatus, IStatus.UNSHIPPED)); //订单状态为待发货的
        list.forEach( item -> {
            //获取京东订单号
            ResponseInfo<JDThirdOrderDto> res = productDocKingFeign.getOrderId(String.valueOf(item.getThirdOrder()));
            if(res.isSuccess()){ //根据京东订单号获取订单信息
                ResponseInfo<List<JDOrderDto>> orderInfo = productDocKingFeign.getOrder(res.getData().getJdOrderId());
                if(orderInfo.isSuccess()){
                    JDOrderDto jdOrderDto = orderInfo.getData().get(0);
                    if(jdOrderDto.getState() == 0){ //判断订单物流状态
                        iOrderItemService.confirmDelivery(item.getUserId(),item.getItemNo(),"京东物流",String.valueOf(res.getData().getJdOrderId()));
                        log.info("[定时任务]============>同步京东订单发货信息成功！订单号：{}，京东订单号：{}",item.getItemNo(),res.getData().getJdOrderId());
                    }
                }
            }
        });

    }











}
