package com.EBank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author liuzhen
* accFlow 实体
*/
@Data
@NoArgsConstructor
public class AccFlow{
    @TableId(value="ID", type= IdType.AUTO)
    private long id;
    private String orderNumber;
    private String superOrderNumber;
    private String payNumber;
    private String payAccNo;
    private String payAccName;
    private String receAccNo;
    private String receAccName;
    private BigDecimal payAmount;
    private Date tranDate;
    private String tranType;
    private String busiStatus;
    private Integer enable;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private String channel;
    private String subject;
    private String userNo;

}