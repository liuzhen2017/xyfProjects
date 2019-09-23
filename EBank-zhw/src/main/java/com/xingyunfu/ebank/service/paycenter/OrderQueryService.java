package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.constant.PayCenterConstant;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterRespBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.orderquery.OrderQueryReqDTO;
import com.xingyunfu.ebank.dto.paycenter.orderquery.OrderQueryRespDTO;
import com.xingyunfu.ebank.util.JsonMapperUtil;
import com.xingyunfu.ebank.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.pay_success_code;

@Slf4j
@Service
public class OrderQueryService {
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private RestTemplate restTemplate;

    private static String charSet = "utf-8";

    public Boolean orderQuery(String orderNo, String sysOrderNo) throws Exception {

        OrderQueryReqDTO queryReq = new OrderQueryReqDTO(payCenterConfig.getMerchantNo(),
                new Date().getTime()/1000, orderNo, sysOrderNo);
        ObjectMapper mapper = JsonMapperUtil.getMapper();
        String reqJson = mapper.writeValueAsString(queryReq);
        log.debug("reqJson: {}", reqJson);

        //请求体
        PayCenterReqBaseDTO payCenterReqBase = new PayCenterReqBaseDTO();
        payCenterReqBase.setMerchantNo(payCenterConfig.getMerchantNo());
        payCenterReqBase.setData(RSAUtil.encryptByPublicKey(reqJson.getBytes(charSet), payCenterConfig.getPayPublicKey()));
        payCenterReqBase.setSign(RSAUtil.sign(reqJson, payCenterConfig.getPayPrivateKey(),charSet));

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = payCenterConfig.getProductQueryUrl();
        log.info("request url: {}", url);
        ResponseEntity<PayCenterRespBaseDTO> responseBase = restTemplate.postForEntity(url,
                new HttpEntity<>(payCenterReqBase, headers), PayCenterRespBaseDTO.class);

        PayCenterRespBaseDTO response = responseBase.getBody();
        log.debug("response: {}", response);
        if(!pay_success_code.equals(response.getRetCode())){
            return false;
        }

        //解码data
        String respData = RSAUtil.decrypt(response.getData(), payCenterConfig.getPayPrivateKey(), charSet);
        log.info("respData: {}", respData);

        //验证签证，验证失败抛出异常
        if (!RSAUtil.verify(respData, response.getSign(), payCenterConfig.getPayPublicKey(), charSet)) {
            return false;
        }

        OrderQueryRespDTO queryResp = mapper.readValue(respData, OrderQueryRespDTO.class);
        return PayCenterConstant.payStatus_1.equals(queryResp.getStatus());
    }
}
