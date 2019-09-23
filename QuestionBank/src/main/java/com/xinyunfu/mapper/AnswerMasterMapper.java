package com.xinyunfu.mapper;

import com.xinyunfu.model.AnswerMaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 答题主表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface AnswerMasterMapper extends BaseMapper<AnswerMaster> {


    @Update("update answer_master set coupon_count = coupon_count + 1,total_count = total_count + 1 where answer_id = #{masterId}")
    int addCount(@Param("masterId") Long masterId);

}
