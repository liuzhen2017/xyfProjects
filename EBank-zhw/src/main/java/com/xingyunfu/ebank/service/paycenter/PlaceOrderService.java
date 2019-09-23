package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterRespBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderReqDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderRespDTO;
import com.xingyunfu.ebank.exception.EBankException;
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

import static com.xingyunfu.ebank.constant.InnerAccountConstant.*;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.*;

/**
 * 统一下单支付接口调用
 */
@Slf4j
@Service
public class PlaceOrderService {
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private RestTemplate restTemplate;

    private static String charSet = "utf-8";
    /**
     * 申请支付，获得支付URL和支付回调
     * retCode是网关的响应吗，resCode才是业务响应码
     */
    public PlaceOrderRespDTO applyPay(PlaceOrderReqDTO placeOrderReq) throws Exception {

        //测试环境，支付价格设置为1分钱
        if(!payCenterConfig.getPayRight()){ placeOrderReq.setAmount(1); }

        //设置异步回调地址 -- 支付中心 -> ebank
        placeOrderReq.setNotifyUrl(payCenterConfig.getProductNotifyUrl());

        ObjectMapper mapper = JsonMapperUtil.getMapper();
        String reqJson = mapper.writeValueAsString(placeOrderReq);
        log.info("reqJson: {}", reqJson);

        //请求体
        PayCenterReqBaseDTO payCenterReqBase = new PayCenterReqBaseDTO();
        payCenterReqBase.setMerchantNo(placeOrderReq.getMerchantNo());
        payCenterReqBase.setData(RSAUtil.encryptByPublicKey(reqJson.getBytes(charSet), payCenterConfig.getPayPublicKey()));
        payCenterReqBase.setSign(RSAUtil.sign(reqJson, payCenterConfig.getPayPrivateKey(),charSet));

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = payCenterConfig.getProductUrl();
        log.info("request url: {}", url);
        ResponseEntity<PayCenterRespBaseDTO> responseBase = restTemplate.postForEntity(url,
                new HttpEntity<>(payCenterReqBase, headers), PayCenterRespBaseDTO.class);

        PayCenterRespBaseDTO response = responseBase.getBody();
        log.debug("response: {}", response);
        //网关失败，抛出异常
        if(!pay_success_code.equals(response.getRetCode())){
            throw new EBankException(PRODUCT_ADD_RESPONSE_ERROR);
        }

        //解码data
        String respData = RSAUtil.decrypt(response.getData(), payCenterConfig.getPayPrivateKey(), charSet);
        log.info("respData: {}", respData);

        //验证签证，验证失败抛出异常
        if (!RSAUtil.verify(respData, response.getSign(), payCenterConfig.getPayPublicKey(), charSet)) {
            throw new EBankException(PRODUCT_ADD_VERIFY_ERROR);
        }

        PlaceOrderRespDTO orderResp = mapper.readValue(respData, PlaceOrderRespDTO.class);
        //申请支付失败，抛出异常
        if(!pay_success_code.equals(orderResp.getResCode())) {
            throw new EBankException(PRODUCT_ADD_RESPONSE.setDesc(orderResp.getResMsg()));
        }
        return orderResp;
    }
}
