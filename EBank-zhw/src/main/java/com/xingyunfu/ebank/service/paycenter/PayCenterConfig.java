package com.xingyunfu.ebank.service.paycenter;

import com.xingyunfu.ebank.constant.TransferConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ebank 支付配置
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "ebank.pay-center")
public class PayCenterConfig {
    private Boolean payRight;           //是否是真实支付
    private String merchantNo;          //商户号
    private String payPrivateKey;       //支付私钥
    private String payPublicKey;        //支付公钥
    private String productUrl;          //统一下单支付申请地址
    private String productNotifyUrl;    //产品购买支付回调地址
    private String transferUrl;         //提交付款申请
    private String transferNotifyUrl;   //付款结果通知回调地址
    private String productQueryUrl;   //查询单个订单支付状态
    private String fastPaymentApply;    //快捷支付申请授权码
    private String fastPaymentConfirm;  //快捷支付使用授权码确认支付
    private String transferMergeSummaryUrl;    //合并的代付款订单对账汇总地址
    private String transferMergeDetailUrl;      //付款订单对账明细
    private Integer commonUserTransferUpper; //普通用户单次转账上限
    private String startTransferHour;     //定时转账配置，配置内容时小时数，时区东八区
    private Boolean autoTransferAll;  //是否开放所有用户自动转账，false仅开放普通用户，true开发所有用户
    private String scanTransferMergeHour;//定时对账时间点

    /** 开始转帐的小时+分钟数，分钟数是10的倍数 */
    private static Map<Integer, Integer> transferHourMap = new LinkedHashMap<>();

    /** 定时对账时间，分钟数是10的倍数 */
    private static Map<Integer, Integer> scanTransferHourMap = new LinkedHashMap<>();

    /** 付款对账明细时间，开始转帐时间+30分钟 */
    private static Map<Integer, Integer> scanTransferDetailHourMap = new LinkedHashMap<>();

    @PostConstruct
    public void show(){

        this.hourMap(startTransferHour, transferHourMap);
        this.hourMap(scanTransferMergeHour, scanTransferHourMap);
        transferHourMap.entrySet().forEach(entry -> {
            Integer key = entry.getKey();
            Integer value = entry.getValue()+30;
            if(value>=60){
                value = value-60;
                key = key+1;
            }
            scanTransferDetailHourMap.put(key, value);
        });

        log.info("=========> Start transfer merge time: {}", TransferConstant.timeFun.apply(transferHourMap));
        log.info("=========> Start scan transfer merge time: {}", TransferConstant.timeFun.apply(scanTransferHourMap));
        log.info("=========> Start scan transfer merge detail time: {}", TransferConstant.timeFun.apply(scanTransferDetailHourMap));
        log.info("=========> Real payment is {}, real payment money is {}{}", payRight, payRight?"not ":"", "0.01RMB");
        log.info("=========> Account user transfer merge cap {}RMB", commonUserTransferUpper);
        log.info("=========> Auto transfer for all user: {}", autoTransferAll);
    }

    public Map<Integer, Integer> getRightHour(){
        return transferHourMap;
    }
    public Map<Integer, Integer> scanRightHour(){
        return scanTransferHourMap;
    }
    public Map<Integer, Integer> scanDetailRightHour(){
        return scanTransferDetailHourMap;
    }

    private void hourMap(String hourStr, Map<Integer, Integer> hourMap){
        for (String s : hourStr.split(",")) {
            s = s.replaceAll(" ", "");
            if(!s.contains(":")) s += ":00";
            String[] sA = s.split(":");
            Integer hour = Integer.valueOf(sA[0]);
            Integer min = Integer.valueOf(sA[1]) /10 * 10;
            hourMap.put(hour, min);
        }
    }
}
