package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description : 购物车DTO对象
 */
@Data
public class OrderCartDTO implements Serializable {


    private long id;

    private boolean chexk = false;

    private String shopname;

    private List<SonListDTO> sonList;

}
