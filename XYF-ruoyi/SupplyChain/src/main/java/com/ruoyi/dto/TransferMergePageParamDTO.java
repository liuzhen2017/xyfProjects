package com.ruoyi.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author TYM
 * @date 2019/8/21
 * @Description :
 */
@Data
public class TransferMergePageParamDTO {
    /**
     * 开始时间戳，单位秒
     */
    private Long startTime;
    /**
     * 结束时间戳，单位秒
     */
    private Long endTime;
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 合并订单状态
     */
    private String status;

    private Date startDate;

    private Date endDate;

}
