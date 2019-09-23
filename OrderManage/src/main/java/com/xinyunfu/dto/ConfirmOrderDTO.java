package com.xinyunfu.dto;

import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.model.PayType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author XRZ
 * @date 2019/7/18
 * @Description : 确认订单数据对象
 */
@Data
public class ConfirmOrderDTO implements Serializable {

    /**
     * 商家集合
     */
    private List<SubStoreDTO> stores;

    /**
     * 总运费
     */
    private BigDecimal totalFreight = new BigDecimal(0);

    /**
     * 商品总金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);

    /**
     * 优惠金额
     */
    private BigDecimal DiscountAmount = new BigDecimal(0);

    /**
     * 应付金额
     */
    private BigDecimal money = new BigDecimal(0);

    /**
     * 总件数
     */
    private Integer totalNumber;

    /**
     * 支持的支付方式
     */
    private List<PayType> list = new ArrayList<>();

    /**
     * 是否为套餐
     */
    private boolean page;

    /**
     * 是否为0元购
     */
    private boolean free;


}
