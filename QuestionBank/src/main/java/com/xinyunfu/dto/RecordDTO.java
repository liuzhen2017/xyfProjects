package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/7/30
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO implements Serializable {

    /**
     * 百科常识答题进度（1-200）
     */
    private Integer lifeCount = 0;

    /**
     * 法律知识的答题进度（1-200）
     */
    private Integer lawCount = 0;
}
