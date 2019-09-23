package com.xinyunfu.dto.infoCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/16
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 发送的模板编号，模板需要运维人员管理
     */
    private String templateName;

    /**
     * 模板内容
     */
    private List<Object> data;


}
