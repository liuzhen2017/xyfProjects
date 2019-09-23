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
 * 订单取消记录表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderQancel extends Base{

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 取消的订单的类型（自动取消=0，我不想买了=1，信息填写错误=2，重新下单=3，其他原因=4）
     */
    private Integer type;

    public OrderQancel(String orderNo, String userId, Integer type) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.type = type;
    }
}
