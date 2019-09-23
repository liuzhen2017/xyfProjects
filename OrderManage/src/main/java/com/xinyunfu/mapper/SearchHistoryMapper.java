package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.model.SearchHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 订单搜索记录表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface SearchHistoryMapper extends BaseMapper<SearchHistory> {


    @Select("select `key` from search_history where user_id = #{uid} and enable = 1")
    String[] getMySerchaHistory(@Param("uid") String uid);

    @Update("update from search_history set enable = 0 where user_id = #{uid}")
    int delSearchHistoryByUid(@Param("uid") String uid);

    @Update("update from search_history set enable = 0 where search_id = #{id}")
    int delSearchHistoryById(@Param("id")  Long id);

}
