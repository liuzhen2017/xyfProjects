package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProProperty;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProPropertyMapper extends BaseMapper<ProProperty> {

    @Insert("insert into pro_property (property_name,pro_id) values(#{propertyName},#{proId})")
    @Options(useGeneratedKeys = true, keyProperty = "propertyId")
    int addProperty(ProProperty proProperty);

    @Select("select property_name from pro_property where property_id=#{propertyId}")
    String getProPertyName(@Param("propertyId") long propertyId);

    @Select("select count(0) from pro_property where pro_id = #{proId} and property_name = #{propertyName}")
    int checkPropertyByProIdAndName(@Param("proId") long proId, @Param("propertyName") String propertyName);

    List<ProProperty> selectPropertyList(ProProperty proProperty);

    @Select("select property_id from pro_property where pro_id=#{proId} and status=0")
    List<Long> findPropertyIdsByProId(@Param("proId") Long proId);
}
