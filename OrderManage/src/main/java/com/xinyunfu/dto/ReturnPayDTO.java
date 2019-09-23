package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/4
 * @Description :
 */
@Data
public class ReturnPayDTO implements Serializable {

    /**
     * 打开的方式
     */
    private Integer openType;

    /**
     * 唤起支付的URL
     */
    private String url;

    public ReturnPayDTO(Integer openType, String url) {
        this.openType = openType;
        this.url = url;
    }
}
