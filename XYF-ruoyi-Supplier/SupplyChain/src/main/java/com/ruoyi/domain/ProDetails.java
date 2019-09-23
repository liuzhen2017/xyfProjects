package com.ruoyi.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 商品参数详情表 pro_details
 *
 * @author ruoyi
 * @date 2019-07-24
 */
@Data
@Accessors(chain = true)
public class ProDetails {

    private Long id;
    /**
     * 商品id
     */
    private Long proId;
    /**
     * 商品规格，采用json格式保存
     */
    private String specs;
    /**
     * '商品详情，采用html格式保存
     */
    private String details;
    /**
     * 状态，启用=0,禁用=1,删除=2
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 修改时间
     */
    private Date updatedTime;
    /**
     * 修改人
     */
    private String updatedBy;


}
