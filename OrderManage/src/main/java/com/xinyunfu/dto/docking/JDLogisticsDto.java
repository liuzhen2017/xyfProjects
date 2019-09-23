package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 *  物流跟踪返回结果
 */
public class JDLogisticsDto {
      private Long jdOrderId;
      private List<JDOrderTrackDto> orderTrack;
}
