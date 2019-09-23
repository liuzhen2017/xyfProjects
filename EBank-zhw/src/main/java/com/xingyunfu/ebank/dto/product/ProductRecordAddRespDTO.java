package com.xingyunfu.ebank.dto.product;

import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRecordAddRespDTO {

    private String orderNo;     //订单号
    private String payNo;       //支付中心订单号
    private String payLoad;     //交易数据
    private Integer openType;   //交易类型

    public ProductRecordAddRespDTO(PlaceOrderRespDTO placeOrderResp, EbankChannelDTO channel){
        this.payNo = placeOrderResp.getSysOrderNo();
        this.payLoad = placeOrderResp.getPayload();
        this.openType = channel.getTradeType();
    }
}
