package com.xinyunfu.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProChannelRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface ProChannelRelationMapper extends BaseMapper<ProChannelRelation> {


    List<ProChannelRelation> selectChannelRelationList(ProChannelRelation proChannelRelation);

    @Select("select count(0) from pro_channel_relation where pro_id=#{proId} and channel_id = #{channelId}")
    int checkByPidAndCid(@Param("proId") long proId, @Param("channelId") Integer channelId);

    @Select("select img_url,color from pro_channel_relation where channel_id=#{channelId} and pro_id=#{proId} and status=0")
    ProChannelRelation getByProIdAndChannelId(@Param("channelId") Long channelId, @Param("proId") Long proId);
}
