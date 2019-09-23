package com.xinyunfu.entity;

import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Date;

/**
* @author liuzhen
* 用户钱包 实体
*/
@Data
@NoArgsConstructor
public class UserWallet implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String userNo;

    /**
     *
     */
    private String userName;

    /**
     * s_系统,u_用户,g_供应商
     */
    private String accountNo;

    /**
     * T00积分钱包,T01现金钱包,T02优惠券钱包
     */
    private String accountType;

    /**
     * U00系统用户,U01 APP用户,U02 供应商
     */
    private String userType;

    /**
     * 余额
     */
    private BigDecimal balance = new BigDecimal(0);

    /**
     *冻结余额
     */
    private BigDecimal frozenBalance = new BigDecimal(0);

    /**
     * 可用余额
     */
    private BigDecimal availableBalance = new BigDecimal(0);

    /**
     * 业务状态
     */
    private String businessStatus;

    /**
     * 是否可用（可用=1，禁用=0）
     */
    private Integer enable;

    /**
     *
     */
    private Timestamp createTime;

    /**
     *
     */
    private Timestamp lastModifyTime;
}