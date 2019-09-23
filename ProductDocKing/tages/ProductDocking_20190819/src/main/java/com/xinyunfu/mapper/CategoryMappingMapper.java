package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.model.CategoryMapping;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *@author lhpu
 *@since  2019/7/19
 */
public interface CategoryMappingMapper extends BaseMapper<CategoryMapping> {

      @Select("select channel_id,channel_name from category_mapping where category_id = #{categoryId} and source = #{source}")
      CategoryMapping getChannelByIdAndSource(@Param("categoryId") int categoryId, @Param("source") int source);

}
