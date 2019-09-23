package com.xinyunfu.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/23
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCountDTO implements Serializable {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 购买套餐的数量
     */
    private String count;


}
