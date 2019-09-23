package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/18
 * @Description : 结算提交对象
 */
@Data
public class SubCartDTO implements Serializable {

    /**
     * 购物车ID 集合
     */
    private List<Long> ids;

    /**
     * 省份编号
     */
    private String  provNo = "0";

    /**
     * 市区编号
     */
    private String cityNo = "0";
}
