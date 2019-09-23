package com.xinyunfu.dto.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 订单主表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderMaster extends Base{

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单编号 唯一
     */
    private String orderNo;

    /**
     * 用户的id
     */
    private String userId;


    /**
     * 订单类型（购买商品=00，购买套餐=01）
     */
    private String orderType;

    /**
     * 地址ID
     */
    private Long addressId;

    /**
     * 子订单
     */
    @TableField(exist = false)
    private List<OrderItem> list;


}
