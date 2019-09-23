package com.EBank.agent;

import com.EBank.config.PayManger;
import com.EBank.util.RSAUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    PayManger payManger;

    /**
     * post请求
     * @param url 请求路径
     * @param parmat 参数
     * @param isSign 是否要加密,如果需要,从data取数据加密
     * @return
     */
    public String postForObject(String url, JSONObject parmat,boolean isSign){
        log.info("begin request ,url={},parmat ={}",url,parmat);
        if(isSign){
            String data = JSON.toJSONString(parmat.get("data"));
            log.info("begin sign ={}",data);
            try {
//                byte[] bytes = Base64.decodeBase64(data);
                parmat.put("sign", RSAUtil.sign(data,payManger.getPayPriKey(),"utf-8").replaceAll("/r/n",""));
                parmat.put("data", RSAUtil.encryptByPublicKey(data.getBytes("utf-8"),payManger.getPayPullKey()).replaceAll("/r/n",""));
            }catch (Exception e){
                log.error("=======加签异常====message={},e={}==",e.getMessage(),e.getMessage());
            }

            log.info("end sign ={}",parmat.get("sign"));
            parmat.remove("Merchantkey");
        }

        HttpHeaders headers = new HttpHeaders();//header参数
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(parmat,headers); //组装
        log.info("begin request ,url={},parmat ={}",url,parmat);
        String result =restTemplate.postForObject(url,request,String.class);
        log.info("recv request result,result ={}",result);
        return  result;
    }
}
