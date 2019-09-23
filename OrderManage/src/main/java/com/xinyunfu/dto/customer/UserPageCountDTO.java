package com.xinyunfu.dto.customer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/23
 * @Description :
 */
@Data
public class UserPageCountDTO implements Serializable {

    /**
     * 用户编号
     */
    private List<String> userNos;

}
