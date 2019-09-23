package com.xinyunfu.service;

import com.xinyunfu.model.Time;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 时间限制表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-19
 */
public interface ITimeService extends IService<Time> {

    /**
     * 校验是否在有效时间内
     * @param type （限制下单优惠券=0，限制下单时间=1）
     * @param currentUserId 用户编号
     */
    public void checkTime(Integer type,String currentUserId);


    /**
     * 修改 下单/购买优惠券时间限制
     * @param time
     */
    public void updateTime(Time time);

}
