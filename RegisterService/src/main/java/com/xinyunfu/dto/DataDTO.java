package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/10
 * @Description :
 */
@Data
public class DataDTO implements Serializable {

    /**
     * 公钥加密后的密文
     */
    private String data;
}
