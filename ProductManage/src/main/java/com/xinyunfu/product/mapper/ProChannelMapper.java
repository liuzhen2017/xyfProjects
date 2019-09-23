package com.xinyunfu.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.dto.HomePageChannelDto;
import com.xinyunfu.product.model.ProChannel;
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
public interface ProChannelMapper extends BaseMapper<ProChannel> {
    @Select("select channel_name,channel_id from pro_channel where father_id=#{channelId} and status=0")
    List<ProChannel> findProChannelNameList(@Param("channelId") Long channelId);

    @Select("select * from pro_channel where channel_id=#{channelId}")
    ProChannel findChannelByChannelId(@Param("channelId") long channelId);

    @Select("select * from pro_channel where channel_id=#{channelId}")
    ProChannel selectByChannelId(@Param("channelId") Long channelId);

    @Select("select max(channel_id) from pro_channel")
    Long findMaxChannelId();

    @Select("select banner_id from pro_channel where channel_id=#{channelId}")
    String findBannerIdsByChannelId(@Param("channelId") Long channelId);

    @Select("select channel_name from pro_channel where channel_id=#{channelId}")
    String getChannelNameByChannelId(@Param("channelId") Long channelId);

    @Select("select count(0) from pro_channel where father_id=#{fatherId} and status!=2")
    Integer selectAmount(@Param("fatherId") Long fatherId);

    @Select("select * from pro_channel where sort_number=(select min(sort_number) from pro_channel where father_id=#{fatherId}) and father_id=#{fatherId}")
    ProChannel selectMinSortNumber(@Param("fatherId") Long fatherId);

    @Select("select * from pro_channel where sort_number=(select max(sort_number) from pro_channel where sort_number<#{sortNumber} and father_id=#{fatherId}) and father_id=#{fatherId}")
    ProChannel selectLast(@Param("fatherId") Long fatherId, @Param("sortNumber") Integer sortNumber);

    @Select("select * from pro_channel where sort_number=(select min(sort_number) from pro_channel where sort_number>#{sortNumber} and father_id=#{fatherId}) and father_id=#{fatherId}")
    ProChannel selectNext(@Param("fatherId") Long fatherId, @Param("sortNumber") Integer sortNumber);

    @Select("select * from pro_channel where sort_number=(select max(sort_number) from pro_channel where father_id=#{fatherId}) and father_id=#{fatherId}")
    ProChannel selectMax(@Param("fatherId") Long fatherId);

    @Select("select max(sort_number) from pro_channel where father_id=#{fatherId}")
    Integer findMaxSortNumber(@Param("fatherId") Long fatherId);

    @Select("select channel_id,channel_name,channel_type_id,father_id,image_url,color,forward_type,link_url,remarks from pro_channel where channel_id=#{channelId} and status=0")
    HomePageChannelDto findHomePageChannelDtoByChannelId(@Param("channelId") Long channelId);

    @Select("select channel_id,channel_name,channel_type_id,father_id,image_url,color,forward_type,link_url,remarks from pro_channel where father_id=#{channelId} and status=0 order by sort_number")
    List<HomePageChannelDto> findHomePageChannelDtoByFatherId(Long channelId);
}
