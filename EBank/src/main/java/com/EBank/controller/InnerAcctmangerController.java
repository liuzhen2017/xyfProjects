package com.EBank.controller;

import com.EBank.constant.SysConstant;
import com.EBank.entity.InnerAcctManger;
import com.EBank.entity.PayChannel;
import com.EBank.service.InnerAcctMangerService;
import com.EBank.service.PayChannelService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.jace.utils.ResponseInfo;
import feign.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 内部账户
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/6
 */
@RestController
@RequestMapping("/AccManger/")
public class InnerAcctmangerController {

    @Autowired
    public InnerAcctMangerService innerAcctMangerService;
    @Autowired
    public PayChannelService payChannelService;
    /**
     * 创建内部账户
     * @param json
     */
    @RequestMapping(value="createdInnerAcct",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseInfo createdInnerAcct(@RequestBody String json){
        InnerAcctManger innerAcctManger = JSONObject.parseObject(json,InnerAcctManger.class);
       return  innerAcctMangerService.addInner(innerAcctManger);

    }
    @RequestMapping(value="queryByChain",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseInfo<InnerAcctManger> queryByChain(String chain){
        InnerAcctManger innerAcctManger =new InnerAcctManger();
        innerAcctManger.setUserId(chain);
        innerAcctManger.setAccType(SysConstant.userType_3);
        return innerAcctMangerService.query(innerAcctManger);
    }
    /**
     * 入账
     * @param userNo 当前操作用户编号
     * @param orderNo 订单编号->唯一
     * @param amount 交易金额
     * @param channel 渠道,记录是哪个服务的入账,订单树微服务不需要 回调,其他微服务需要回调
     * @param buyTpe 购买类型，00商品，01套餐
     * @param orderCount 订单数量
     * @return
     */
    @RequestMapping(value = "accountEntry.do",method ={RequestMethod.POST,RequestMethod.GET })
    public ResponseInfo<String> accountEntry(@RequestParam String userNo,@RequestParam String orderNo,@RequestParam BigDecimal amount,@RequestParam String channel,@RequestParam int payType,@RequestParam String returnUrl,@RequestParam String subject,@RequestParam String tradeType,Integer orderCount,String buyTpe,Integer orderType){

        return  innerAcctMangerService.accountEntry(userNo,orderNo,amount,channel,payType,returnUrl,subject,tradeType,orderCount,buyTpe,orderType);
    }

    /**
     *  卡券交易付款
     *  1.A推荐用户B
     * 2.B用户购买套餐,订单编号001
     * 3.将001订单编号传给支付平台,进行支付.
     * @param orderNo 上一手订单编号
     * @param recvUserNo 接收用户
     * @param sourceUserNo 购买订单来源人
     * @param amount 金额
     * @param channel 渠道->微服务名
     * @return
     */
    @RequestMapping("paymentByCoupon.do")
    public ResponseInfo<String> paymentByCoupon(@RequestParam String recvUserNo,@RequestParam BigDecimal amount,@RequestParam String channel,@RequestParam String sourceUserNo,@RequestParam String orderNo){
        return innerAcctMangerService.paymentByCoupon(recvUserNo,amount,channel,sourceUserNo,orderNo);
    }

    /**
     * 查询支付渠道
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("queryPayChanel.do")
    public ResponseInfo<IPage<PayChannel>> queryPayChanel(Integer page,int pageSize){
        return  payChannelService.list(page,pageSize);
    }

    /**
     * 异步通知->入账回调
     * @param json
     * @return
     */
    @PostMapping("callbackAccountEntry.do")
    public String callbackAccountEntry(@RequestBody String json){
      return  innerAcctMangerService.callbackAccountEntry(json);
    }
    /**
     * 异步通知
     * @param json
     * @return
     */
    @PostMapping("callbackPayment.do")
    public String callbackPayment(@RequestBody String json){
        return innerAcctMangerService.callbackPayment(json);
    }

    /**
     * 向供应商支付金额
     * 1.记录流水
     * 2.账户增加
     * @param chainUserId
     * @param amount
     * @param orderNo
     * @param chanel
     * @return
     */
    @RequestMapping("paymentByChain.do")
    public ResponseInfo<String> paymentByChain(@RequestParam String chainUserId,@RequestParam BigDecimal amount,@RequestParam String orderNo,@RequestParam String chanel){
        return innerAcctMangerService.paymentByChain(chainUserId,amount,orderNo,chanel);
    }
    /**
     * 供应商提现
     * 1.记录流水
     * 2.账户减少
     * @param chainUserId
     * @param amount 提现金额
     * @return
     */
    @RequestMapping("chainPresentation.do")
    public ResponseInfo<String> chainPresentation(@RequestParam String chainUserId,@RequestParam BigDecimal amount){
        return innerAcctMangerService.chainPresentation(chainUserId,amount);
    }
}

