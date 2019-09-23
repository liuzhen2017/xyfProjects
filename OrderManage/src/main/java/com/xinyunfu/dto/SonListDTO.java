package com.xinyunfu.dto;

import com.xinyunfu.dto.volume.CouponList;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description : 购物车商品DTO
 */
@Data
public class SonListDTO implements Serializable{

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 购物车ID
     */
    private long cartId;

    /**
     * 是否选中
     */
    private boolean sonChexk = false;

    /**
     * 商品名字
     */
    private String name;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品SKUID
     */
    private Long skuId;

    /**
     * 商品SKU说明
     */
    private String  strt;

    /**
     * 商品单价
     */
    private BigDecimal money;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 保底金额
     */
    private BigDecimal minAmount;

    /**
     * 商品类型（普通商品=0，秒杀商品=1，套餐商品=2）
     */
    private Integer goodsType;

    /**
     * 使用券的数量
     */
    private Integer couponsNumber;

    /**
     * 商品来源(0=其他,1=京东,2=怡亚通)
     */
    private Integer source;

}
