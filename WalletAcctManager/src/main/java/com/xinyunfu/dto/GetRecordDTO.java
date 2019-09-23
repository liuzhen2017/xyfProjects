package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/20
 * @Description :
 */
@Data
public class GetRecordDTO implements Serializable {

    private Integer page = 1;

    private Integer pageSize = 10;

    /**
     * 数据类型 （in/out）
     */
    private String type;

}
