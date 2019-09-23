package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.entity.InfoCenterPushDeviceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Objects;

@Mapper
public interface InfoCenterPushDeviceMapper extends BaseMapper<InfoCenterPushDeviceDTO> {
    @Select("select * from infocenter_push_device where user_no = #{userNo}")
    InfoCenterPushDeviceDTO findByUserNo(Long userNo);

    default void addOrUpdate(InfoCenterPushDeviceDTO pushDevice){
        if(Objects.isNull(pushDevice.getId()))
            this.insert(pushDevice);
        else
            this.updateById(pushDevice);
    }
}
