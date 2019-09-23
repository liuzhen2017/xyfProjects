package com.xinyunfu.jace.entites.entite;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* 优惠券管理 实体
*/
@Data
@NoArgsConstructor
public class CouponManger{
	private Integer id;
    private Integer couponType;
    private String couponNode;
    private String title;
    private String describes;
    private String picUrl;
    private String rules;
    private BigDecimal valueAmount;
    private Date effectiveTime;
    private Date invalidTime;
    private Integer totalNum;
    private Integer canNum;
    private Integer usedNum;
    private String couponStatus;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
}