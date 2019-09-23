package com.EBank.util;

import com.EBank.agent.RestTemplateAgent;
import com.EBank.config.PayManger;
import com.EBank.entity.AccFlow;
import com.EBank.entity.InnerAcctManger;
import com.EBank.entity.TransactionSendLog;
import com.EBank.service.TransactionRecvLogService;
import com.EBank.service.TransactionSendLogService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.EBank.constant.SysConstant.notify_payPlatform;
import static com.EBank.constant.SysConstant.notify_payment;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/9
 */
@Service
@Slf4j
public class PayMangerUtil {

    @Autowired
    RestTemplateAgent restTemplateAgent;
    @Autowired
    PayManger payManger;
    @Autowired
    TransactionSendLogService sendLogService;
    @Autowired
    TransactionRecvLogService recvLogService;

    /**
     * 发起下单
     * @param accFlow
     * @param payType
     * @param returnUrl
     * @param subject
     * @param tradeType
     * @return
     */
    public String placeAnOrder(AccFlow accFlow,int payType,String returnUrl,String subject,String tradeType,String priValue){
        log.info("begin place An Order accFlow ={},payType ={},retruenUrl={},subject={},subject={},tradeType={}",accFlow,payType,returnUrl,subject,tradeType);
        TransactionSendLog sendLog =new TransactionSendLog();
        sendLog.setMerchantNo(payManger.getMerchantNo());
        sendLog.setOrderNo(accFlow.getOrderNumber());
        sendLog.setUserName(accFlow.getPayAccNo());
        //amount.multiply(new BigDecimal(100));
        //TODO暂时全部修改为1分钱
        sendLog.setAmount(new BigDecimal(1));
        //支护平台金额以分为单位
//        sendLog.setAmount(accFlow.getPayAmount().multiply(new BigDecimal(100)));
        sendLog.setSubject(subject);
        sendLog.setPayType(payType);
        sendLog.setReturnUrl(returnUrl);
        sendLog.setNotifyUrl(payManger.getNotifyurlRemittanceurl());
        sendLog.setPrivateValue(notify_payment);
        JSONObject jb =new JSONObject();
        jb.put("merchantNo",payManger.getMerchantNo());
        Map<String,Object> dataMap =new HashMap<>();

        dataMap =JSONObject.parseObject(JSONObject.toJSONString(sendLog),dataMap.getClass());
        dataMap.put("userCode",accFlow.getUserNo());
        dataMap.put("privateValue",priValue);
        dataMap.put("tradeType",tradeType);
        //去除空字符
        dataMap=trimNull(dataMap);

        jb.put("data",dataMap);
        sendLogService.save(sendLog);
        String msg =restTemplateAgent.postForObject(payManger.getBaseUrl()+payManger.getPaymentUrl(),jb,true);
        return msg;

    }

    /**
     * 查询下单结果
     * @param orderNo 订单号
     * @param payOrderno 支付编号
     * @return
     */
    public String queryplaceAnOrder(String orderNo,String payOrderno){

        //加密算法
        JSONObject jb =new JSONObject();
        jb.put("merchantNo",payManger.getMerchantNo());
        Map<String,Object> dataMap =new HashMap<>();
        dataMap.put("outTradeNo",orderNo);
        dataMap.put("sysOrderNo",payOrderno);

        jb.put("data",dataMap);

        String msg =restTemplateAgent.postForObject(payManger.getBaseUrl()+payManger.getPaymentUrl(),jb,true);
        return msg;

    }

    /**
     * 提交付款申请
     * @param accFlow
     * @param payType
     * @param returnUrl
     * @param subject
     * @param tradeType
     * @return
     */
    public String payment(AccFlow accFlow, int payType, String returnUrl, String subject, String tradeType, JSONObject userAcc){
        TransactionSendLog sendLog =new TransactionSendLog();
        sendLog.setMerchantNo(payManger.getMerchantNo());
        sendLog.setOrderNo(accFlow.getOrderNumber());
        sendLog.setUserName(accFlow.getPayAccNo());
        //TODO暂时全部修改为1分钱
        sendLog.setAmount(new BigDecimal(1));
        //支护平台以分为单位
//        sendLog.setAmount(accFlow.getPayAmount().multiply(new BigDecimal(100)));
        sendLog.setSubject(subject);
        sendLog.setBankName(userAcc.getString("bankName"));
        sendLog.setBranchName(userAcc.getString("branchName"));
        sendLog.setCardNo(accFlow.getReceAccNo());
        sendLog.setChannelType(tradeType);
        sendLog.setIdCardNo(userAcc.getString("accountNo"));

        sendLog.setUserName(userAcc.getString("accountName"));
        sendLog.setNotifyUrl(payManger.getNotifyurlpaymentUrl());
        sendLog.setProvinceName(userAcc.getString("provinceName"));
        sendLog.setCityName(userAcc.getString("cityName"));
        sendLog.setPayType(payType);
        sendLog.setPrivateValue(notify_payPlatform);

        //加密算法
        JSONObject jb =new JSONObject();
        jb.put("merchantNo",payManger.getMerchantNo());
        Map<String,Object> dataMap =new HashMap<>();
        dataMap =JSONObject.parseObject(JSONObject.toJSONString(sendLog),dataMap.getClass());
        //去除空字符
        dataMap=trimNull(dataMap);
        dataMap.put("userCode",userAcc.getString("accountName"));
      //  dataMap.put("cardNo",userAcc.getString("cardNo"));
        sendLogService.save(sendLog);
        jb.put("data",dataMap);
        String msg =restTemplateAgent.postForObject(payManger.getBaseUrl()+payManger.getRemittanceUrl(),jb,true);
        return msg;

    }

    /**
     * 查询付款结果
     * @param orderNo 订单号
     * @param payOrderno 支付编号
     * @return
     */
    public String queryPayment(String orderNo,String payOrderno){
        return this.queryplaceAnOrder(orderNo,payOrderno);
    }

    public  static Map<String,Object> trimNull(Map<String,Object> dataMap){
        Iterator<String> it = dataMap.keySet().iterator();
        String key =null;
        while (it.hasNext()){
            key =it.next();
            if(dataMap.get(key) ==null){
                dataMap.remove(key);
            }
        }
        return dataMap;
    }


}
