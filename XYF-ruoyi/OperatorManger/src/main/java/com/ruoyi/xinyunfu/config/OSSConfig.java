package com.ruoyi.xinyunfu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auto: liuzhen
 * @Date: 2019/7/28
 * @Version: 1.0
 */
@Configuration("aliOSS")
@Component
public class OSSConfig {
    /**
     * 区域名
     */
    private String endpoint;
    /**
     * key
     */
    private String accessKeyId;
    /**
     * 秘钥
     */
    private String acceaccessKeySecret;
    /**
     * 文件夹名
     */
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAcceaccessKeySecret() {
        return acceaccessKeySecret;
    }

    public void setAcceaccessKeySecret(String acceaccessKeySecret) {
        this.acceaccessKeySecret = acceaccessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
