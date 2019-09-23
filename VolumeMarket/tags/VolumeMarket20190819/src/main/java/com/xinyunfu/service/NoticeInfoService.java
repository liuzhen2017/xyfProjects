package com.xinyunfu.service;

public interface NoticeInfoService {
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
    public void saveNotifyInfo(String notifyObject, String errMsg, String status, String notifyClass, String notifyMethod, Object... notifyParmat);
}
