package com.xinyunfu.jace.entites.entite;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* couponMarket 实体
*/
@Data
@NoArgsConstructor
public class CouponMarket{
	@TableId(value="ID", type= IdType.AUTO)
	private Integer id;
    private Integer couponId;
    private BigDecimal amount;
    private String orderNo;
    private String userNo;
    private String couponStatus;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Date useDate;
    private Date actiDate;
}