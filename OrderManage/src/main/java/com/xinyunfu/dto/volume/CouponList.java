package com.xinyunfu.dto.volume;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* couponList 实体
*/
@Data
@NoArgsConstructor
public class CouponList implements Serializable {
    private String id;
    private String couponId;
    private String userNo;
    private String couponStatus;
    private Integer couponType;
    private boolean sonChexk = false;
    private String couponNode;
    private String title;
    private String describes;
    private String picUrl;
    private String rules;
    private BigDecimal valueAmount;
    private Date effectiveTime;
    private Date invalidTime;
    @JsonIgnore
    private Date createdDate;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private Date updatedDate;
    @JsonIgnore
    private String updatedBy;
    private Date ticketDate;
    private Date useDate;
    private String source;
    private String productType;
    private Date dueDate;
}