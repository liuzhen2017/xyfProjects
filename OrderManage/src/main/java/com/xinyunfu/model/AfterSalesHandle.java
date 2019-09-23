package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售后处理表
 * </p>
 *
 * @author Xurongze
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AfterSalesHandle extends Base {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 售后订单id
     */
    private String aId;

    /**
     * 处理结果（0=同意）（1=拒绝）
     */
    private Integer result;

    /**
     * 处理说明
     */
    private String instructions;

    /**
     * 处理图片url，多张以分号拼接(;)
     */
    private String imgUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createdDate;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 修改时间
     */
    private LocalDateTime updatedDate;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 是否可用（可用=1，禁用=0）
     */
    private Integer enable;


}
