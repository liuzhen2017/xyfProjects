package com.xinyunfu.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* couponList 实体
*/
@Data
@NoArgsConstructor
public class CouponList{
	@TableId(value="ID")
	private String id;
    private String couponId;
    private String userNo;
    private String couponStatus;
    private Integer couponType;
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
    @JsonFormat(pattern="YYYY-MM-dd",timezone="GMT+8")
    private Date ticketDate;
    @JsonFormat(pattern="YYYY-MM-dd",timezone="GMT+8")
    private Date useDate;
    private String source;
    private String productType;
    @JsonFormat(pattern="YYYY-MM-dd",timezone="GMT+8")
    private Date dueDate;
    
}