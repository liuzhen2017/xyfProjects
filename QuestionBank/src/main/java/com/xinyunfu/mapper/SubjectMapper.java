package com.xinyunfu.mapper;

import com.xinyunfu.model.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 题库表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    @Select("select count(subject_id) from `subject`")
    int count();

}
