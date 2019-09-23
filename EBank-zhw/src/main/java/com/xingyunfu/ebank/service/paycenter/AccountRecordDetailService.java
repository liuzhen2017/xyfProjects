package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterRespBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.detail.DetailReqDTO;
import com.xingyunfu.ebank.dto.paycenter.detail.DetailRespDTO;
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

import java.io.UnsupportedEncodingException;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.pay_success_code;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.*;

/**
 * 代付对账明细
 */
@Slf4j
@Service
public class AccountRecordDetailService {
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private RestTemplate restTemplate;
    private static String charSet = "utf-8";

    public DetailRespDTO recordDetail(DetailReqDTO detailReq) throws Exception {
        ObjectMapper mapper = JsonMapperUtil.getMapper();
        String reqJson = mapper.writeValueAsString(detailReq);
        log.info("record detail reqJson: {}", reqJson);

        //请求体
        PayCenterReqBaseDTO payCenterReqBase = new PayCenterReqBaseDTO();
        payCenterReqBase.setMerchantNo(payCenterConfig.getMerchantNo());
        payCenterReqBase.setData(RSAUtil.encryptByPublicKey(reqJson.getBytes(charSet), payCenterConfig.getPayPublicKey()));
        payCenterReqBase.setSign(RSAUtil.sign(reqJson, payCenterConfig.getPayPrivateKey(),charSet));

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = payCenterConfig.getTransferMergeDetailUrl();
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

        DetailRespDTO resp = mapper.readValue(respData, DetailRespDTO.class);
        if(!pay_success_code.equals(resp.getResCode())) {
            throw new EBankException(TRANSFER_APPLY_RESPONSE.setDesc(resp.getResMsg()));
        }
        return resp;
    }
}
