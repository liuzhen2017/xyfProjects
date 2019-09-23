package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.PayType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayTypeMapper extends BaseMapper<PayType> {

    @Select("select id from pay_type where pay_type=#{payType}")
    Long findIdByPayType(@Param("payType") String payType);

    @Select("select pay_type from pay_type where id=#{payTypeId}")
    String findPayTypeByPayTypeId(@Param("payTypeId") Long payTypeId);
}
