package com.xinyunfu.service.pay;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.CouponStatus;
import com.xinyunfu.constant.IStatus;
import com.xinyunfu.dto.PayDTO;
import com.xinyunfu.dto.ReturnPayDTO;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.ebank.ProductRecordAddDTO;
import com.xinyunfu.dto.ebank.ProductRecordAddRespDTO;
import com.xinyunfu.dto.findFreightDTO;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.EBankFeign;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderCoupons;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.model.OrderMaster;
import com.xinyunfu.service.IOrderCommodityService;
import com.xinyunfu.service.IOrderCouponsService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.IOrderMasterService;
import com.xinyunfu.timing.DelayProducer;
import com.xinyunfu.utils.JsonUtils;
import com.xinyunfu.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/8/2
 * @Description : 获取支付请求
 */
@Slf4j
@Service
public class PayRequstService {

    @Value("${returnUrl}")
    private String returnUrl;
    @Value("${order.payTimeOut}")
    private Long payTimeOut;
    @Autowired
    private IOrderMasterService iOrderMasterService;
    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private IOrderCouponsService iOrderCouponsService;
    @Autowired
    private EBankFeign eBankFeign;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private DelayProducer delayProducer;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;

    /**
     * 获取唤起支付的URL  主订单
     * @param currentUserId
     * @param
     * @param request
     * @return
     */
    public ReturnPayDTO payment(String currentUserId, PayDTO vo, HttpServletRequest request) {
        if(StringUtils.isEmpty(vo.getOrderNo()) || StringUtils.isEmpty(vo.getChannle()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        String subject = "京东商品";
        String productType = Common.COMMODITY;               //订单类型 默认商品
        OrderMaster om = iOrderMasterService.getOne(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getOrderNo, vo.getOrderNo()));
        if(null != om && om.getOrderType().equals(Common.PACKAGE)){
            productType = Common.PACKAGE; //设置为套餐
            subject = "套餐商品";
            //调用券集市判断是否可以购买
            ResponseInfo<String> res = volumeMarketFeign.queryIsCanBuyByService(currentUserId);
            if(!res.isSuccess())
                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        }
        //根据主订单编号 获取 子订单列表
        List<OrderItem> list = iOrderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getMasterNo, vo.getOrderNo()));
        return checkeMoney(om.getAddressId(),vo.getOrderNo(),list,currentUserId,vo.getChannle(),subject,productType,request,Common.MASTER,vo.getIpAddr());
    }

//    /**
//     * （未付款中调用）去付款  子订单
//     * @param currentUserId
//     * @param object
//     * @param request
//     * @return
//     */
//    public ReturnPayDTO toPay(String currentUserId, PayDTO vo, HttpServletRequest request) {
//        if(StringUtils.isEmpty(vo.getOrderNo()) || StringUtils.isEmpty(vo.getChannle()))
//            throw new CustomException(ExecptionEnum.ERROR_PARAM);
//        String productType = Common.COMMODITY;               //订单类型
//        OrderItem orderItem = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, vo.getOrderNo()));
//        OrderMaster om = iOrderMasterService.getOne(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getOrderNo, orderItem.getMasterNo()));
//        if(null != om && om.getOrderType().equals(Common.PACKAGE)){
//            productType = Common.PACKAGE; //设置为套餐
//            //调用券集市判断是否可以购买
//            ResponseInfo<String> res = volumeMarketFeign.queryIsCanBuyByService(currentUserId);
//            if(!res.isSuccess())
//                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
//        }
//        return checkeMoney(om.getAddressId(),vo.getOrderNo(), Arrays.asList(orderItem),currentUserId,vo.getChannle(),vo.getOrderNo(),productType,request,Common.ITEM,vo.getIpAddr());
//    }

    /**
     *  购买优惠券 获取唤起支付的URL
     * @param currentUserId
     * @param object
     * @param request
     * @return
     */
    public ReturnPayDTO payCoupons(String currentUserId, PayDTO dto, HttpServletRequest request) {
        if(StringUtils.isEmpty(dto.getOrderNo()) || StringUtils.isEmpty(dto.getChannle()))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        //通过订单号获取优惠券订单信息
        OrderCoupons oc = iOrderCouponsService.getOrderCoupons(dto.getOrderNo());
        if(null == oc)
            throw new CustomException(ExecptionEnum.BY_ORDERNO_GET_COUPONS_INFO_ISNULL);
        //调用电子银行 获取唤起支付的URL
        ProductRecordAddDTO vo = new ProductRecordAddDTO();
        vo.setUserNo(Long.valueOf(currentUserId));
        vo.setOrderNo(dto.getOrderNo());
        vo.setProductType(Common.COUPONS);
        vo.setAmount(oc.getTotalPrice());
        vo.setPayType(oc.getPayType());
        vo.setChannel(dto.getChannle());
        vo.setReturnUrl(returnUrl);
        vo.setServerName(Common.SERVICE_NAME);
        vo.setOrderType(Common.PAY_COUPONS);
        vo.setSubject("优惠券");
        vo.setClientIp(dto.getIpAddr());
        vo.setPayTimeOut(TimeUtils.payTimeOut(payTimeOut)); //设置支付限时
        ResponseInfo<ProductRecordAddRespDTO> res = eBankFeign.accountEntry(vo);
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,res.getCode(),res.getMessage());
        ProductRecordAddRespDTO data = res.getData();
        if(!data.getOrderNo().equals(dto.getOrderNo())) //校验 订单编号是否一致
            throw new CustomException(ExecptionEnum.EBANK_RETURN_ORDERNO_ATYPISM);
        String url = data.getPayLoad();  //获取唤起的Url
        if(StringUtils.isEmpty(url))
            throw new CustomException(ExecptionEnum.EBAKN_RETURN_URL_ISNULL);
        Integer openType = data.getOpenType();
        if(null == openType)
            throw new CustomException(ExecptionEnum.EBANK_RETURN_OPENTYPE_ISNULL);
        return new ReturnPayDTO(openType,url);
    }



    /**
     *  校验订单的状态必须为待支付
     *  校验商品金额是否变化
     *  校验邮费是否有改动
     *  校验优惠券的状态必须为 锁定中
     *
     * @param addressId     地址的ID
     * @param orderNo       支付的订单编号
     * @param itemList      子订单集合
     * @param currentUserId 用户的编号
     * @param channel       支付的渠道
     * @param subject       交易主体 （套餐商品/京东商品）
     * @param productType   购买的类型 00商品，01套餐
     * @param request       当前的请求
     * @param orderType     订单类型（主订单=0，子订单=1）
     * @param ipAddr        IP地址
     * @return 返回唤起的URL 打开方式
     */
    private ReturnPayDTO checkeMoney(Long addressId, String orderNo, List<OrderItem> itemList, String currentUserId,
                                     String channel, String subject, String productType, HttpServletRequest request, Integer orderType, String ipAddr){
        if(itemList.size() < 1)
            throw new CustomException(ExecptionEnum.BY_ORDERNO_GET_ORDERINFO);
        //获取的我地址信息
        ResponseInfo<ShippingAddressInfoDTO> addressRes = customerManageFeign.getAddress(addressId);
        if(!addressRes.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,addressRes.getCode(),addressRes.getMessage());
        ShippingAddressInfoDTO ship = addressRes.getData();
        String pNo = ship.getAreaInfo().getProvince().getAreaCode(); //省编号
        String cNo = ship.getAreaInfo().getCity().getAreaCode();     //市编号
        String addres = ship.getAreaInfo().getProvince().getAreaLongName()+"-"+ship.getAreaInfo().getCity().getAreaLongName()+"-"+ship.getAreaInfo().getRegion().getAreaLongName()+"-";
        if( ship.getAreaInfo().getTown() != null){
            addres = addres + ship.getAreaInfo().getTown().getAreaLongName();
        }
        //获取我的优惠券信息 <优惠券ID,优惠券对象>
        Map<String, CouponList> couponList = iOrderItemService.getCouponList(currentUserId);
        //获取该订单下的商品最新信息 <SKUID, 商品信息对象>
        Map<Long, FreightDTO> comInfo = this.getComInfo(pNo,cNo,itemList,addres);
        ProductRecordAddDTO vo = new ProductRecordAddDTO();
        for (OrderItem item : itemList) {
            //校验订单的状态必须为待支付
            if(item.getStatus() != IStatus.UNPAID)
                throw new CustomException(ExecptionEnum.ORDER_STATUC_ERROR);
            //获取订单商品集合
            List<OrderCommodity> list = iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                    .eq(OrderCommodity::getItemNo, item.getItemNo()));
            for (OrderCommodity commodity : list) {
                //校验商品单价是否有变动
                if(comInfo.get(commodity.getSkuId()).getPrice().compareTo(commodity.getPrice()) != 0)
                    throw new CustomException(ExecptionEnum.GOODS_PRICE_CHANGE);
                //校验商品的邮费是否有变动
                if(comInfo.get(commodity.getSkuId()).getPostage().compareTo(commodity.getFreight()) != 0)
                    throw new CustomException(ExecptionEnum.GOODS_FREIGHT_CHANGE);
                String[] coupons = commodity.getCoupons().split(",");
                for (int i = 0; i < coupons.length; i++) {
                    if(StringUtils.isEmpty(coupons[i])) break; //如果券为空 跳出当前循环
                    CouponList cp = couponList.get(coupons[i]);
                    if(null == cp) break;
                    String couponStatus = cp.getCouponStatus();
                    if(!couponStatus.equals(CouponStatus.LOCK)){
                        //校验优惠券的状态必须为 锁定中
                        log.error("[订单服务]========>支付校验时发现优惠券状态异常！优惠券ID为：{}，状态为：{}",coupons[i],couponStatus);
                        throw new CustomException(ExecptionEnum.COUPONS_STATUS_ERROR);
                    }
                }
            }
            vo.setAmount(vo.getAmount().add(item.getAmount())); //叠加子订单应付金额
            vo.setPayType(item.getPayType());                 //获取支付方式
        }
        vo.setUserNo(Long.valueOf(currentUserId));
        vo.setOrderNo(orderNo);
        vo.setProductType(productType);
        vo.setChannel(channel);
        vo.setReturnUrl(returnUrl);
        vo.setSubject(subject);
        vo.setServerName(Common.SERVICE_NAME);
        vo.setOrderType(orderType);
        vo.setClientIp(ipAddr);
        vo.setPayTimeOut(TimeUtils.payTimeOut(payTimeOut)); //设置支付限时
        log.info("[订单服务]==========>支付时校验订单数据通过！订单编号为：{}",orderNo);
        //调用电子银行，获取URL
        ResponseInfo<ProductRecordAddRespDTO> res = eBankFeign.accountEntry(vo);
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,res.getCode(),res.getMessage());
        ProductRecordAddRespDTO data = res.getData();
        if(!data.getOrderNo().equals(orderNo))  //校验 订单编号是否一致
            throw new CustomException(ExecptionEnum.EBANK_RETURN_ORDERNO_ATYPISM);
        String url = data.getPayLoad();  //获取唤起的Url
        if(StringUtils.isEmpty(url))
            throw new CustomException(ExecptionEnum.EBAKN_RETURN_URL_ISNULL);
        Integer openType = data.getOpenType();
        if(null == openType)
            throw new CustomException(ExecptionEnum.EBANK_RETURN_OPENTYPE_ISNULL);
        return new ReturnPayDTO(openType,url);
    }


    /**
     * 根据订单列表获取 最新商品信息
     *
     * @param pNo 省编号
     * @param cNo 市编号
     * @param addres 省市区名称
     * @param itemList
     * @return   Map<SKUID, 商品信息对象>
     */
    private Map<Long, FreightDTO> getComInfo(String pNo, String cNo, List<OrderItem> itemList,String addres){
        Map<Long, Integer> orderMap = new HashMap<>();
        itemList.forEach( item -> {
            iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                    .eq(OrderCommodity::getItemNo,item.getItemNo())).forEach( c -> {
                if( null == c ) return;
                orderMap.put(c.getSkuId(),c.getBuyCount());
            });
        });
        Map<Long, FreightDTO> map = new HashMap<>();
        findFreightDTO findFreightDTO = new findFreightDTO();
        findFreightDTO.setProvince(pNo);
        findFreightDTO.setCity(cNo);
        findFreightDTO.setAddres(addres);
        findFreightDTO.setMap(orderMap);
        ResponseInfo<List<FreightDTO>> res = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        res.getData().forEach( f -> {
            map.put(f.getSkuId(),f);  // Map<SKUID, FreightDTO>
        });
        return map;
    }

}
