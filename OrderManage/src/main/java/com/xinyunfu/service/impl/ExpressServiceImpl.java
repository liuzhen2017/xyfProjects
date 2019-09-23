package com.xinyunfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netflix.discovery.converters.Auto;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.GoodsSource;
import com.xinyunfu.dto.docking.JDLogisticsDto;
import com.xinyunfu.dto.docking.JDOrderTrackDto;
import com.xinyunfu.dto.docking.JDThirdOrderDto;
import com.xinyunfu.dto.express.ExpReqDto;
import com.xinyunfu.feign.ProductDocKingFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.ExpressMapper;
import com.xinyunfu.model.Express;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.service.IExpressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.utils.HttpClientUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物流公司表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Data
@Service
@Slf4j
public class ExpressServiceImpl extends ServiceImpl<ExpressMapper, Express> implements IExpressService {

    /**
     * 客户授权Key
     */
    @Value("${express.key}")
    private String key;

    /**
     * 公司编号 实时查询
     */
    @Value("${express.customer}")
    private String customer;

    /**
     * 请求的URL
     */
    @Value("${express.url}")
    private String url;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private ProductDocKingFeign productDocKingFeign;


    /**
     * 根据订单号获取物流信息
     * @param orderNo
     * @return
     */
    @Override
    public String getExpressInfoByOrderNo(String orderNo){
        //通过订单号获取 订单信息
        OrderItem item = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, orderNo));
        if(null == item)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        if(item.getOrderSource() == GoodsSource.JD){ //查询京东物流信息
            log.info("[物流信息]==========>开始调用京东物流接口查询！");
            ResponseInfo<JDThirdOrderDto> thirdOrderDto =  productDocKingFeign.getOrderId(item.getThirdOrder().toString());
            if(!thirdOrderDto.isSuccess())
                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,thirdOrderDto.getCode(),thirdOrderDto.getMessage());
            ResponseInfo<JDLogisticsDto> logisticsDto =  productDocKingFeign.getOrderTrack(thirdOrderDto.getData().getJdOrderId());
            if(!logisticsDto.isSuccess())
                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,logisticsDto.getCode(),logisticsDto.getMessage());
            return  JSON.toJSONString((logisticsDto.getData().getOrderTrack()));
        }
        if (item.getOrderSource() == GoodsSource.YYT){ //查询怡亚通物流信息
            // TODO: 2019/8/17 等待对接怡亚通
        }
        log.info("[物流信息]==========>开始调用快递100接口查询！");
        // 根据快递公司 获取 公司编号  调快递100查询物流信息
        Express express = this.getOne(new LambdaQueryWrapper<Express>().eq(Express::getExpressName, item.getShippingCompName()));
        if(null == express)
            throw new CustomException(ExecptionEnum.ERROR_EXPRESS_IS_NOT);
        return this.getExpressInfo(new ExpReqDto(express.getExpressCode(),item.getShippingSn()));
    }


    /**
     * 获取快递信息
     * @param param
     * @return
     */
    public String getExpressInfo(ExpReqDto param){
        String parn = JSONObject.toJSONString(param);
        String sign = signMD5(parn+key+customer);
        Map params = new HashMap();
        params.put("param",parn);
        params.put("sign",sign);
        params.put("customer",customer);
        return HttpClientUtils.doPost(url,params);
    }


    /**
     * 快递100 签名
     * @param s
     * @return
     */
    public static String signMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return bytesToHex(bytes);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for(int i = 0; i < bytes.length; ++i) {
            ret.append(HEX_DIGITS[bytes[i] >> 4 & 15]);
            ret.append(HEX_DIGITS[bytes[i] & 15]);
        }
        return ret.toString().toUpperCase();
    }



}
