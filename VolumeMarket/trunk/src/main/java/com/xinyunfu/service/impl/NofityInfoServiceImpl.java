package com.xinyunfu.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.entity.NotifyInfo;
import com.xinyunfu.mapper.NotiftyInfoMapper;
import com.xinyunfu.service.NoticeInfoService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class NofityInfoServiceImpl extends ServiceImpl<NotiftyInfoMapper, NotifyInfo> implements NoticeInfoService  {

		/**
	     * 保存发送通知
	     *
	     * @param notifyObject 通知对象
	     * @param errMsg       错误消息
	     * @param status       状态
	     * @param notifyParmat 通知参数,按顺序注入方法
	     * @param notifyClass  通知类
	     * @param notifyMethod 通知方法
	     */
		@Override
	    public void saveNotifyInfo(String notifyObject, String errMsg, String status, String notifyClass, String notifyMethod, Object... notifyParmat) {
	        //创建发送记录
	        NotifyInfo notifyInfo = new NotifyInfo();
	        notifyInfo.setErrMsg(errMsg);
	        notifyInfo.setNotifyObject(notifyObject);
	        notifyInfo.setNotifyParmat(JSONObject.toJSONString(notifyParmat));
	        notifyInfo.setNotifyStatus(status);
	        notifyInfo.setNotifyMethod(notifyMethod);
	        notifyInfo.setNotifyClass(notifyClass);
	        this.save(notifyInfo);
	    }

}
