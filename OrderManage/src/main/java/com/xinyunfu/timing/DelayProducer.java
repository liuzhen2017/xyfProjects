package com.xinyunfu.timing;

import com.xinyunfu.constant.MQ;
import com.xinyunfu.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author XRZ
 * @date 2019/7/13
 * @Description : 延迟消息
 */
@Slf4j
@Component
public class DelayProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送延时消息 取消订单
     * @param currentUserId
     * @param orderNo
     */
    public void update(String currentUserId,String orderNo){
        rabbitTemplate.convertAndSend(MQ.ORDER_EXCHANGE,MQ.KEY_CANCEL,currentUserId+"_"+orderNo, message -> {
            message.getMessageProperties().setDelay(Math.toIntExact(TimeUtils.ORDER_UNPAID));
            return message;
        });
    }

    /**
     * 发送延时消息 删除订单
     * @param currentUserId
     * @param orderNo
     */
    public void delete(String currentUserId,String orderNo){
        rabbitTemplate.convertAndSend(MQ.ORDER_EXCHANGE,MQ.KEY_DELETE,currentUserId+"_"+orderNo, message -> {
            message.getMessageProperties().setDelay(Math.toIntExact(TimeUtils.ORDER_AUTO_DELETE));
            return message;
        });
    }


    /**
     * 发送延时消息 自动确认收货
     * @param currentUserId
     * @param orderNo
     */
    public void confirm(String currentUserId,String orderNo){
        rabbitTemplate.convertAndSend(MQ.ORDER_EXCHANGE,MQ.KEY_AUTO,currentUserId+"_"+orderNo, message -> {
            message.getMessageProperties().setDelay(Math.toIntExact(TimeUtils.ORDER_AUTO_COMFIRM_GOODS));
            return message;
        });
    }

//    /**
//     * 延迟 释放券 （防止用户恶意刷券）
//     * @param currentUserId
//     * @param cIds          券ID，多个以分号拼接
//     */
//    public void release(String currentUserId,String cIds){
//        rabbitTemplate.convertAndSend(MQ.ORDER_EXCHANGE,MQ.KEY_COUPON,currentUserId+"_"+cIds, message -> {
//            message.getMessageProperties().setDelay(Math.toIntExact(TimeUtils.ORDER_RELEASE_COUPON));
//            return message;
//        });
//    }



}

