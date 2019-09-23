package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.SearchHistory;

/**
 * <p>
 * 订单搜索记录表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface ISearchHistoryService extends IService<SearchHistory> {

    /**
     * 获取我的搜索历史
     * @param uid
     * @return
     */
    String[] getMySerchaHistory(String uid);

    /**
     * 删除搜索记录
     *
     * @param uid 用户ID
     * @param sid 搜索记录对象ID 0(删除全部)/其他 删除单个
     * @return
     */
    boolean delSearchHistory(String uid,Long sid);



}
