package com.xinyunfu.mapper;
import com.xinyunfu.model.AnswerItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * 答题子表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface AnswerItemMapper extends BaseMapper<AnswerItem> {


    @Select("select answer_id from answer_item where item_id = #{itemId}")
    Long getMasterId(@Param("itemId") Long itemId);

    @Select("select item_status from answer_item where item_id = #{itemId}")
    int getStatus(@Param("itemId") Long itemId);
}
