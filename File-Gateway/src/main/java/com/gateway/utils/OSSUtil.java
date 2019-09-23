package com.gateway.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

/**
 * @author XRZ
 * @date 2019-05-10
 * @Description : 阿里云OSS对象存储工具类
 */
public class OSSUtil {

    private static String endpoint = "images.xyf823.com";//"images.xyf823.com";             //EndPoint（地域节点）
    private static String accessKeyId = "LTAIVgPTngg9CemW";                      //阿里云主账号AccessKey
    private static String accessKeySecret = "CLsYpujPEce3sSGWUhidbKeENEFgoW";    //对应的accessKeySecret
    private static String bucketName = "xyf888";   //Bucket 名称
    
    /**
     * 上传文件
     *
     * @param suffix 文件后缀
     * @param file 文件对象
     * @return URL 绝对路径
     */
    public static String uploadFileToOss(OSSClient ossClient,String suffix, byte[] bytes,int hours,int type) throws IOException {
        // 创建OSSClient实例。
        try {
            //获取UUID 用于文件名 以确保文件名的唯一
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //文件名 = UUID + 后缀
            String fileName = uuid + "." +suffix;
            fileName = getImageDir(type).concat(fileName);
            //上传至OSS    PutObjectRequest(Bucket名称,文件名,MultipartFile类型的文件)
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
            System.out.println("fileName =>:"+fileName);
            // 设置URL过期时间为100年，默认这里是int型，转换为long型即可
            Date expiration = new Date(new Date().getTime() + 3600 * 1000 * hours );
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            return url.toString().split("[?]")[0]; //返回文件的绝对路径
        } finally {
            ossClient.shutdown();  //关闭OSSClient。
        }
    }


    /**
     * 	删除文件
     * @param URL 绝对路径
     * @return boolean
     */
    public static boolean deleteImg(String url) {
        //提取文件名
        String[] urls =  url.split("[/]");
        String fileName = urls[urls.length-1];
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try{
            if(fileName.equals("user.jpg")){ //保留默认文件
                return true;
            }
            //判断文件是否存在
            if(ossClient.doesObjectExist(bucketName, fileName)){
                //删除文件
                ossClient.deleteObject(bucketName, fileName);
            }
        }finally {
            ossClient.shutdown(); //关闭OSSClient。
        }
        return true;
    }


    /**
     * 获取文件后缀
     * @param fileupload
     * @return
     */
    public static String getSuffix(MultipartFile fileupload){
        String originalFilename = fileupload.getOriginalFilename(); //获取文件名
        return FilenameUtils.getExtension(originalFilename);//获取文件名后缀
    }


    /**
     * 临时授权的链接，进行操作
     * @param objectName
     * @param objectContextType
     * @param invailHours
     * @return
     */
    public static String getSTSUrl(String objectName,String objectContextType,int invailHours) {
    	
    	OSSClient ossClient  = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.PUT);
    	// 设置URL过期时间为1小时。
    	Date expiration = new Date(new Date().getTime() + 3600 * 1000*invailHours);
    	request.setExpiration(expiration);
    	// 设置ContentType。
    	request.setContentType(objectContextType);
    	// 设置自定义元信息。
    	request.addUserMetadata("author", "aliy");

    	// 生成PUT方式的签名URL。
    	URL signedUrl = ossClient.generatePresignedUrl(request);
    	return signedUrl.toString();
    }   
    
    public String getOssUrl(String imageName,OSSClient oss) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, imageName, HttpMethod.POST);
		// 设置URL过期时间为1小时。
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		request.setExpiration(expiration);
		// 设置ContentType。
		request.setContentType("");
		// 设置自定义元信息。
		request.addUserMetadata("author", "aliy");
		return "";
	}
    
    
    public static String getImageDir(int imageType) {
		switch(imageType) {
		   case 1: 
			  return "product/details/";
		   case 2:
			   return "product/banner/";
		   case 3: 
			   return "product/video/banner/";
		   case 4:
			   return "product/video/details/";
		   case 5:
			   return "home/banner/";
		   case 6:
			   return "home/menu/";
		   case 7:
			   return "category/menu/";
		   case 8:
			   return "category/banner/";
		   case 9:
			   return "user/avatar/";
		   case 10:
			   return "user/idcard/";
		   default:
			return "user/avatar/";
		}
	}
    
    public static void main(String[] args) throws IOException {
    	OSSClient ossClient  = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\timg.jpg"));
		byte[] bytes = new byte[fis.available()];
		fis.read(bytes);
		String imageFile = uploadFileToOss(ossClient, "jpg", bytes, 5, 9);
		System.out.println(imageFile.substring(imageFile.indexOf("/user")));
	}
   
}
