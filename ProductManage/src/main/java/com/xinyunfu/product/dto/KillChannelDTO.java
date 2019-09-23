package com.xinyunfu.product.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KillChannelDTO {
    private Long channelId;
    private String channelName;
    private Integer killStatus;  //-1未开始 0抢购中 1已结束
}
