package com.xinyunfu.jace.entites.entite;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* couponList 实体
*/
@Data
@NoArgsConstructor
public class CouponList{
	private Integer id;
    private Integer couponId;
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
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Date ticketDate;
    private Date useDate;
}