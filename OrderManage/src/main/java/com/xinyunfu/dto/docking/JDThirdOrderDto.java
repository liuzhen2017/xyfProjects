package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDThirdOrderDto implements Serializable {
      private long jdOrderId;       //京东订单号
}
