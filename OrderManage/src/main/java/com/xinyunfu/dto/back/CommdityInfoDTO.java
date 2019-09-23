package com.xinyunfu.dto.back;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderConsignee;
import com.xinyunfu.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/20
 * @Description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommdityInfoDTO implements Serializable {


    /**
     * 商品列表
     */
    private List<OrderCommodity> list;

    /**
     * 收货人信息
     */
    private OrderConsignee consignee;

    /**
     * 子订单信息
     */
    private OrderItem item;


}
