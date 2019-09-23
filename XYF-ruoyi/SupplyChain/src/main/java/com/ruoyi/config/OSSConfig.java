package com.ruoyi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auto: liuzhen
 * @Date: 2019/7/28
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "alioss")
@Data
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
    /**
     * 商品路径
     */
    private String productPath;
    /**
     * 商品banner
     */
    private String productBanner;
    /**
     * 商品视频详情
     */
    private String productVoideDetails;
    /**
     * 首页bannner
     */
    private String homeBanner;
    /**
     * 首页菜单
     */
    private String homeNumu;
    /**
     * 商品分类banner图
     */
    private String homeCategory;
    /**
       用户头像：
     */
    private String userAvatar;
    private String baseHost;

    private String productAD;
    private String productSku;

    public String getPathByType(Integer type){
        type = type==null? 1:type;
        switch (type){
            case 1:
                return productPath;
            case 2:
                return productBanner;
            case 4:
                return productVoideDetails;
            case 5:
                return homeBanner;
            case 6:
                return homeNumu;
            case 7:
                return homeCategory;
            case 8:
                return userAvatar;
            case 9:
                return productSku;
            case 10:
                return productAD;
        }
        return null;
    }
}
