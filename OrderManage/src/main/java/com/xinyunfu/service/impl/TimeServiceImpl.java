package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.model.Time;
import com.xinyunfu.mapper.TimeMapper;
import com.xinyunfu.service.ITimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.utils.RedisCommonUtil;
import com.xinyunfu.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 时间限制表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-19
 */
@Service
public class TimeServiceImpl extends ServiceImpl<TimeMapper, Time> implements ITimeService {

    @Autowired
    private RedisCommonUtil redis;

    /**
     * 校验是否在有效时间内
     *
     * @param type          （限制下单优惠券=0，限制下单时间=1）
     * @param currentUserId
     */
    @Override
    public void checkTime(Integer type, String currentUserId) {
        if(currentUserId.equals("15986784985")) return; // TODO: 2019/8/20 过滤指定手机号码
        String key = type+Redis.KEY_TIME_OUT;
        Time time = null;
        if(redis.exists(key)){
            time = (Time) redis.getCache(key);
        }else{
            time = this.getOne(new LambdaQueryWrapper<Time>().eq(Time::getType,type));
            redis.setCache(key,time);
        }
        String startDate = time.getEffeciveTime().split("_")[0]; //起始时间
        String endDate = time.getEffeciveTime().split("_")[1];   //结束时间
        try {
            if(!TimeUtils.hourMinuteBetween(startDate,endDate)) throw new CustomException(time.getTips());
        } catch (Exception e) {
            throw new CustomException(time.getTips());
        }
    }

    /**
     * 修改 下单/购买优惠券时间限制
     *
     * @param time
     */
    @Override
    @Transactional
    public void updateTime(Time time) {
        if(!this.update(time,new LambdaQueryWrapper<Time>().eq(Time::getType,time.getType())))
            throw new CustomException(ExecptionEnum.CHANGE_TIME_OUT_FAIL);
        redis.clear(Redis.KEY_TIME_OUT);
    }


}
