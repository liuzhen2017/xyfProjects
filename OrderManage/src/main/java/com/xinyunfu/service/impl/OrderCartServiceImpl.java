package com.xinyunfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.*;
import com.xinyunfu.constant.Package;
import com.xinyunfu.dto.*;
import com.xinyunfu.dto.customer.UserInfoDTO;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderCartMapper;
import com.xinyunfu.model.OrderCart;
import com.xinyunfu.model.PayType;
import com.xinyunfu.service.IOrderCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.service.IPayTypeService;
import com.xinyunfu.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@Service
public class OrderCartServiceImpl extends ServiceImpl<OrderCartMapper, OrderCart> implements IOrderCartService {

    @Autowired
    private RedisCommonUtil redis;

    @Autowired
    private OrderCartMapper orderCartMapper;

    @Autowired
    private ProductManageFeign productManageFeign;

    @Autowired
    private VolumeMarketFeign volumeMarketFeign;

    @Autowired
    private IPayTypeService iPayTypeService;

    @Autowired
    private CustomerManageFeign customerManageFeign;

    /**
     * 加入购物车
     *
     * @param currentUserId 用户ID
     * @return
     */
    @Override
    @Transactional
    public void addCart(String currentUserId,JSONObject object) {
        //购买数量
        Integer count = object.getInteger("buyCount");
        Long skuId = object.getLong("skuId");
        if(null == skuId || null == count)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        //调用商品服务获取商品所有信息
        findFreightDTO findFreightDTO = new findFreightDTO();
        Map<Long,Integer> map = new HashMap<>();
        map.put(skuId,count);
        findFreightDTO.setMap(map);
        ResponseInfo<List<FreightDTO>> res = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        //先验证是否在购物车存在
        OrderCart orderCart = orderCartMapper.selectOne(new LambdaQueryWrapper<OrderCart>()
                .eq(OrderCart::getUserId,currentUserId)
                .eq(OrderCart::getEnable, Common.ENABLE)
                .eq(OrderCart::getSkuId, skuId));
        if(null != orderCart){ //如果存在 叠加数量
            ///判断商品来源，如果来源是京东的则
            if(res.getData().get(0).getSource() == GoodsSource.JD)
                throw new CustomException(ExecptionEnum.BUY_COUNT_OUT_MAX);
            orderCart.setBuyCount(orderCart.getBuyCount()+count);
            if(orderCartMapper.updateById(orderCart) != 1)
                throw new CustomException(ExecptionEnum.ERROR_ADD_CART_FAIL);
        }else{ //不存在，添加
            //调用商品服务获取商品类型
            ResponseInfo<Map<Long, Integer>> proTypeBySkuId = productManageFeign.findProTypeBySkuId(String.valueOf(skuId));
            if(!proTypeBySkuId.isSuccess())
                throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,proTypeBySkuId.getCode(),proTypeBySkuId.getMessage());
            Map<Long, Integer> data = proTypeBySkuId.getData();
            OrderCart cart = new OrderCart();
            for (FreightDTO f : res.getData()) {
                //判断商品来源，如果来源是京东的则 限制数量为1
                if(f.getSource() == GoodsSource.JD){
                    if(count != 1) throw new CustomException(ExecptionEnum.ADD_CART_GOODS_MAX);
                }
                cart.setSource(f.getSource());
                cart.setGoodsId(f.getProId());
                cart.setGoodsName(f.getProName());
                cart.setGoodsPrice(f.getPrice());
                cart.setSkuDescription(f.getSkuDesc());
                cart.setSkuId(f.getSkuId());
                cart.setImg(f.getImgUrl());
                cart.setStoreId(f.getStoreId());
                cart.setStoreName(f.getStoreName());
                cart.setBuyCount(count);
                cart.setGoodsType(data.get(skuId)); //设置商品类型
            }
            cart.setUserId(currentUserId);
            cart.setCreatedBy(currentUserId);
            cart.setUpdatedBy(currentUserId);
            if(!super.save(cart))
                throw new CustomException(ExecptionEnum.ERROR_ADD_CART_FAIL);
        }
       redis.clear(Redis.KEY_ORDERCART+currentUserId);
    }

    /**
     * 获取我的购物车
     *
     * @param currentUserId 用户ID
     * @return
     */
    @Override
    public Object getMyCart(String currentUserId) {
        String key = Redis.KEY_ORDERCART+currentUserId; //键
        if(redis.exists(key))
            return (Map<String, List<OrderCartDTO>>) redis.getCache(Redis.KEY_ORDERCART+currentUserId);
        List<OrderCartDTO> ocDTO = new ArrayList<>();
        //获取商家的信息
        orderCartMapper.selectList(new LambdaQueryWrapper<OrderCart>()
                .eq(OrderCart::getUserId, currentUserId)
                .eq(OrderCart::getEnable,Common.ENABLE)
                .groupBy(OrderCart::getStoreId).orderByDesc(OrderCart::getCreatedDate)).forEach( cart -> {
                    //设置商家的信息
            OrderCartDTO o = new OrderCartDTO();
            o.setId(cart.getStoreId());
            o.setShopname(cart.getStoreName());
            //设置该商家的商品信息
            List<SonListDTO> sons = new ArrayList<>();
            //根据商家ID 和用户ID获取 商品信息
            orderCartMapper.selectList(new LambdaQueryWrapper<OrderCart>()
                    .eq(OrderCart::getUserId, currentUserId)
                    .eq(OrderCart::getEnable,Common.ENABLE)
                    .eq(OrderCart::getStoreId, o.getId())).forEach( c -> {
                SonListDTO son = new SonListDTO();
                son.setCartId(c.getCartId());
                son.setId(c.getGoodsId());
                son.setSkuId(c.getSkuId());
                son.setStrt(c.getSkuDescription());
                son.setName(c.getGoodsName());
                son.setMoney(c.getGoodsPrice());
                son.setNum(c.getBuyCount());
                son.setImg(c.getImg());
                son.setGoodsType(c.getGoodsType());
                son.setSource(c.getSource());  //设置商品来源
                sons.add(son);
            });
            o.setSonList(sons);
            ocDTO.add(o);
        });
        Map<String, List<OrderCartDTO>> map = new HashMap<>();
        map.put("cartList",ocDTO);
        //放入缓存
       if(!CollectionUtils.isEmpty(ocDTO) && !redis.setCache(Redis.KEY_ORDERCART+currentUserId,map,Redis.EXC_REDIS)){
           log.warn("[订单服务]==========>购物车放入缓存失败！");
       }
        return map;
    }

    /**
     * 获取确认订单信息
     *
     * @param currentUserId
     * @param object
     * @return
     */
    @Override
    public ConfirmOrderDTO getConfirmOrderInfo(String currentUserId, JSONObject object) {
        BigDecimal money = new BigDecimal(0); //应付金额
        AtomicInteger totalNumber = new AtomicInteger(); //总件数
        List<ConfirmOrderInfoDTO> ids = object.getJSONArray("ids").toJavaList(ConfirmOrderInfoDTO.class);  //获取 SKUID 数量 集合
        String provNo = object.getString("provNo"); //获取省份编号
        String cityNo = object.getString("cityNo"); //获取市编号
        String addres = object.getString("addres"); //省市区名字 使用-拼接
        if(ids.size() < 0)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        //获取优惠金额 <SKUID,该SKUID商品选中的券的金额>
        Map<Long, BigDecimal> cMap = this.getDiscountAmountList(ids, currentUserId);
        ConfirmOrderDTO vo = new ConfirmOrderDTO();
        List<SubStoreDTO> stores = new ArrayList<>();
        //获取商品信息list
        List<FreightDTO> proList = this.getProductInfo(ids,provNo,cityNo,addres);
        //获取商品数组
        Long[] cids = proList.stream()
                .map(p -> p.getProId())
                .distinct()
                .toArray(Long[]::new);
        //获取支付方式
        Set<String> payTypes = this.getPayType(cids);
        // Map<商品ID,券对象集合>
        List<PayType> ps = new ArrayList<>();
        payTypes.forEach( val -> { //遍历支付方式，转换为对象集合
            PayType pt = iPayTypeService.getOne(new LambdaQueryWrapper<PayType>()
                    .eq(PayType::getTypeValue, val)
                    .eq(PayType::getEnable, Common.ENABLE));
            if(null == pt) return;
            ps.add(pt);
        });
        vo.setList(ps);
        //获取商家ID集合
        List<Long> stroeIds = proList.stream()
                .map(p -> p.getStoreId())
                .distinct()
                .collect(Collectors.toList());
        Map<Long,Integer> skuMap = new HashMap<>(); //<SKU_ID,数量>
        Map<Long,Long> cartMap = new HashMap<>();  //<SKU_ID,购物车ID>
        Map<Long,Integer> couponsMap = new HashMap<>(); //<SKUID,使用券的数量>
        ids.forEach( c -> {
            totalNumber.addAndGet(c.getNum());
            skuMap.put(c.getSkuId(),c.getNum());
            cartMap.put(c.getSkuId(),c.getCartId());
            couponsMap.put(c.getSkuId(), CollectionUtils.isEmpty(c.getCoupons()) ? 0:c.getCoupons().size());
        });
        //遍历商家ID 获取 商家DTO
        for (Long sid : stroeIds) {
            //生成商家的DTO
            SubStoreDTO store = new SubStoreDTO();
            store.setStoreId(sid);
            for (FreightDTO p : proList) {
                if(p.getStoreId() == sid){ //如果商家ID 一致
                    store.setStoreName(p.getStoreName());
                    SonListDTO son = new SonListDTO();
                    son.setId(p.getProId());
                    son.setName(p.getProName());
                    son.setImg(p.getImgUrl());
                    son.setSkuId(p.getSkuId());
                    son.setStrt(p.getSkuDesc());
                    son.setMoney(p.getPrice());
                    son.setCouponsNumber(couponsMap.get(p.getSkuId())); //记录使用券的数量
                    son.setCartId(cartMap.get(p.getSkuId())); //获取购物车ID
                    Integer num = skuMap.get(p.getSkuId());   //获取购买的数量
                    if(p.getStock() < num){
                        log.info("[订单服务]=========>获取确认订单信息时,效验库存不足！SKUID为：{}",p.getSkuId());
                        throw new CustomException(ExecptionEnum.ERROR_INSUFFICIENT_STOCK);
                    }
                    son.setNum(num);
                    son.setMinAmount(p.getMinSellPrice());
                    store.setAmount(store.getAmount().add(p.getPrice().multiply(BigDecimal.valueOf(num)))); //叠加该商家下的商品总额
                    store.setFreight(store.getFreight().add(p.getPostage())); //叠加该商家下的商品总运费
                    store.getList().add(son);
                    //获取该款商品使用的优惠金额
                    BigDecimal dis = cMap.get(p.getSkuId());
                    //获取该款商品最大可优惠的金额 （商品原价-商品保底金额）* 数量
                    BigDecimal maxDis = (p.getPrice().subtract(p.getMinSellPrice())).multiply(BigDecimal.valueOf(num));
                    //如果优惠金额 大于 最大可优惠的金额
                    if(dis.compareTo(maxDis) == 1){
                        log.info("[订单服务]==========>获取确认订单信息时发现优惠券溢出。商品SKUID为：{}，单价为：{}，保底金额为：{}，购买数量：{}，总优惠金额为：{}，最大可优惠的金额为：{}，溢出的金额为：{}",p.getSkuId(),p.getPrice(),p.getMinSellPrice(),num,dis,maxDis,dis.subtract(maxDis));
                        //叠加最大可优惠的金额
                        vo.setDiscountAmount(vo.getDiscountAmount().add(maxDis));
                    }else{
                        //叠加总优惠金额
                        vo.setDiscountAmount(vo.getDiscountAmount().add(dis));
                    }
                }
            }
            stores.add(store);
            //叠加商品总金额、邮费
            vo.setTotalAmount(vo.getTotalAmount().add(store.getAmount()));
            vo.setTotalFreight(vo.getTotalFreight().add(store.getFreight()));
        }
        //校验是否为套餐 并且同时设置
        vo.setPage(this.checkPackage(skuMap,vo.getTotalAmount(), currentUserId));
        //应付金额 = 商品总额 - 优惠金额 + 运费
        vo.setMoney((vo.getTotalAmount().subtract(vo.getDiscountAmount())).add(vo.getTotalFreight()));
        vo.setTotalNumber(totalNumber.get());
        vo.setStores(stores);
        vo.setFree(vo.getMoney().compareTo(new BigDecimal(0)) == 0); //判断是否为0元商品
        return vo;
    }

    /**
     * 修改购物车
     *
     * @param currentUserId
     * @param cartDTOS
     * @return
     */
    @Override
    @Transactional
    public boolean modifyCart(String currentUserId, CartDTO c) {
        if( null == c.getCartId() || c.getCartId() < 1){
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        }
        OrderCart orderCart = new OrderCart();
        orderCart.setCartId(c.getCartId());
        //设置购买数量
        if( c.getNumber() > 0){
            orderCart.setBuyCount(c.getNumber());
        }
        //设置SKUID
        if(c.getSkuId() > 0){
            orderCart.setSkuId(c.getSkuId());
            if(StringUtils.isEmpty(c.getSkuDesc()))
                throw new CustomException(ExecptionEnum.ERROR_UPDATE_CART_FAIL);
            orderCart.setSkuDescription(c.getSkuDesc());
        }
        orderCart.setUpdatedBy(currentUserId);
        //修改
        if (orderCartMapper.updateById(orderCart) != 1)
            throw new CustomException(ExecptionEnum.ERROR_UPDATE_CART_FAIL);
        redis.clear(Redis.KEY_ORDERCART+currentUserId);
        return true;
    }

    /**
     * 删除购物车 修改数据状态为禁用
     *
     * @param ids           购物车ID集合
     * @param currentUserId 当前用的ID
     * @return
     */
    @Override
    @Transactional
    public void delCommoditys(List<Long> ids, String currentUserId) {
        //购物车ID集合 转换为对象集合
        for (Long id : ids) {
            OrderCart cart = orderCartMapper.selectById(id);
            if(cart != null){
                cart.setEnable(Common.DISABLE); //设为禁用
                cart.setUpdatedBy(currentUserId);
                if (orderCartMapper.updateById(cart) != 1)
                    throw new CustomException(ExecptionEnum.ERROR_DELETE_CART_FAIL);
            }
        }
        redis.clear(Redis.KEY_ORDERCART+currentUserId);
    }

    /**
     * 根据商品ID 数组 获取 支持的支付方式
     * @param ids 商品ID数组
     * @return
     */
    @Override
    public Set<String> getPayType(Long[] ids){
        //调用商品服务获取 商品的支付方式
        ResponseInfo<Map<Long, List<String>>> res = productManageFeign.queryPayTypeAndCouponType(StringUtils.join(ids, ";"));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        //<"商品id",["支付方式","支持券的类型"]>
        Map<Long, List<String>> resData = res.getData();
        Set<String> payType = new HashSet<>();             //支持的支付方式
        // 交集操作
        resData.forEach( (k,v) -> {
            //获取支持的支付方式
            if (CollectionUtils.isEmpty(payType)) {
                payType.addAll(Arrays.asList(v.get(0).split(",")));
            } else {
                payType.retainAll(Arrays.asList(v.get(0).split(",")));
            }
        });
        return payType;
    }

    /**
     * 通过购物车ID 获取商品数组
     * @param ids
     * @return
     */
    @Override
    public Long[] getCids(List<Long> ids){
        Long[] cids = new Long[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            cids[i] = super.getById(ids.get(i)).getGoodsId();
        }
        return cids;
    }


    /**
     * 调用用商品服务 查询商品信息list
     *
     * @param list
     * @param pNo 省编号
     * @param cNo 市编号
     * @return List<FreightDTO>
     */
    @Override
    public List<FreightDTO> getProductInfo(List<ConfirmOrderInfoDTO> ids,String pNo,String cNo,String addres){
        // Map<sku_ID, 购买的数量>
        Map<Long, Integer> goods = new HashMap<>();
        ids.forEach( c -> {
            goods.put(c.getSkuId(),c.getNum());
        });
        //调用商品服务获取 对应信息
        findFreightDTO findFreightDTO = new findFreightDTO();
        findFreightDTO.setMap(goods);
        findFreightDTO.setProvince(pNo);
        findFreightDTO.setCity(cNo);
        findFreightDTO.setAddres(addres);
        ResponseInfo<List<FreightDTO>> res = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        return res.getData();
    }


    /**
     *  判断是否为套餐 的SKUID
     *      如果是则
     *          获取用户类型
     *          调用订单树验证是否可以购买
     *          如果为普通用户 限制购买的数量为 1
     *          如果是VIP用户，限制购买的金额为 1W
     * @param skuMap Map<SKUID,购买的数量>
     * @param money  金额
     * @param currentUserId
     * @return
     */
    @Override
    public boolean checkPackage(Map<Long,Integer> skuMap,BigDecimal money,String currentUserId){
        // 调用商品服务 通过SKUID 获取  商品类型 从而进行判断
        Long[] skuIds = skuMap.keySet().stream().toArray(Long[]::new);      //获取SKU数组
        Integer count = skuMap.values().stream().mapToInt(i -> i).sum();//获取购买商品的数量
        ResponseInfo<Map<Long, Integer>> proTypeBySkuId = productManageFeign.findProTypeBySkuId(StringUtils.join(skuIds, ","));
        if (!proTypeBySkuId.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,proTypeBySkuId.getCode(),proTypeBySkuId.getMessage());
        Integer type = SkuType.GOODS; //SKU类型(默认为普通商品)
        Collection<Integer> values = proTypeBySkuId.getData().values();
        for (Integer value : values) {
            type = value;
        }
       if( type == SkuType.PACKAGE){ //如果是套餐
           //通过用户ID 获取 用户类型
           ResponseInfo<UserInfoDTO> userInfo = customerManageFeign.getUserInfo(Long.valueOf(currentUserId));
           if(!userInfo.isSuccess())
               throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,userInfo.getCode(),userInfo.getMessage());
           Integer level = userInfo.getData().getLevel(); //获取用户的类型
           //判断用户类型
           if(level == UserType.COMMON){ //普通用户 限制数量
                if(count > Package.commonMaxNum) throw new CustomException(ExecptionEnum.COMMON_USER_BUY_COUNT);
           }else if(level == UserType.VIP){     //VIP 限制数量
               if(count > Package.commonMaxNum) throw new CustomException(ExecptionEnum.COMMON_USER_BUY_COUNT);
           }else if(level == UserType.AMBASSADOR){     //大使限制金额
               if(money.compareTo(Package.ambassador) == 1) throw new CustomException(ExecptionEnum.VIP_USER_BUY_MAX_MONEY);
           }
           //调用券集市判断是否可以购买
           ResponseInfo<String> res = volumeMarketFeign.queryIsCanBuyByService(currentUserId);
           if(!res.isSuccess())
               throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,res.getCode(),res.getMessage());
           return true;
       }
       return false;
    }

    /**
     * 获取优惠金额
     * @param list
     * @param currentUserId
     * @return Map<SKUID , 该款商品的优惠金额>
     */
    @Override
    public Map<Long, BigDecimal> getDiscountAmountList(List<ConfirmOrderInfoDTO> list,String currentUserId) {
        //获取我的券
        ResponseInfo<List<CouponList>> vres = volumeMarketFeign.queryListByOrder(currentUserId);
        if(!vres.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,vres.getCode(),vres.getMessage());
        Map<Long, BigDecimal> map = new HashMap<>();
        //Map<券的ID,价值>
        Map<String,BigDecimal> cMap = vres.getData().stream()
                .filter(c -> null != c)
                .filter(c -> c.getCouponStatus().equals(CouponStatus.EFFECTIVE)) //只要优惠券状态为 未使用的
                .collect(toMap(CouponList::getId, CouponList::getValueAmount));
        list.forEach( info -> {
            BigDecimal money = new BigDecimal(0); //叠加优惠金额
            List<String> coupons = info.getCoupons();
            if(!CollectionUtils.isEmpty(coupons)){
                for (String cId : coupons) { //遍历匹配我的卷 通过券ID匹配
                    if(StringUtils.isEmpty(cId)) break;
                    BigDecimal mon = cMap.get(cId);
                    if(null != mon){
                        money = money.add(mon);
                    }
                }
            }
            map.put(info.getSkuId(),money);
        });
        return map;
    }


    /**
     * 获取指定商品支持的券
     * @param id    商品ID
     * @param currentUserId
     * @return
     */
    @Override
    public List<CouponList> getCoupon(Long id,String currentUserId) {
        //调用商品服务获取 商品的支付方式
        ResponseInfo<Map<Long, List<String>>> res = productManageFeign.queryPayTypeAndCouponType(id.toString());
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        //<"商品id",["支付方式","支持券的类型"]>
        Map<Long, List<String>> resData = res.getData();
        //获取我的券
        ResponseInfo<List<CouponList>> vres = volumeMarketFeign.queryListByOrder(currentUserId);
        if(!vres.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,vres.getCode(),vres.getMessage());
        List<CouponList> myCouponList = vres.getData();    //我的券集合
        List<CouponList> arr = new ArrayList<>(); //可使用的券
        //获取该商品支持的券 类型
        resData.forEach( (k,v) -> {
            if(StringUtils.isEmpty(v.get(1))) return;
            //获取商品 可使用的券  把商品支持的券的类型 和 我的券支持的类型 进行匹配，如果一致则保留
            String[] cType = v.get(1).split(",");
            for (int i = 0; i < cType.length; i++) { //遍历该商品支持的券类型
                if(StringUtils.isEmpty(cType[i])) break;
                for (CouponList c : myCouponList) {//遍历我的券
                    if(null != c && c.getCouponStatus().equals(CouponStatus.EFFECTIVE)){ //过滤不可用的券
                        if(c.getCouponType().toString().equals(cType[i])){
                            arr.add(c); //保留支持的券
                        }
                    }
                }
            }
        });
        return arr;
    }
}
