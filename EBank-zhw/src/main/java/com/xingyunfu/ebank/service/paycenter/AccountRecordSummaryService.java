package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterRespBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.summary.SummaryReqDTO;
import com.xingyunfu.ebank.dto.paycenter.summary.SummaryRespDTO;
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

import static com.xingyunfu.ebank.constant.InnerAccountConstant.pay_success_code;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.*;

/**
 * 代付对账汇总
 */
@Slf4j
@Service
public class AccountRecordSummaryService {

    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private RestTemplate restTemplate;
    private static String charSet = "utf-8";

    public SummaryRespDTO recordSummary(SummaryReqDTO summaryReq) throws Exception {

        ObjectMapper mapper = JsonMapperUtil.getMapper();
        String reqJson = mapper.writeValueAsString(summaryReq);
        log.info("record summary reqJson: {}", reqJson);

        //请求体
        PayCenterReqBaseDTO payCenterReqBase = new PayCenterReqBaseDTO();
        payCenterReqBase.setMerchantNo(payCenterConfig.getMerchantNo());
        payCenterReqBase.setData(RSAUtil.encryptByPublicKey(reqJson.getBytes(charSet), payCenterConfig.getPayPublicKey()));
        payCenterReqBase.setSign(RSAUtil.sign(reqJson, payCenterConfig.getPayPrivateKey(),charSet));

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = payCenterConfig.getTransferMergeSummaryUrl();
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
        SummaryRespDTO resp = mapper.readValue(respData, SummaryRespDTO.class);
        if(!pay_success_code.equals(resp.getResCode())) {
            throw new EBankException(TRANSFER_APPLY_RESPONSE.setDesc(resp.getResMsg()));
        }
        return resp;
    }
}
