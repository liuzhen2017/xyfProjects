package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 物流公司表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Express extends Base {

    /**
     * 主键id
     */
    @TableId(value = "express_id", type = IdType.AUTO)
    private Integer expressId;

    /**
     * 物流公司名称
     */
    private String expressName;

    /**
     * 物流公司编号
     */
    private String expressCode;


}
