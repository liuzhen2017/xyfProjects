package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.model.JdAddr;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface JDAddrMapper extends BaseMapper<JdAddr> {

      @Select("select area_id from jd_addr where area_code = #{code}  and level =  #{level}")
      Integer queryByCode(@Param("code") Long code, @Param("level") Integer level);

      @Select("select area_id from jd_addr where parent_area_id = #{parentAreaId}  and area_name =  #{areaName}")
      Integer queryByParmat(@Param("parentAreaId") Integer parentAreaId, @Param("areaName") String areaName);


      // 根据省名字查询 jd area_id
      @Select("select * from jd_addr where area_name =#{provinceName}")
      JdAddr getProvinceByName(@Param("provinceName")String provinceName);


      //根据当前区域名称和父类id查询
      @Select("select * from jd_addr where area_name like CONCAT('%',#{areaName},'%')   and parent_area_id = #{parentAreaId}")
      JdAddr getAreaByNameAndParentId(@Param("parentAreaId")Long parentAreaId, @Param("areaName")String areaName);

}
