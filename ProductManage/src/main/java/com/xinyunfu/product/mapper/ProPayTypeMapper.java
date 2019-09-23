package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProPayType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface ProPayTypeMapper extends BaseMapper<ProPayType> {

    @Select("select id from pro_pay_type where pay_type=#{payType}")
    Long findIdByPayType(String payType);

    @Select("select * from pro_pay_type where pro_id=#{proId} and status=0")
    ProPayType selectByProId(Long proId);
}
