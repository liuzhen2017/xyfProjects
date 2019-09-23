package com.xinyunfu.service;

import com.xinyunfu.model.AnswerMaster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 答题主表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface IAnswerMasterService extends IService<AnswerMaster> {


    /**
     * 初始化 6张 万能卷
     *      为该用户生成对应的题
     * @param currentUserId
     * @param orderNo
     * @return
     */
    boolean CreateAnswer(String currentUserId,String orderNo);

    /**
     * 获取我点亮的万能卷数
     * @param currentUserId 当前用户的ID
     * @return
     */
    AnswerMaster getMyLightenCoupon(String currentUserId);


    /**
     * 增加 万能卷激活数量和 答题数量
     * @param masterId
     * @param currentUserId
     * @return
     */
    Object addCount(Long masterId,String currentUserId);

}
