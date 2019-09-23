package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 错误信息表
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ErrorInfo implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求的方式（GET、POST）
     */
    private String httpMethod;

    /**
     * 请求的类路径
     */
    private String requestClass;

    /**
     * 请求的方法名
     */
    private String requestMethod;

    /**
     * 请求的参数 Map<参数名，参数值>
     */
    private String requsetParam;

    /**
     * 错误的状态码
     */
    private String responseCode;

    /**
     * 错误的消息
     */
    private String responseMessage;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 发生时间
     */
    private Timestamp createdDate;


}
