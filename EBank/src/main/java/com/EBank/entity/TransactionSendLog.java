package com.EBank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/9
 */
@Data
@NoArgsConstructor
public class TransactionSendLog {
    @TableId(value="ID", type= IdType.AUTO)
    private long id;
    private String subject;
    private String privateValue;
    private String merchantNo;
    private BigDecimal amount;
    private String orderNo;
    private String bankName;
    private String branchName;
    private String branchNo;
    private String cardNo;
    private String channelType;
    private String idCardNo;
    private String userName;
    private String bankCode;
    private String cityName;
    private String provinceName;
    private String tranType;
    private int payType;
    private String returnUrl;
    private String notifyUrl;
    private String deathTime;
    private String subChannel;
    private String tradeType;
    private String authCode;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Integer enable;
}
