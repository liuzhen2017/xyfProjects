package com.xinyunfu.dto;

import com.xinyunfu.dto.volume.CouponList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayTypeCouponDTO implements Serializable {

    /**
     * 支付的方式
     */
    private Set<String> PayType;
}
