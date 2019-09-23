package com.xinyunfu.agent;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.config.SendMsgConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/10
 */
@Service
@Slf4j
public class RestTemplateAgent {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SendMsgConfig sendMsgConfig;

    /**
     * post请求
     * @param url 请求路径
     * @param parmat 参数
     * @param isSign 是否要加密,如果需要,从data取数据加密
     * @return
     */
    public JSONObject postForObject(String url, JSONObject parmat,boolean isSign){
        log.info("begin request ,url={},parmat ={}",url,parmat);
        if(isSign){
            String data =JSONObject.toJSONString(parmat.get("data"));
            log.info("begin sign ={}",data);
            parmat.put("sign",  DigestUtils.md5Hex(data+sendMsgConfig.getMerchantNoKey()));
            log.info("end sign ={}",parmat.get("sign"));
           
        }
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("data", JSONObject.toJSONString(parmat.get("data")));
        map.add("MerchantNo", parmat.getString("MerchantNo"));
        map.add("Sign", parmat.getString("sign"));
        
        HttpHeaders headers = new HttpHeaders();//header参数
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,headers); //组装
        ResponseEntity<String> result =restTemplate.postForEntity(url,request,String.class);
        log.info("recv request result,result ={}",result);
        return  JSONObject.parseObject(result.getBody());
    }
}
