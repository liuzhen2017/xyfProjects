package com.xingyunfu.ebank.domain.channel;

import com.xingyunfu.ebank.constant.ChannelConstants;
import com.xingyunfu.ebank.dto.channel.ChannelUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/***
 * 交易渠道<br/>
 * 说明：ebank支付方式+客户端类型在表中是唯一约束，可以根据这两个值找到支付中心的支付方式和付款方式
 */
@Data
@NoArgsConstructor
public class EbankChannelDTO {
    private Long id;

    private Integer ebankPayType;     //ebank支付方式
    private String clientType;          //客户端类型
    private String ebankPayName;      //ebank支付方式名称

    private Integer payType;            //支付方式 -- 支付中心
    private Integer tradeType;          //付款方式 -- 支付中心
    private String payName;             //支付方式名称
    private String tradeName;           //付款方式名称

    private Boolean enable;             //是否有效

    public EbankChannelDTO(ChannelUpdateDTO var){
        this.id = var.getId();

        this.payType = var.getPayType();
        this.tradeType = var.getTradeType();
        this.payName = ChannelConstants.payTypeMap.get(payType);
        this.tradeName = ChannelConstants.tradeTypeMap.get(tradeType);

        this.enable = var.getEnable();
    }
}

