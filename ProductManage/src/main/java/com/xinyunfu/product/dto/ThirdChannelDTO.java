package com.xinyunfu.product.dto;

import com.xinyunfu.product.vo.PageVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author TYM
 * @date 2019/8/10
 * @Description :
 */
@Data
@AllArgsConstructor
public class ThirdChannelDTO {

    private PageVO<ProDto> page;
    private Map<String,Long> map;





}
