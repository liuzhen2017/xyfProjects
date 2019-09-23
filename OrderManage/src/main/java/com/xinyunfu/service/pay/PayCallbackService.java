package com.xinyunfu.service.pay;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.*;
import com.xinyunfu.dto.customer.PageCountDTO;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.customer.UserInfoDTO;
import com.xinyunfu.dto.findFreightDTO;
import com.xinyunfu.dto.infoCenter.MessageDto;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.feign.*;
import com.xinyunfu.infoCenter.SendMessage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderItemMapper;
import com.xinyunfu.mapper.OrderMasterMapper;
import com.xinyunfu.model.*;
import com.xinyunfu.service.*;
import com.xinyunfu.timing.DelayProducer;
import com.xinyunfu.utils.JsonUtils;
import com.xinyunfu.utils.RedisCommonUtil;
import com.xinyunfu.utils.TimeUtils;
import jdk.nashorn.internal.runtime.Undefined;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author XRZ
 * @date 2019/7/31
 * @Description :  支付回调处理
 */
@Slf4j
@Service
public class PayCallbackService {

    @Value("${share.percentage}")
    private String sharePercentage;
    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private EBankFeign eBankFeign;
    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private QuestionBankFeign questionBankFeign;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private IOrderCouponsService iOrderCouponsService;
    @Autowired
    private IOrderMasterService iOrderMasterService;
    @Autowired
    private IOrderErrorService iOrderErrorService;
    @Autowired
    private ProductDocKingFeign productDocKingFeign;
    @Autowired
    private SendMessage sendMessage;
    @Autowired
    private UserWalletFeign userWalletFeign;


    /**
     * 支付回调，修改订单
     *
     * @param orderNo    订单编号
     * @param payNumber  支付交易号
     * @param payAmount  支付金额
     * @param status     支付状态
     * @param orderType  订单类型（主=0，子=1,优惠券订单=3）
     * @return
     */
    @Transactional
    public boolean paySuccess(String orderNo, String payNumber, BigDecimal payAmount, Boolean status, int orderType) {
        String currentUserId = ""; //用户编号
        if(status){ //支付成功
            if(orderType == Common.PAY_COUPONS){  //处理购买优惠券
                return iOrderCouponsService.UpCoupons(orderNo);
            }else if(orderType == Common.MASTER){ //处理主订单
                currentUserId = this.handleMasterOrder(orderNo,payNumber,payAmount);
            }else if(orderType == Common.ITEM){ //处理子订单
                currentUserId = this.handleItemOrder(orderNo,payNumber,payAmount);
            }
            //发送消息
            sendMessage.successfulPayment(currentUserId);
        }else{ // 支付失败
            this.payFail(orderType,orderNo);
        }
        redis.clear(Redis.KEY_ORDER+currentUserId);
        log.info("[支付回调]==========>支付回调已成功处理完成！订单号：{},交易号：{},交易金额：{},订单类型：{}",orderNo,payNumber,payAmount,orderType);
        return true;
    }

    /**
     * 处理支付成功的主订单
     * @param orderNo
     * @param payNumber
     * @param payAmount
     * @param couIds
     * @return
     */
    private String handleMasterOrder(String orderNo,String payNumber,BigDecimal payAmount){
        String currentUserId = "";
        List<String> couIds = new ArrayList<>(); //记录使用的券ID集合
        OrderMaster om = orderMasterMapper.selectOne(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getOrderNo, orderNo));
        BigDecimal amout = new BigDecimal(0); //获取子订单的总金额
        int nums = 0; //记录总购买数量
        List<OrderItem> items = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getMasterNo, orderNo));
        currentUserId = items.get(0).getUserId(); //保存用户ID
        //校验订单状态
        for (OrderItem i : items) {
            if(i.getStatus() != IStatus.UNPAID){ //如果订单状态不为待支付
                items.forEach(item -> {
                    this.errorOrder(item.getItemNo(),item.getCreatedBy()); //订单状态异常  记录该订单
                });
                return currentUserId;
            }
            amout = amout.add(i.getAmount()); //获取每个子订单的应付金额
        }
        if(amout.compareTo(payAmount) != 0)
            throw new CustomException(ExecptionEnum.INCONSISTENT_AMOUNT);
        //将子订单是实付金额 设置 为应付金额
        for (OrderItem i : items) {
            this.getConpons(couIds,i); //获取优惠券ID
            nums += i.getTotalCount();
            i.setPayAmount(i.getAmount());       //设置支付金额
            i.setPayTime(TimeUtils.getDate());   //纪录支付时间
            i.setPayStatus(MStatus.PAY_SUCCESS); //修改为支付成功
            i.setStatus(IStatus.UNSHIPPED);      //修改为待发货
            i.setPayNumber(payNumber);           //设置支付交易号
            if(!iOrderItemService.updateById(i))
                throw new CustomException(ExecptionEnum.UPDATE_ORDER_ITEM_FAIL);
        }
        //套餐的后续操作
        if(om.getOrderType().equals(Common.PACKAGE)){
            ComPackage(currentUserId,orderNo,nums);
        }else{ //非套餐 参与分账
            this.divided(items,currentUserId);
        }
        //消费券
        this.consumerCoupon(couIds,currentUserId);
        //正式下单
        for (OrderItem item : items) {
            this.officalOrder(item);
        }
        return currentUserId;
    }

    /**
     * 处理支付成功的子订单
     * @param orderNo
     * @param payNumber
     * @param payAmount
     * @param couIds
     * @return
     */
    private String handleItemOrder(String orderNo,String payNumber,BigDecimal payAmount){
        String currentUserId = "";
        List<String> couIds = new ArrayList<>(); //记录使用的券ID集合
        //获取子订单对象
        OrderItem item = orderItemMapper.selectOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, orderNo));
        if(null == item)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        if(item.getAmount().compareTo(payAmount) != 0)
            throw new CustomException(ExecptionEnum.INCONSISTENT_AMOUNT);
        //校验订单状态
        currentUserId = item.getUserId(); //保存用户ID
        if(item.getStatus() != IStatus.UNPAID){ //如果订单状态不为待支付
            this.errorOrder(item.getItemNo(),currentUserId); //订单状态异常  记录该订单
            return currentUserId;
        }
        item.setPayAmount(payAmount);           //设置支付金额
        item.setPayTime(TimeUtils.getDate());   //纪录支付时间
        item.setPayStatus(MStatus.PAY_SUCCESS); //修改为支付成功
        item.setStatus(IStatus.UNSHIPPED);      //修改为待发货
        item.setPayNumber(payNumber);           //设置支付交易号
        if(orderItemMapper.updateById(item) != 1)
            throw new CustomException(ExecptionEnum.UPDATE_ORDER_ITEM_FAIL);
        OrderMaster orderMaster = orderMasterMapper.selectOne(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getOrderNo, item.getMasterNo()));
        if(null != orderMaster && orderMaster.getOrderType().equals(Common.PACKAGE)){ //判断是否为套餐
            ComPackage(currentUserId,orderNo,item.getTotalCount());
        }else{ //非套餐 参与分帐
            this.divided(Arrays.asList(item),currentUserId);
        }
        //获取优惠券ID
        this.getConpons(couIds,item);
        //消费券
        this.consumerCoupon(couIds,currentUserId);
        //正式下单
        this.officalOrder(item);
        return currentUserId;
    }


    /**
     * 调用券集市修改券的状态为已使用
     * @param couIds
     * @param currentUserId
     */
    private void consumerCoupon(List<String> couIds,String currentUserId){
        if(!CollectionUtils.isEmpty(couIds)) {
            String cous = StringUtils.join(couIds, ";");
            ResponseInfo<String> res = volumeMarketFeign.updateCoupon(cous, currentUserId, CouponStatus.FAILURE);
            if(!res.isSuccess()){
                log.info("[支付回调]=========>调用券集市，消费券失败！券ID为：{}",cous);
                throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
            }
            log.info("[支付回调]=========>消费券成功！，调用券集市修改优惠券的状态为已使用！券ID为：{}",cous);
        }
    }

    /**
     * 获取子订单中使用券的ID
     * @param couIds
     * @param item
     */
    private void getConpons(List<String> couIds,OrderItem item){
        if(null != item){
            iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                    .eq(OrderCommodity::getEnable,Common.ENABLE)
                    .eq(OrderCommodity::getItemNo,item.getItemNo())).forEach( c -> {
                        if(null != c){
                            for (String id : c.getCoupons().split(",")) {
                                if(StringUtils.isNotEmpty(id))
                                    couIds.add(id);
                            }
                        }
            });
        }
    }

    /**
     *  支付套餐成功后的操作
     *
     *  通知用户系统 发送购买的总数量
     *  通知券集市 给用户赠送指定的券
     *  通知趣味答题，初始化万能券的数量，生成六道题
     * @param currentUserId
     * @param orderNo
     * @param nums
     */
    private void ComPackage(String currentUserId,String orderNo,Integer nums) {
        //通知用户系统 发送购买的总数量
        PageCountDTO vo = iOrderItemService.getUserPageCount(currentUserId);
        log.info("[支付回调]==========>通知用户系统发送购买套餐的总数量。用户编号为：{}，购买套餐的总数量为：{}",vo.getUserNo(),vo.getCount());
        customerManageFeign.meal(Long.valueOf(vo.getUserNo()),Integer.valueOf(vo.getCount()));
        //通知券集市 给用户赠送指定的券
        ResponseInfo<String> res = volumeMarketFeign.distriCouonByOrderNo(orderNo, currentUserId, String.valueOf(nums));
        if(!res.isSuccess()){
            log.error("[支付回调]==========>通知券集市给用户赠送指定的券失败!用户的编号为：{}，订单编号为：{},券集市返回错误为：{}",currentUserId,orderNo,res.getMessage());
            throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
        }
        log.info("[支付回调]==========>通知券集市给用户赠送指定的券成功!用户的编号为：{}，订单编号为：{}",currentUserId,orderNo);
        //获取用户的类型  VIP用户不需要 通知
        ResponseInfo<UserInfoDTO> userRes = customerManageFeign.getUserInfo(Long.valueOf(currentUserId));
        if(!userRes.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,userRes.getCode(),userRes.getMessage());
        if(userRes.getData().getLevel() != UserType.AMBASSADOR){ //如果不为大使
            //通知趣味答题，初始化万能券的数量，生成六道题
            ResponseInfo<String> resq = questionBankFeign.CreateAnswer(currentUserId, orderNo);
            if(!resq.isSuccess()){
                log.error("[支付回调]==========>通知趣味答题，初始化万能券的数量，生成六道题失败!用户的编号为：{}，订单编号为：{},趣味答题返回错误为：{}",currentUserId,orderNo,resq.getMessage());
                throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,resq.getCode(),resq.getMessage());
            }
            log.info("[支付回调]==========>通知趣味答题，初始化万能券的数量，生成六道题成功!用户的编号为：{}，订单编号为：{}",currentUserId,orderNo);

        }
    }

    /**
     * 支付回调返回支付失败的处理
     *
     * @param orderType
     * @param orderNo
     */
    private void payFail(int orderType,String orderNo){
//        String col = "";
//        if(orderType == Common.MASTER){
//            col = "master_no";
//        }else if(orderType == Common.ITEM){
//            col = "item_no";
//        }
//        OrderItem item = new OrderItem();
//        item.setPayStatus(IStatus.UNPAID); //修改订单状态为待支付
//        if(orderItemMapper.update(item,new UpdateWrapper<OrderItem>().eq(col, orderNo)) < 1)
//            throw new RuntimeException("支付回调时修改子订单状态为待支付失败！");
        log.info("[支付回调]=========>支付回调返回支付失败,订单类型为：{},订单编号为：{},暂无处理！",orderType,orderNo);
    }

    /**
     * 正式下单
     * @param item
     */
    private void officalOrder(OrderItem item){
        Integer source = item.getOrderSource();
        if(source == GoodsSource.JD){
            ResponseInfo<String> res = productDocKingFeign.ensureOrder(item.getThirdOrder(), "1");
            if(!res.isSuccess()){
                log.error("[支付回调]==========>调用京东接口正式下单失败！第三方订单号为：{}"+item.getThirdOrder());
                throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
            }
            log.info("[支付回调]==========>调用京东接口正式下单成功！第三方订单号为：{}"+item.getThirdOrder());
        }else if(source == GoodsSource.YYT){
           //TODO 给怡亚通正式下单
        }else if(source == GoodsSource.OTHRE){
           //TODO 给其他下单
        }
    }


    /**
     * 记录异常订单
     * @param itemNo
     */
    @Async
    private void errorOrder(String itemNo,String currentUserId){
        boolean res = iOrderErrorService.save(new OrderError(itemNo, currentUserId, currentUserId));
        log.info("[支付回调]==========>记录异常订单！记录订单返回值：{},订单号为:{}",res,itemNo);
    }


    /**
     * 分帐  （非套餐）
     * @param items         子订单集合
     * @param currentUserId  用户编号
     */
    public void divided(List<OrderItem> items,String currentUserId){
        //根据用户编号获取获取推荐关系
        ResponseInfo<List<String>> res = customerManageFeign.referral(Long.valueOf(currentUserId));
        if(!res.isSuccess()){
            log.error("[支付回调]==========>根据用户编号获取 用户推荐关系失败！分账失败！");
            return;
        }
        List<String> data = res.getData();
        if(data.size() < 1) return; //如果没有推荐人 直接跳过

        List<Long> skuIdArr = new ArrayList<>();
        //获取所有商品的SKUID
        items.forEach( item -> {
            iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>().eq(OrderCommodity::getItemNo, item.getItemNo())).forEach( com -> skuIdArr.add(com.getSkuId()));
        });

        //获取分润的 金额 和 级数
        // TODO: 2019/9/18  等待调用 商品模块
        String skuIds = StringUtils.join(skuIdArr, ";");
        Map<String,List<String>> map = new HashMap<>();
        map.put("147",Arrays.asList("1;0.8","2;0.6"));
        map.put("456",Arrays.asList("1;1.25","2;0.7"));

        List<String> strs = new ArrayList<>(); //记录 用户编号及对应的佣金
        for (List<String> list : map.values()) {
            for (String str : list) { //遍历每件商品的 分帐规则
                String userNo = data.get(Integer.parseInt(str.split(";")[0])); //获取用户编号
                String money = str.split(";")[1];                              //获取佣金
                //根据用户编号 获取订单的购买记录 如果没有购买过则按照 指定 值扣减佣金
               if( iOrderItemService.list(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getUserId,userNo)).size() > 0){
                   // 推荐人未购买佣金的百分比
                   money = String.valueOf(new BigDecimal(money).multiply(new BigDecimal(this.sharePercentage)));
               }
                strs.add(userNo+";"+money);
            }
        }
        //调用 电子钱包 发红包
        ResponseInfo<Object> response = userWalletFeign.redEnvelopes(StringUtils.join(strs, ","));
        if(!response.isSuccess()){
            log.error("[支付回调]==========>调用电子钱包 分账返回失败！用户编号以及对应的佣金为：{}",StringUtils.join(strs, ","));
            return;
        }
        log.info("[支付回调]==========>调用电子钱包分账成功！");
    }

}
