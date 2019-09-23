package com.xinyunfu.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.dto.pushmessage.PushMessageStartPushDTO;
import com.xinyunfu.entity.InfoCenterMessage;
import com.xinyunfu.service.InfoCenterMessageService;
import com.xinyunfu.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdminEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/8
 * @Description :
 */
@Slf4j
@RestController
@RequestMapping("/InfoCenterMessage")
public class InfoCenterMessageController {

    @Autowired
    private InfoCenterMessageService infoCenterMessageService;


    /**
     * 获取我的消息列表
     * @param currentUserId
     * @param page           当前页
     * @param pageSize       每页显示条数（可不传，默认为10）
     * @return
     */
    @PostMapping("/getMyMessageList")
    public IPage<InfoCenterMessage> getMyMessageList(@RequestHeader("currentUserId") String currentUserId,
                                                     @RequestBody JSONObject object){
        Integer page = object.getInteger("page");
        Integer pageSize = object.getInteger("pageSize");
        log.info("[消息中心]=========>getMyMessageList (入参) 用户编号：{},当前页数：{},每页显示条数：{}",currentUserId,page,pageSize);
        if( page == null || page < 1) page = 1;
        if( pageSize == null || pageSize < 1) pageSize = 10;
        IPage<InfoCenterMessage> iPage = infoCenterMessageService.getMyMessage(currentUserId, page, pageSize);
        iPage.getRecords().forEach( m -> {
            m.setDateTime(TimeUtils.getStr(m.getCreatedDate())); //设置时间
        });
        log.info("[消息中心]=========>getMyMessageList (出参) {}",iPage.getRecords());
        return iPage;
    }
//
//    /**
//     * 根据消息ID获取消息
//     * @param id
//     * @return
//     */
//    @PostMapping("/getMessageById")
//    public InfoCenterMessage getMessageById(@RequestBody JSONObject object){
//        Long id = object.getLong("id");
//        return infoCenterMessageService.getMessageById(id);
//    }

}
