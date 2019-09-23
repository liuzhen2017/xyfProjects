package com.xingyunfu.ebank.dto.paycenter.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DetailRespDTO {

    private String resCode;
    private String resMsg;
    private List<DetailOrderListDTO> orderList; //交易订单明细列表，仅包含成功的订单
}
