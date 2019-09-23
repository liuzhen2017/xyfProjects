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
 * 发票信息表
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InvoiceInfo extends Base {

    /**
     * 唯一的id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 发票类型（电子发票=0，纸只发票=1）
     */
    private Integer type;

    /**
     * 发票抬头（个人=0，企业=1）
     */
    private Integer payable;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;


}
