package com.xinyunfu.customer.service.rabbitmq;

import com.rnmg.jace.utils.ResponseInfo;
import com.xinyunfu.customer.config.CustomerVerifyCodeConfig;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.feigin.InfoCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerifyCode {

    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private InfoCenter infoCenter;
    @Autowired private CustomerVerifyCodeConfig customerVerifyCodeConfig;

    /**
     * 发送验证码
     */
    public void send(String phone, String codeType) throws CustomerException {

        String temp = "{\"recvObject\": \"%s\", \"sendType\":\"01\", \"templateNo\":\"%s\"}";
        if(customerVerifyCodeConfig.getSend()) {
            ResponseInfo<String> sendRes = infoCenter.sendMsa(
                    String.format(temp, phone, customerVerifyCodeConfig.verifyCode(codeType)));
            if(!sendRes.getCode().equals("0000"))
                throw new CustomerException(CustomerExceptionEnum.COMMON_SEND_VERIFY_CODE.setDesc(sendRes.getData()));
        }else{
            log.info("send verify code closed!");
        }
    }

    /**
     * 校验验证码
     */
    public Boolean check(String phone,String code,String codeType){
        String temp = "{\"phone\": \"%s\", \"code\":\"%s\", \"templateNo\":\"%s\"}";
        Boolean sendResult = true;
        if(customerVerifyCodeConfig.getSend()) {
            sendResult = infoCenter.checkSendMsg(String.format(temp, phone, code, customerVerifyCodeConfig.verifyCode(codeType))).getData();
        }
        return sendResult;
    }
}
