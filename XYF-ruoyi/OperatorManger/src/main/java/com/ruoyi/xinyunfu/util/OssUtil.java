package com.ruoyi.xinyunfu.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ruoyi.xinyunfu.config.OSSConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @Auto: liuzhen
 * @Date: 2019/7/28
 * @Version: 1.0
 */
@Component
public class OssUtil {
    private OSSConfig ossConfig;

    public String uploadFile(File file,String suffix) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(),ossConfig.getAccessKeyId(),ossConfig.getAcceaccessKeySecret());

        // 上传文件流。
        try {
            //获取UUID 用于文件名 以确保文件名的唯一
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //文件名 = UUID + 后缀
            String fileName = uuid + "." +suffix;
            InputStream inputStream = new FileInputStream(file);
            ossClient.putObject(ossConfig.getBucketName(), "<yourObjectName>", inputStream);
            URL url =ossClient.generatePresignedUrl(ossConfig.getBucketName(),fileName,new Date());
            // 关闭OSSClient。
            ossClient.shutdown();
            return url.toString().split("[?]")[0];
        }catch (Exception e){
            e.printStackTrace();;
            return null;
        }

    }
}
