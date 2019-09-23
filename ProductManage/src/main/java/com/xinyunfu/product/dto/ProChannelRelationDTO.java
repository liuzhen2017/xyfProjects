package com.xinyunfu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author TYM
 * @date 2019/8/11
 * @Description :
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProChannelRelationDTO {

    private String proIds;
    private String proNames;
    private Long channelId;
    private String channelName;


}
