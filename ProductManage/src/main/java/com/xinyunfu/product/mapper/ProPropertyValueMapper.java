package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProPropertyValue;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface ProPropertyValueMapper extends BaseMapper<ProPropertyValue>{

    @Insert("insert into pro_property_value (property_id,value_name,remarks) vlaues(#{propertyId},#{valueName},#{remarks})")
    @Options(useGeneratedKeys = true,keyColumn = "valueId")
    int insert(ProPropertyValue propertyValue);

    @Select("select value_text from pro_property_value where value_id=#{valueId}")
    String getValueName(@Param("valueId") long valueId);

    List<ProPropertyValue> selectPropertyValueList(ProPropertyValue proPropertyValue);

    /**
     * 根据属性id和属性值查询属性值id
     * @param propertyId
     * @param value
     * @return
     */
    @Select("select value_id from pro_property_value where property_id=#{propertyId} and value_text=#{value}")
    Long getValueId(@Param("propertyId") Long propertyId, @Param("value") String value);

    /**
     * edit by lhpu
     * =======================================================
     */
    @Select("select count(0) from pro_property_value where property_id=#{propertyId} and value_text=#{valueText}")
    int checkByPropertyIdAndValueText(@Param("propertyId") long propertyId, @Param("valueText") String valueText);

    @Select("select * from pro_property_value where property_id in #{propertyIds}")
    List<ProPropertyValue> selectPropertyValueByPropertyIds(List<Long> propertyIds);
}
