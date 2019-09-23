package com.xingyunfu.ebank.service.paycenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.PayCenterRespBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderReqDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderRespDTO;
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
 * 提交付款申请
 */
@Slf4j
@Service
public class ApplyTransferService {
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private RestTemplate restTemplate;

    private static String charSet = "utf-8";

    public TransferOrderRespDTO applyTransfer(TransferOrderReqDTO transferOrderReq) throws Exception {
        log.info("开始执行转账，向支付中心发送请求");

        //测试环境，支付价格设置为1分钱
        if(!payCenterConfig.getPayRight()) transferOrderReq.setAmount(1);
        //设置异步回调地址 -- 支付中心 -> ebank
        transferOrderReq.setNotifyUrl(payCenterConfig.getTransferNotifyUrl());

        ObjectMapper mapper = JsonMapperUtil.getMapper();
        String reqJson = mapper.writeValueAsString(transferOrderReq);
        log.info("reqJson: {}", reqJson);

        //请求体
        PayCenterReqBaseDTO payCenterReqBase = new PayCenterReqBaseDTO();
        payCenterReqBase.setMerchantNo(transferOrderReq.getMerchantNo());
        payCenterReqBase.setData(RSAUtil.encryptByPublicKey(reqJson.getBytes(charSet), payCenterConfig.getPayPublicKey()));
        payCenterReqBase.setSign(RSAUtil.sign(reqJson, payCenterConfig.getPayPrivateKey(),charSet));

        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = payCenterConfig.getTransferUrl();
        log.info("url: {}", url);
        ResponseEntity<PayCenterRespBaseDTO> responseBase = restTemplate.postForEntity(url,
                new HttpEntity<>(payCenterReqBase, headers), PayCenterRespBaseDTO.class);

        PayCenterRespBaseDTO response = responseBase.getBody();
        log.debug("response: {}", response);
        //网关失败，抛出异常
        if(!pay_success_code.equals(response.getRetCode())){
            throw new EBankException(TRANSFER_APPLY_ORDER_NO);
        }

        //解码data
        String respData = RSAUtil.decrypt(response.getData(), payCenterConfig.getPayPrivateKey(), charSet);
        log.info("respData: {}", respData);

        //验证签证，验证失败抛出异常
        if (!RSAUtil.verify(respData, response.getSign(), payCenterConfig.getPayPublicKey(), charSet)) {
            throw new EBankException(TRANSFER_APPLY_VERIFY_ERROR);
        }

        TransferOrderRespDTO trasferResp = mapper.readValue(respData, TransferOrderRespDTO.class);
        //申请支付失败，抛出异常
        if(!pay_success_code.equals(trasferResp.getResCode())) {
            throw new EBankException(TRANSFER_APPLY_RESPONSE.setDesc(trasferResp.getResMsg()));
        }

        return trasferResp;
    }
}
