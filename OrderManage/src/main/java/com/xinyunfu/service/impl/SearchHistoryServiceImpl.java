package com.xinyunfu.service.impl;

import com.xinyunfu.mapper.SearchHistoryMapper;
import com.xinyunfu.model.SearchHistory;
import com.xinyunfu.service.ISearchHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单搜索记录表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    /**
     * 获取我的搜索历史
     *
     * @param uid
     * @return
     */
    @Override
    public String[] getMySerchaHistory(String uid) {
        return this.searchHistoryMapper.getMySerchaHistory(uid);
    }

    /**
     * 删除搜索记录
     *
     * @param uid 用户ID
     * @param sid  0(删除全部)/其他 删除单个
     * @return
     */
    @Override
    public boolean delSearchHistory(String uid, Long sid) {
        if(sid == 0){
            //清空该用户的搜索订单记录
            return this.searchHistoryMapper.delSearchHistoryByUid(uid) > 0;
        }
        //清除指定的
        return this.searchHistoryMapper.delSearchHistoryById(sid) > 0;

    }
}
