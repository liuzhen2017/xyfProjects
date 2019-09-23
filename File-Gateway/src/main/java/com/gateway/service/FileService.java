package com.gateway.service;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.gateway.constant.MessageContent;
import com.gateway.exception.BusinessException;
import com.gateway.utils.JsonUtils;
import com.gateway.utils.JwtUtils;
import com.gateway.utils.OSSUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {

	
	
	@Value("${xyf.api.version}")
	private String apiVersion;
	
	@Value("${oss.application.endpoint}")
	private String endpoint;
	
	@Value("${oss.application.accessKeyId}")
	private String accessKeyId;
	
	@Value("${oss.application.accessKeySecret}")
	private String accessKeySecret;
	
	@Value("${oss.application.bucketName}")
	private String bucketName;
	
	@Value("${oss.application.vliadHours}")
	private String hours;
	
	@Value("${upload.avatar.count}")
	private String avatarCount;
	
	@Value("${upload.idcard.count}")
	private String idcardCount;
	
	@Value("${oss.application.repalce}")
	private String imagePrefix;
	
	
	@Autowired
	private RedisService redis;
	
	/**
	 * @param json
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public JSONObject uploadBase64(JSONObject json,String token) throws Exception {
		log.info("=======================================upload=========================================");
		JSONObject result = new JSONObject();
        String userNo = JwtUtils.getUserNoByToken(token);
        if(checkToken(token, userNo)) {
        	OSSClient ossClient = initOSSClient();
        	String base64String = json.getString("data");
        	int imageType = json.getInteger("imageType");
            byte[] bytes = Base64.decode(base64String.substring(22)); 
            String suffix = base64String.substring(0, 22);
        	String imageUrl = OSSUtil.uploadFileToOss(ossClient, suffix, bytes, Integer.parseInt(hours),imageType);
        	imageUrl =  imagePrefix.concat(imageUrl.substring(imageUrl.indexOf("/user")));
            log.info("上传图片  ==> ：imageUrl {}",imageUrl);
        	result.put("url", imageUrl);
            return JsonUtils.success(result);        
        }else {
        	result.put("data", "token验证未通过");
			return JsonUtils.error(result);
        }
		
	}

	
	
	/**
	 * token有效性验证
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token,String userNo) throws BusinessException{

        if(StringUtils.isEmpty(token)) {
        	throw new BusinessException("您还没有登录，请登录再操作","00001");
        }
        
        if(!JwtUtils.checkToken(token, apiVersion)) {
        	throw new BusinessException("操作不受信任，请重新登录","00001");
        }
        
        if(!token.equals(redis.get("gateway".concat(userNo)))) {
        	throw new BusinessException("您已经在其他地方登录了，如要操作请重新登录当前设备","00001");
        }
		return true;
	}
	
	
	
	
	
	
	/**
	 * @return
	 */
	public OSSClient initOSSClient() {
		return new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}



	/**
	 * @param file   待上传文件
	 * @param token  token验证
	 * @return
	 * @throws Exception 
	 */
	public JSONObject uploadMultipartFile(MultipartFile file, String token,int imageType) throws Exception {
			JSONObject result = new JSONObject();
			
			String userNo = JwtUtils.getUserNoByToken(token);
			log.info("upload file 用户编号：{}   图片类型： {}",userNo,imageType);
           //上传次数控制
			if(!this.handlerUploadCount(imageType, userNo)) {
                return this.overUploadCount(imageType);
            }		
			
			if(checkToken(token, userNo)) {
			   String imageUrl = OSSUtil.uploadFileToOss(initOSSClient(), OSSUtil.getSuffix(file), file.getBytes(), Integer.parseInt(hours),imageType);
			   imageUrl =  imagePrefix.concat(imageUrl.substring(imageUrl.indexOf("/user")));
	           log.info("上传图片  ==> ：imageUrl {}",imageUrl);
			   result.put("data", imageUrl);
			   return JsonUtils.success(result);
			}else {
				log.info("========================================token验证未通过==================================================");
				result.put("data", "token验证未通过");
				return JsonUtils.error(result);
			}
		
	}
	
	
	
	
	/**
	 * 根据类型判断
	 * @param type
	 * @param userNo
	 * @return
	 */
	public boolean handlerUploadCount(int type,String userNo) {
		int uploadCount = redis.getUploadCount(userNo, type);
	    if(10==type&&Integer.parseInt(idcardCount)>uploadCount) {//上传的是身份证
	      	return true;
	    }
	    if(9==type||Integer.parseInt(avatarCount)>uploadCount) {//上传的是头像
	    	return true;
	    }
		return false;
	}
	
	
	
	public JSONObject overUploadCount(int type) {
		if(9==type) {
			return JsonUtils.create(new JSONObject(), "0010", MessageContent.VARTAR_WARN);
		}else {
			return JsonUtils.create(new JSONObject(), "0010", MessageContent.IDCARD_WARN);
		}
		
	}
	
}
