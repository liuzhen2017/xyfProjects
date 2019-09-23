package com.xinyunfu.dto.express;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/15
 * @Description : 请求的快递信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpReqDto implements Serializable {

    /**
     * 查询的快递公司的编码， 一律用小写字母
     */
    private String com;

    /**
     * 查询的快递单号， 单号的最大长度是32个字符
     */
    private String num;


    public String getCom() {
        return com.toLowerCase();
    }
}
