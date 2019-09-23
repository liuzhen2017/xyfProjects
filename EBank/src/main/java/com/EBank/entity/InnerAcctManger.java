package com.EBank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

/**
* @author liuzhen
* innerAcctManger 实体
*/
@Data
@NoArgsConstructor
public class InnerAcctManger{
    @TableId(value="ID", type= IdType.AUTO)
    private long id;
    private String accNo;
    private String idCardNo;
    private String userName;
    private String accName;
    private String bankNo;
    private BigDecimal balance;
    private BigDecimal freezingBalance;
    private BigDecimal canUserBalance;
    private String userId;
    private String busiStatus;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private Integer enable;
    private String accType;
    private String bankName;
    //分行名称
    private String branchName;
    //分行行号
    private String  branchNo;
    //省份
    private String provinceName;
    //城市
    private String cityName;
}