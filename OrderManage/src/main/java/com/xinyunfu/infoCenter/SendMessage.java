package com.xinyunfu.infoCenter;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.dto.infoCenter.MessageDto;
import com.xinyunfu.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/17
 * @Description : 给消息中心发送消息
 */
@Slf4j
@Component
public class SendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息中心的交换机
     */
    static final String INFO_CENTER_EXCHANGE =  "info_center_exchange";

    /**
     * 消息中心的键
     */
    static final String INFO_CENTER_KEY =  "info_center_key";


    /**
     *  取消订单回退券的消息通知
     * @param currentUserId
     * @param obj
     */
    public void returnCoupons(String currentUserId,Object...obj){
        log.info("[消息通知]=========>取消订单回退券的消息通知已推出！");
        send("008",currentUserId,obj);
    }

    /**
     *  商家确认发货的消息通知
     * @param currentUserId
     * @param obj
     */
    public void merchantDelivery(String currentUserId,Object...obj){
        log.info("[消息通知]=========>商家确认发货的消息通知已推出！");
        send("003",currentUserId,obj);
    }

    /**
     * 支付成功的消息通知
     * @param currentUserId
     * @param obj
     */
    public void successfulPayment(String currentUserId,Object...obj){
        log.info("[消息通知]=========>支付成功的消息通知已推出！");
        send("007",currentUserId,obj);
    }

    /**
     * 发送消息
     * @param type
     * @param currentUserId
     * @param obj
     */
    public void send(String type,String currentUserId,Object...obj){
        rabbitTemplate.convertAndSend(INFO_CENTER_EXCHANGE,INFO_CENTER_KEY,
                JSONObject.toJSONString(new MessageDto(currentUserId,type, obj == null ? new ArrayList<>():Arrays.asList(obj))));
    }


}
