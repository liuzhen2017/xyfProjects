package com.xinyunfu.customer.dto.user;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class UserPageQueryDTO {
    @NotNull@Min(0)@Max(20)
    private Integer pageNo;
    @NotNull@Min(3)
    private Integer pageSize;
    private Long startTime = 0L;         //创建用户区间--开始时间
    private Long endTime = Instant.now().getEpochSecond();           //创建用户区间--结束时间

    private Long userNo;            //用户编号
    private String userCode;        //当前用户的推荐码
    private String phone;           //手机号
    private Integer sex;            //性别
    private Integer level;          //用户级别
}
