package com.xinyunfu.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProChannelType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品类型分类 数据层
 *
 * @author TYM
 * @date 2019-08-30
 */
public interface ProChannelTypeMapper extends BaseMapper<ProChannelType> {

    @Select("select * from pro_channel_type")
    List<ProChannelType> findAll();
}