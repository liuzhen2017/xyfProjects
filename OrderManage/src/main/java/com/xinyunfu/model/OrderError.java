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
 * 异常订单记录表
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderError extends Base{

    /**
     * 唯一的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    public OrderError(String orderNo,String createdBy,String updatedBy) {
        this.orderNo = orderNo;
        super.createdBy = createdBy;
        super.updatedBy = updatedBy;
    }
}
