package com.xinyunfu.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author XRZ
 * @date 2019/7/22
 * @Description :
 */
@Data
public class Base implements Serializable {

    /**
     * 创建时间
     */
    @JsonIgnore
    protected Timestamp createdDate;

    /**
     * 创建人id
     */
    @JsonIgnore
    protected String createdBy;

    /**
     * 最后修改时间
     */
    @JsonIgnore
    protected Timestamp updatedDate;

    /**
     * 最后修改人id
     */
    @JsonIgnore
    protected String updatedBy;

    /**
     * 是否可用（可用=1，禁用=0）
     */
    @JsonIgnore
    protected Integer enable;

}
