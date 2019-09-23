package com.EBank.mapper;

import com.EBank.entity.AccFlow;
import com.EBank.entity.PayChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuzhen
 * accFlow Mapper
 */
@Mapper
public interface PayChannelMapper extends BaseMapper<PayChannel> {

    @Select("select * from pay_channel where pay_key ='${payType}'and channles ='${tradeType}' and enables =1 order by order_bys desc limit 1 " )
    PayChannel selectByPayTypeOrderBy(@Param("payType")int payType,@Param("tradeType")String tradeType);
}