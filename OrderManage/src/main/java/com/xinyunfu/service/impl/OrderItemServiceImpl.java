package com.xinyunfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.*;
import com.xinyunfu.dto.*;
import com.xinyunfu.dto.back.BackOrderDTO;
import com.xinyunfu.dto.back.SelectOrderDTO;
import com.xinyunfu.dto.customer.PageCountDTO;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.infoCenter.MessageDto;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.EBankFeign;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.infoCenter.SendMessage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderItemMapper;
import com.xinyunfu.model.*;
import com.xinyunfu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.service.othreOrder.PreorderService;
import com.xinyunfu.service.pay.PayCallbackService;
import com.xinyunfu.timing.DelayProducer;
import com.xinyunfu.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单子表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private IOrderMasterService iOrderMasterService;
    @Autowired
    private IOrderCartService iOrderCartService;
    @Autowired
    private DelayProducer delayProducer;
    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private EBankFeign eBankFeign;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private IPayTypeService iPayTypeService;
    @Autowired
    private PreorderService preorderService;
    @Autowired
    private IInvoiceInfoService iInvoiceInfoService;
    @Autowired
    private SendMessage sendMessage;
    @Autowired
    private PayCallbackService payCallbackService;
    @Autowired
    private ITimeService iTimeService;


    /**
     * 生成订单
     * @param currentUserId 当前请求中的ID
     * @param AddOrderVo   下单对象
     * @return 返回 订单号
     */
    @Override
    @Transactional
    public String addOrder(String currentUserId, AddOrderDTO vo) {
        Map<String, CouponList> couponList = this.getCouponList(currentUserId); //获取我的优惠券信息 <优惠券ID,优惠券对象>
        Map<Long, FreightDTO> infoMap = this.getList(vo); //获取选中商品的详细信息 <SKUID,商品信息对象>
        //如果商品来源为京东
        Integer source = infoMap.get(Long.valueOf(vo.getGoodsVos().get(0).getCom().get(0).getSkuId())).getSource();
        log.info("[下单操作]=========>下单时订单来源为：{}",source);
        if (source == GoodsSource.OTHRE) { //普通商品
            iTimeService.checkTime(Common.TIME_ORDER,currentUserId);
        }else if(source == GoodsSource.JD){ //京东商品
            iTimeService.checkTime(Common.ITME_JD,currentUserId);
        }
        Map<Long, Long> preMap = preorderService.preOrder(currentUserId, vo, infoMap); //预下单 Map<商家ID，第三方订单号>
        OrderMaster om = iOrderMasterService.getOrderMaster(vo, currentUserId);  //生成主订单
        if(!iOrderMasterService.save(om))
            throw new CustomException(ExecptionEnum.CREATE_ORDER_SAVE_MASTER_FAIL);
        List<Long> couIds = new ArrayList<>(); //使用的券集合
        Map<Long,Integer> goodsMap = new HashMap<>(); //记录sku和购买的数量
        BigDecimal money = new BigDecimal(0);       //记录整笔订单的金额
        List<String> itemNos = new ArrayList<>(); //记录子订单编号用于延时消息
        //生成子订单
        for (GoodsDTO goodsVo : vo.getGoodsVos()) {
            OrderItem item = new OrderItem();
            item.setThirdOrder(preMap.get(goodsVo.getStoreId())); //设置第三方订单号
            item.setPayType(vo.getPayType());
            item.setUserId(currentUserId);
            item.setItemNo(SnowFlake.nextId()+"");
            item.setMasterNo(om.getOrderNo());
            item.setStoreId(goodsVo.getStoreId());
            item.setCreatedBy(currentUserId);
            item.setUpdatedBy(currentUserId);
            if(StringUtils.isNotEmpty(goodsVo.getRemarks())) item.setRemarks(goodsVo.getRemarks());
            for (Clist clist : goodsVo.getCom()) {
                OrderCommodity oc = new OrderCommodity();
                BigDecimal discountAmount = new BigDecimal(0); //记录该商品使用的优惠券总金额
                oc.setItemNo(item.getItemNo());
                //通过SKUID获取商品信息
                FreightDTO f = infoMap.get(Long.valueOf(clist.getSkuId()));
                if(f.getSource() == GoodsSource.JD){ //如果来源是京东的
                    if(clist.getNum() != 1) throw new CustomException(ExecptionEnum.ADD_CART_GOODS_MAX);
                }
                oc.setGoodsId(f.getProId());
                oc.setSkuId(f.getSkuId());
                oc.setSkuRemarks(f.getSkuDesc());
                oc.setGoodsName(f.getProName());
                oc.setPrice(f.getPrice());
                oc.setBuyCount(clist.getNum());
                oc.setImg(f.getImgUrl());
                oc.setFreight(f.getPostage());
                oc.setCoupons(StringUtils.join(clist.getCoupons(),","));
                oc.setCostPrice(f.getCostPrice());
                oc.setCreatedBy(currentUserId);
                oc.setUpdatedBy(currentUserId);
                //获取用户选择的优惠券ID
                Long[] coupons = clist.getCoupons();
                if(coupons.length > 0){ //如果有则计算优惠金额
                    for (int i = 0; i < coupons.length; i++) {
                        if(null == coupons[i]) break;
                        CouponList coupon = couponList.get(coupons[i].toString());
                        if( null == coupon) break;
                        couIds.add(coupons[i]); //记录使用的券ID 用于锁券
                        //叠加该商品的所有优惠金额
                        discountAmount = discountAmount.add(coupon.getValueAmount());
                    }
                }
                //获取该款商品最大可优惠的金额 （商品原价-商品保底金额）* 数量
                BigDecimal maxDis = (f.getPrice().subtract(f.getMinSellPrice())).multiply(BigDecimal.valueOf(oc.getBuyCount()));
                //如果优惠金额 大于 最大可优惠的金额
                if(discountAmount.compareTo(maxDis) == 1){
                    log.info("[订单服务]==========>生成订单时发现优惠券溢出。商品SKUID为：{}，单价为：{}，保底金额为：{}，购买数量：{}，总优惠金额为：{}，最大可优惠的金额为：{}，溢出的金额为：{}", f.getSkuId(),f.getPrice(),f.getMinSellPrice(),oc.getBuyCount(),discountAmount,maxDis,discountAmount.subtract(maxDis));
                    item.setTotalDiscount(item.getTotalDiscount().add(maxDis));  //叠加最大可优惠的金额
                }else{
                    item.setTotalDiscount(item.getTotalDiscount().add(discountAmount));  //叠加优惠金额
                }
                item.setOrderSource(f.getSource());  //订单来源
                item.setStoreName(f.getStoreName()); //商家名称
                item.setTotalCount(item.getTotalCount()+clist.getNum()); // 购买数量
                item.setTotalAmount(item.getTotalAmount().add(f.getPrice().multiply(BigDecimal.valueOf(oc.getBuyCount())))); // 商品总额
                item.setTotalFreight(item.getTotalFreight().add(f.getPostage()));  // 总运费
                item.setCostAmount(item.getCostAmount().add(f.getCostPrice().multiply(BigDecimal.valueOf(oc.getBuyCount()))));//叠加 成本金额
                goodsMap.put(oc.getSkuId(),oc.getBuyCount());
                if(!iOrderCommodityService.save(oc))
                    throw new CustomException(ExecptionEnum.CREATE_ORDER_SAVE_COMMODITY_FAIL);
            }
            // ==========计算应付金额
            //应付金额 = （商品总额-优惠券总金额） + 总运费
            item.setAmount((item.getTotalAmount().subtract(item.getTotalDiscount())).add(item.getTotalFreight()));
            if(orderItemMapper.insert(item) != 1)   //保存子订单
                throw new CustomException(ExecptionEnum.CREATE_ORDER_SAVE_ITEM_FAIL);
            itemNos.add(item.getItemNo()); //保存订单编号，待入队处理
            money = money.add(item.getAmount()); //记录该订单的总金额
        }
        //验证是否为套餐
        iOrderCartService.checkPackage(goodsMap,money,currentUserId);
        //添加收货地址
        if(!iOrderConsigneeService.add(currentUserId,itemNos,vo.getAddressId()))
            throw new CustomException(ExecptionEnum.CREATE_ORDER_SAVE_CONSIGNEE);
        //删除购物车
        if(!CollectionUtils.isEmpty(vo.getCartIds())){ //如果购物车集合不为空
            iOrderCartService.delCommoditys(vo.getCartIds(),currentUserId);
        }
        // 支付倒计时开始  入延迟队列处理
        itemNos.forEach( no -> {
            delayProducer.update(currentUserId,no);
        });
        //判断是否需要发票
        if(StringUtils.isNotEmpty(vo.getInvoiceId())){
            iInvoiceInfoService.insert(itemNos,vo.getInvoiceId(),currentUserId);
        }
        //减库存、券
        this.reduceStocks(currentUserId,goodsMap,couIds);
        //判断是否为0元商品 如果是则直接调用支付回调接口
        this.checkFree(itemNos);
        redis.clear(Redis.KEY_ORDER+currentUserId);
        log.info("[订单服务]==========>生成订单成功！用户ID为：{}，订单编号为：{}",currentUserId,om.getOrderNo());
        return om.getOrderNo();
    }


    /**
     * 校验是否为0元购商品 如果是则直接调用支付回调
     * @param itemNos
     */
    public void checkFree(List<String> itemNos){
        itemNos.forEach( no -> {
            OrderItem item = this.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, no));
            if(item.getAmount().compareTo(new BigDecimal(0)) == 0){ //如果子订单应付金额为0元商品
                log.info("[下单操作]=========>0元购商品，将直接走支付回调流程！订单号为：{}",no);
                payCallbackService.paySuccess(no,"0",item.getAmount(),true,1);
            }
        });
    }


    /**
     * 通过SKUID、数量 获取商品信息集合
     * @param goodsVos
     * @return  Map<SKUID,FreightDTO>
     */
    public Map<Long,FreightDTO> getList(AddOrderDTO vo){
        if(vo.getGoodsVos().size() < 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        ResponseInfo<ShippingAddressInfoDTO> res = customerManageFeign.getAddress(vo.getAddressId());
        if (!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        ShippingAddressInfoDTO address = res.getData();
        List<GoodsDTO> goodsVos = vo.getGoodsVos();
        // Map<sku_ID, 购买的数量>
        Map<Long, Integer> goods = new HashMap<>();
        goodsVos.forEach( g -> {
            if(null == g) return;
            g.getCom().forEach( c -> {
                if(null == c) return;
                goods.put(Long.valueOf(c.getSkuId()),c.getNum());
            });
        });
        Map<Long,FreightDTO> map = new HashMap<>();
        findFreightDTO findFreightDTO = new findFreightDTO();
        findFreightDTO.setProvince(address.getAreaInfo().getProvince().getAreaCode());
        findFreightDTO.setCity(address.getAreaInfo().getCity().getAreaCode());
        String addres = address.getAreaInfo().getProvince().getAreaLongName()+"-"+address.getAreaInfo().getCity().getAreaLongName()+"-"+address.getAreaInfo().getRegion().getAreaLongName()+"-";
        if( address.getAreaInfo().getTown() != null){
            addres = addres + address.getAreaInfo().getTown().getAreaLongName();
        }
        findFreightDTO.setAddres(addres);
        findFreightDTO.setMap(goods);
        ResponseInfo<List<FreightDTO>> cres = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
        if(!cres.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,cres.getCode(),cres.getMessage());
        cres.getData().forEach( f -> {
            map.put(f.getSkuId(),f);// Map<SKUID, FreightDTO>
        });
        return map;
    }

    /**
     * 获取优惠券信息
     * @param vo
     * @return  Map<优惠券ID, 优惠券对象>
     */
    public Map<String, CouponList> getCouponList(String currentUserId){
        Map<String, CouponList> res = new HashMap<>();
        //获取我的券
        ResponseInfo<List<CouponList>> vres = volumeMarketFeign.queryListByOrder(currentUserId);
        if(!vres.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,vres.getCode(),vres.getMessage());
        //如果没有券，返回支付方式
        if(CollectionUtils.isEmpty(vres.getData()))
            return res;
        //把我的卷 和 支持的类型 进行匹配
        vres.getData().forEach( c -> {
            if(null != c) res.put(c.getId(),c);
        });
        return res;
    }

    /**
     * 确认发货
     * @param currentUserId
     * @param orderNo           订单编号
     * @param shippingCompName  快递公司名称
     * @param shippingSn        快递单号
     * @return
     */
    @Override
    public void confirmDelivery(String currentUserId, String orderNo,String shippingCompName,String shippingSn) {
        OrderItem item = this.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, orderNo));
        if (item == null)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        item.setStatus(IStatus.UNSIGEND);
        item.setShippingCompName(shippingCompName);                     //设置快递公司名称
        item.setShippingSn(shippingSn);                                 //设置快递单号
        item.setDeliveryTime(new Timestamp(System.currentTimeMillis())); //设置发货时间
        if (!this.update(item,new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo,orderNo)))
            throw new CustomException(ExecptionEnum.CHANGE_ORDER_STATUS_FAIL);
        redis.clear( Redis.KEY_ORDER+item.getUserId());
        //该笔订单入自动确认收货处理
        delayProducer.confirm(item.getUserId(),orderNo);
        //发送消息 
        // TODO: 2019/8/23  orderNo需要修改为商品名称  但是商品名称可能有多
        sendMessage.merchantDelivery(item.getUserId(),orderNo);
    }

    /**
     * 根据用户编号获取购买套餐的数量
     *
     * @param list 用户编号集合
     * @return
     */
    @Override
    public List<PageCountDTO> getUserPageCount(List<String> list) {
        List<PageCountDTO> arr = new ArrayList<>();
        for (String no : list) {
            arr.add(this.getUserPageCount(no));
        }
        return arr;
    }

    /**
     * 根据用户编号获取购买套餐的数量
     *
     * @param userNo 用户编号
     * @return
     */
    @Override
    public PageCountDTO getUserPageCount(String userNo) {
        String count = orderItemMapper.getUserPageCount(userNo);
        return new PageCountDTO(userNo,StringUtils.isEmpty(count) ? "0":count);
    }

    /**
     * 后台供应商 订单列表的查询
     *
     * @param vo
     * @return
     */
    @Override
    public Page<BackOrderDTO> findOrderItemByChainId(SelectOrderDTO vo, Integer page, Integer pageSize){
        return orderItemMapper.findOrderItemByChainId(new Page(page,pageSize),vo);
    }

    /**
     * 用户确认收货
     *
     * @param currentUserId
     * @param itemNo 订单号
     * @return
     */
    @Override
    @Transactional
    public boolean confirmGoods(String currentUserId,String itemNo) {
        OrderItem item = orderItemMapper.selectOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, itemNo));
        if(item == null)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        item.setStatus(IStatus.NOT_COMMENT); //修改订单状态为 待评价
//        item.setPayStatus(MStatus.DEAL_CLOSURE); //修改订单支付状态为 支付完成
        item.setSettlement(Common.SETTLEMENT_YES); //修改结算状态为  可结算
        if(orderItemMapper.updateById(item) != 1) return false;
        redis.clear(Redis.KEY_ORDER+currentUserId);
        return true;
    }


    /**
     * 减库存
     *      通知券集市 锁券
     *      调用商品服务 减商品库存
     * @param currentUserId
     * @param goodsMap      Map<SKUID, 购买的数量>
     * @param couMap        使用的优惠券ID集合
     * @return 是否成功
     */
    public void reduceStocks(String currentUserId,Map<Long,Integer> goodsMap,List<Long> couIds){
        //调用商品服务 减商品库存
        if(!productManageFeign.subtractStock(goodsMap)){
            log.info("[订单服务]=========>调用商品服务 减商品库存失败！SKUID为：{}",goodsMap);
            throw new CustomException(ExecptionEnum.ERROR_INSUFFICIENT_STOCK);
        }
        log.info("[订单服务]==========>生成订单 -> 调用商品服务减库存成功！");
        //调用券集市，锁券
        if(!CollectionUtils.isEmpty(couIds)) {
            String cous = StringUtils.join(couIds, ";");
            ResponseInfo<String> res = volumeMarketFeign.updateCoupon(cous, currentUserId, CouponStatus.LOCK);
            if(!res.isSuccess()){
                log.error("[订单服务]==========>生成订单 -> 调用券集市锁券失败！券ID为：{}",cous);
                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
            }
            log.info("[订单服务]==========>生成订单 -> 调用券集市锁券成功！券ID为：{}",cous);
        }
    }

    /**
     * 根据用户ID 和 订单状态 获取 子订单列表
     *
     * @param currentUserId 用户ID
     * @param status        （ 全部 = -1，待付款 = 0，待发货 = 1，待收货 = 2，待点评 = 4 ）
     * @return
     */
    @Override
    public IPage<OrderItem> getAllOrderInfoByUserId(String currentUserId, OrderDTO vo) {
        String key = Redis.KEY_ORDER+currentUserId;
        String item = vo.getStatus()+"_"+vo.getPage()+"_"+vo.getPageSize();
        if(redis.hexists(key,item)){
            //返回之前更新时间值
            IPage<OrderItem> pages = (IPage<OrderItem>) redis.getHashCache(key,item);
            pages.getRecords().forEach( i -> {
                this.setOtherAttr(i);
            });
            return pages;
        }
        LambdaQueryWrapper<OrderItem> queryWrapper = null;
        if(vo.getStatus() != IStatus.ALL){
            queryWrapper =  new LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getUserId, currentUserId)
                    .eq(OrderItem::getStatus, vo.getStatus())
                    .eq(OrderItem::getEnable, Common.ENABLE)
                    .orderByDesc(OrderItem::getCreatedDate);
        }else{
            queryWrapper =  new LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getUserId, currentUserId)
                    .eq(OrderItem::getEnable, Common.ENABLE)
                    .ne(OrderItem::getStatus,IStatus.UNPAID)
                    .ne(OrderItem::getStatus,IStatus.QANCEL)
                    .orderByDesc(OrderItem::getCreatedDate);
        }
        IPage<OrderItem> pages = super.page(new Page<>(vo.getPage(), vo.getPageSize()), queryWrapper);
        pages.getRecords().forEach(i -> {
            i.setList(iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                    .eq(OrderCommodity::getItemNo, i.getItemNo())
                    .eq(OrderCommodity::getEnable, Common.ENABLE)));
            //设置其他属性
            this.setOtherAttr(i);
        });

        if(!redis.setHashCache(key,item,pages,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>订单列表放入缓存失败！");
        }
        return pages;
    }

    /**
     * 设置其他属性
     *       下单时间
     *       订单状态值
     *       剩余时间
     * @param item 子订单对象
     */
    public void setOtherAttr(OrderItem item){
        //设置格式化后的下单时间
        item.setCreateTime(TimeUtils.getStr2(item.getCreatedDate()));
        //设置该订单对应的状态值
        item.setStatusVal(IStatus.STATUS[item.getStatus()]);
        //设置剩余时间
        String time = "";
        switch (item.getStatus()){
            case IStatus.UNPAID:   //未付款
                time = TimeUtils.getStrTime(TimeUtils.ORDER_UNPAID,item.getCreatedDate().getTime());
                time = time.substring(2);  //去除天显示
                break;
            case IStatus.UNSIGEND: //自动确认收货
                time = TimeUtils.getStrTime(TimeUtils.ORDER_AUTO_COMFIRM_GOODS,item.getDeliveryTime().getTime());
                break;
        }
        item.setLastTime(time);
    }

    /**
     * 根据子订单编号获取 该订单的所有详情
     *
     * @param currentUserId
     * @param orderNo
     * @return
     */
    @Override
    public OrderDetailsDTO getOrderDetailByItemId(String currentUserId, OrderDTO vo) {
        String redisKey = Redis.KEY_ORDER+currentUserId;
        String redisItem = vo.getOrderNo();
        if(redis.hexists(redisKey,redisItem)){
            OrderDetailsDTO orderDetailsDTO =(OrderDetailsDTO) redis.getHashCache(redisKey,redisItem);
            //设置过期时间
            this.setOtherAttr(orderDetailsDTO.getOrderItem());
            return orderDetailsDTO;
        }
        //获取子订单对象
        OrderItem item = super.getOne(new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getItemNo,vo.getOrderNo()).eq(OrderItem::getEnable, Common.ENABLE),true);
        if(null == item)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        //获取主订单对象
        OrderMaster master = iOrderMasterService.getOne(new LambdaQueryWrapper<OrderMaster>()
                .eq(OrderMaster::getOrderNo, item.getMasterNo()).eq(OrderMaster::getEnable,Common.ENABLE), true);
        //填充商品信息
        item.setList(iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                .eq(OrderCommodity::getItemNo, item.getItemNo())
                .eq(OrderCommodity::getEnable, Common.ENABLE)));
        //根据主订单编号获取 收货信息
        OrderConsignee one = iOrderConsigneeService.getOne(new LambdaQueryWrapper<OrderConsignee>()
                .eq(OrderConsignee::getOrderNo,item.getItemNo()));
        //填充DTO对象 并返回
        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setOrderItem(item);
        orderDetailsDTO.setOrderMaster(master);
        orderDetailsDTO.setOrderConsignee(one);
        //如果订单状态为待支付 获取支付方式
        if(item.getStatus() == IStatus.UNPAID){
            //获取商品ID 数组
            Long[] cids = item.getList()
                    .stream()
                    .map(OrderCommodity::getGoodsId)
                    .toArray(Long[]::new);
            //调用商品服务，获取支持的支付方式
            Set<String> payTypes = iOrderCartService.getPayType(cids);
            List<PayType> ps = new ArrayList<>();
            payTypes.forEach( val -> { //遍历支付方式，转换为对象集合
                PayType pt = iPayTypeService.getOne(new LambdaQueryWrapper<PayType>()
                        .eq(PayType::getTypeValue, val)
                        .eq(PayType::getEnable, Common.ENABLE));
                if(null == pt) return;
                ps.add(pt);
            });
            orderDetailsDTO.setList(ps);
        }
        // 设置过期时间
        this.setOtherAttr(orderDetailsDTO.getOrderItem());
        if(!redis.setHashCache(redisKey,redisItem,orderDetailsDTO,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>订单详情放入缓存失败！");
        }
        return orderDetailsDTO;
    }

    /**
     * 根据订单编号删除订单
     * @param currentUserId 用户ID
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public boolean delOrder(String currentUserId,String orderNo) {
        if(orderItemMapper.delOrder(orderNo) != 1) return false;
        redis.clear(Redis.KEY_ORDER+currentUserId);
        return true;
    }


    /**
     * 根据子订单编号或者商品名称 搜索订单
     * @param currentUserId 用户ID
     * @param key           搜索的关键字
     * @return
     */
    @Override
    public PageInfo<OrderItem> getOrderInfoByOrderNo(String currentUserId,OrderDTO vo) {
        String rediskey = Redis.KEY_ORDER+currentUserId;
        String redisitem = vo.getKey()+"_"+vo.getPage()+"_"+vo.getPageSize();
        if(redis.hexists(rediskey,redisitem)){
            return (PageInfo<OrderItem>) redis.getHashCache(rediskey,redisitem);
        }
        //获取搜索出来的订单ID
        List<OrderItem> items = new ArrayList<>();
        //分页
        PageHelper.startPage(vo.getPage(),vo.getPageSize());
        List<OrderItem> itemNos = orderItemMapper.getItemNos(currentUserId, vo.getKey());
        itemNos.forEach( itemNo -> {
            String no = itemNo.getItemNo();
            OrderItem item = super.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo,no));
            if(null == item) return;
            item.setList(iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                    .eq(OrderCommodity::getItemNo, item.getItemNo())
                    .eq(OrderCommodity::getEnable, Common.ENABLE)));
            items.add(item);
        });
        PageInfo<OrderItem> page=new PageInfo<OrderItem>(items);
        if(page != null && !redis.setHashCache(rediskey,redisitem,page,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>搜索订单结果放入缓存失败！");
        }
        return page;
    }

    /**
     * 修改订单状态
     *
     * @param currentUserId
     * @param orderNo       子订单编号
     * @param status        订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9）默认为0
     * @return
     */
    @Override
    public boolean updateStatus(String currentUserId, String orderNo, Integer status) {
        if(orderItemMapper.updateStatus(orderNo,status) != 1) return false;
        redis.clear(Redis.KEY_ORDER+currentUserId);
        return true;
    }

    /**
     * 根据订单编号获取订单状态
     *
     * @param orderNo
     * @return
     */
    @Override
    public int getStatus(String orderNo) {
        return orderItemMapper.getStatus(orderNo);
    }




}
