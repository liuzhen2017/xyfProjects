package com.xinyunfu.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.dto.pushmessage.PushMessageStartPushDTO;
import com.xinyunfu.entity.InfoCenterMessage;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import com.xinyunfu.mapper.InfoCenterMessageMapper;
import com.xinyunfu.mapper.InfoCenterPushTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/8
 * @Description : 消息服务
 */
@Service
public class InfoCenterMessageService {

    @Autowired
    private InfoCenterMessageMapper infoCenterMessageMapper;
    @Autowired
    private InfoCenterPushTemplateMapper infoCenterPushTemplateMapper;


    /**
     * 获取我的消息列表
     * @param currentUserId
     * @return
     */
    public IPage<InfoCenterMessage> getMyMessage(String currentUserId,Integer page,Integer pageSize){
        return infoCenterMessageMapper.selectPage(new Page<>(page,pageSize),
                new LambdaQueryWrapper<InfoCenterMessage>()
                        .eq(InfoCenterMessage::getUserNo,currentUserId)
                        .eq(InfoCenterMessage::getEnable,1)
                        .orderByDesc(InfoCenterMessage::getId));
    }


    /**
     * 根据消息ID获取消息
     * @param id
     * @return
     */
    public InfoCenterMessage getMessageById(Long id){
        return infoCenterMessageMapper.selectById(id);
    }

    /**
     * 保存消息
     * @param message json格式的
     */
    public void insert(String message){
        PushMessageStartPushDTO dto = JSONObject.parseObject(message, PushMessageStartPushDTO.class);
        InfoCenterMessage infoCenterMessage = new InfoCenterMessage();
        infoCenterMessage.setCreatedBy(String.valueOf(dto.getUserNo()));
        infoCenterMessage.setUpdatedBy(String.valueOf(dto.getUserNo()));
        infoCenterMessage.setTemplateName(dto.getTemplateName());
        //根据模板类型 获取 模板信息
        InfoCenterPushTemplateDTO template = infoCenterPushTemplateMapper.findByTemplateName(dto.getTemplateName());
        if(null == template)
            throw new RuntimeException("根据模板类型获取模板信息失败！");
        infoCenterMessage.setTemplateType(template.getTemplateType());
        infoCenterMessage.setUserNo(dto.getUserNo());
        infoCenterMessage.setTitle(template.getTitle());
        //套入模板
        String context = String.format(template.getContent(), dto.getData().toArray());
        infoCenterMessage.setContext(context);
        if (infoCenterMessageMapper.insert(infoCenterMessage) != 1)
            throw new RuntimeException("保存消息失败！");
    }

}
