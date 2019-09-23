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
 * 支付方式表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PayType extends Base{


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 支付方式的名称
     */
    private String typeKey;

    /**
     * 支付方式对应的值
     */
    private String typeValue;

}
