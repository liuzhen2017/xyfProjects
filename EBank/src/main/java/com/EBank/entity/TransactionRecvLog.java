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
public class TransactionRecvLog {
    @TableId(value="ID", type= IdType.AUTO)
    private int id;
    private String merchantNo;
    private String subject;
    private String outTradeNo;
    private BigDecimal amount;
    private Date dealtime ;
    private String privateValue;
    private int payType ;
    private int status;
    private BigDecimal settleAmount ;
    private BigDecimal fee;
    private String version;
    private String sysOrderNo;
    private Date createtime;
    private int channelType ;
    private String voucher;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Integer enable;
}
