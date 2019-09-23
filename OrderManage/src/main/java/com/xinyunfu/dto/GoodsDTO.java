package com.xinyunfu.dto;

import com.xinyunfu.model.OrderCart;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description :
 */
@Data
public class GoodsDTO implements Serializable {

    /**
     * 商家ID
     */
    private Long storeId;

    /**
     * 备注
     */
    private String remarks = "暂无。";

    /**
     * 商品列表
     */
    private List<Clist> com;

}
