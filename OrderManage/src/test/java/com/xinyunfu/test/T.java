package com.xinyunfu.test;


import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.Runner;
import com.xinyunfu.dto.AddOrderDTO;
import com.xinyunfu.dto.PayTypeCouponDTO;
import com.xinyunfu.dto.SubCartDTO;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.customer.UserInfoDTO;
import com.xinyunfu.dto.docking.*;
import com.xinyunfu.dto.express.ExpReqDto;
import com.xinyunfu.dto.findFreightDTO;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.dto.volume.CouponList;
import com.xinyunfu.feign.*;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderItemMapper;
import com.xinyunfu.mapper.SearchHistoryMapper;
import com.xinyunfu.scheduler.OrderScheduler;
import com.xinyunfu.service.IOrderCartService;
import com.xinyunfu.service.IOrderConsigneeService;
import com.xinyunfu.service.IOrderItemService;
import com.xinyunfu.service.ITimeService;
import com.xinyunfu.service.impl.OrderCartServiceImpl;
import com.xinyunfu.timing.DelayProducer;
import com.xinyunfu.utils.JsonUtils;
import com.xinyunfu.utils.RedisCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author XRZ
 * @date 2019/7/6 0006
 * @Description :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Runner.class )
public class T {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private SearchHistoryMapper searchHistoryMapper;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private DelayProducer delayProducer;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;
    @Autowired
    private ProductManageFeign productManageFeign;
    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private EBankFeign eBankFeign;
    @Autowired
    private IOrderCartService iOrderCartService;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private QuestionBankFeign questionBankFeign;
    @Autowired
    private ProductDocKingFeign productDocKingFeign;
    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private ITimeService iTimeService;
    @Autowired
    private OrderScheduler orderScheduler;



    @Test
    public void test3(){
        orderScheduler.checkOrder();
//        ResponseInfo orderId = productDocKingFeign.getOrder(1);
//        iTimeService.checkTime(1);
//        String s = iOrderItemService.addOrder("1", new AddOrderDTO());
    }


    public static String getStr(String str){
        for (Object o : map.keySet()) {
            str = str.replaceAll(o.toString(),map.get(o).toString());
        }
        return str;
    }

    private static Map map = new HashMap<String,String>(){{
       put("%3C","<");
       put("%3E",">");
       put("%3D","=");
       put("%2F","/");
       put("%3A",":");
       put("%20"," ");
       put("%22","'");
       put("%3B",";");
    }};

    @Test
    public void test1(){
        customerManageFeign.meal(39707243l,123);
//        ResponseInfo<Boolean> booleanResponseInfo = eBankFeign.paySuccess("123456");

//        String str = "%3Cp%3E%3Cimg%20style%3D%22width%3A%20533px%3B%22%20src%3D%22http%3A%2F%2Fimages.xyf823.com%2Fuser%2Favatar%2F45ddc83f91c04250a22cbe10115142a2index.jpg%22%3E%3Cbr%3E%3C%2Fp%3E";
//
//        System.out.println(getStr(str));
    }



    @Test
    public void test(){


//        {
//            "thirdOrder":1203719,
//                "sku":[{"skuId":1095883, "num":1,"bNeedAnnex":true, "bNeedGift":false }],
//            "name":"罗志鹏",
//                "province":19,
//                "city":1607,
//                "county":3638,
//                "town":0,
//                "address":"平安大厦",
//                "zip":"",
//                "phone":"",
//                "mobile":"17097239480",
//                "email":"137073283@qq.com",
//                "remark":"",
//                "ext":""
//        }\





//        ResponseInfo<Integer> res = productDocKingFeign.createSerialNum(" ");
//
//        Integer thirdOrder = res.getData();

//        System.out.println("================================获取序列号");
////        System.out.println(thirdOrder);
//        JDPreOrderParam jd = new JDPreOrderParam();
//
//        JDSkuParam jdSkuParam = new JDSkuParam();
//        jdSkuParam.setSkuId(1095883);
//        jdSkuParam.setNum(1);
//        jdSkuParam.setBNeedAnnex(true);
//        jdSkuParam.setBNeedGift(false);
//
////        jd.setThirdOrder(thirdOrder);
//        jd.setSku(new JDSkuParam[]{jdSkuParam});
//        jd.setName("小白");
//        jd.setProvince(19);
//        jd.setCity(1607);
//        jd.setCounty(3638);
//        jd.setTown(0);
//        jd.setAddress("天上人间");
//        jd.setZip("516133");
//        jd.setPhone("");
//        jd.setMobile(13143098753L);
//        jd.setEmail("397047243@qq.com");
//        jd.setRemark("？");
//        jd.setExt("");
//        System.out.println("================================预下单");
////        ResponseInfo<Object> jdPreOrderDtoResponseInfo = productDocKingFeign.preOrder(jd);
////
//        System.out.println(jdPreOrderDtoResponseInfo.getData());
//
//        System.out.println("================================正式下单");
//        ResponseInfo<String> stringResponseInfo = productDocKingFeign.ensureOrder((Long)jdPreOrderDtoResponseInfo.getData(), "1");
//        System.out.println(stringResponseInfo);

//        System.out.println("================================根据序列号获取京东订单号");
//        ResponseInfo<JDThirdOrderDto> orderId = productDocKingFeign.getOrderId(thirdOrder.toString());
//
//
//        System.out.println("================================根据京东订单号查询订单");
//        ResponseInfo<List<JDOrderDto>> order = productDocKingFeign.getOrder(orderId.getData().getJdOrderId());
//
//        System.out.println(order);

//        productManageFeign.queryProductByProNo("123");

//        ResponseInfo<String> res = volumeMarketFeign.queryIsCanBuyByService("7843094100154056704");

//        volumeMarketFeign.updateCoupon("45;46","397047243","00");


//        volumeMarketFeign.distriCouonByOrderNo("123456","456123","4");



//
//        Map<Long,Integer> map = new HashMap<>();
//        map.put(62l,1);
//
//        System.out.println(JsonUtils.serialize(map));
////
//        findFreightDTO findFreightDTO = new findFreightDTO();
//        findFreightDTO.setProvince("140000");
//        findFreightDTO.setCity("140200");
//        findFreightDTO.setMap(map);
//
//        ResponseInfo<List<FreightDTO>> freightDTO1 = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
//        System.out.println(freightDTO1);






//        Map<String,Object> map1 = new HashMap<>();
//        map1.put("userNo","397047243");
//        map1.put("couponid",3);
//        map1.put("num",5);
//
//        volumeMarketFeign.byCoupon(new JSONObject(map1));






//















//        Boolean aBoolean = productManageFeign.subtractStock(map);


//        volumeMarketFeign.queryListByOrder("7839375286373384192");

//
//        ResponseInfo<Map<Long, List<String>>> mapResponseInfo = productManageFeign.queryPayTypeAndCouponType("1000006;1000007");
//
//        System.out.println(mapResponseInfo);
//        AtomicInteger

//
//        Boolean aBoolean = productManageFeign.subtractStock(JsonUtils.serialize(map));
//        ResponseInfo<Map<Long, Integer>> proTypeBySkuId = productManageFeign.findProTypeBySkuId("183,189");

//        System.out.println(proTypeBySkuId.getData());
//        Long uid = 7839454877329711104l;
//        ResponseInfo<UserInfoDTO> userInfo = customerManageFeign.getUserInfo(7839455775716073472l);
////
//        Map<String,Object> map = new HashMap<>();
//        map.put("userNo",uid);
//        map.put("userType",user.getLevel());
//        ResponseInfo<String> res = orderTreeFeign.allowJoinTree(new JSONObject(map));

//        String data = res.getData();
//
//        System.out.println(data);


//        String data = questionBankFeign.CreateAnswer("123456").getData();

//        System.out.println(data);

//
//        ResponseInfo<String> setMeal = volumeMarketFeign.giveCoupon("123456", "setMeal", "4464564645646");
//        System.out.println(setMeal);

//        Map<String,String> map = new HashMap<>();
//        map.put("userNo","");
//        String s1 = volumeMarketFeign.queryListAll(JsonUtils.serialize(map));




//        SubCartDTO dto = new SubCartDTO();
//        dto.setIds(Arrays.asList(14l,15l));
//        dto.setProvNo("440000");
//        dto.setCityNo("440300");
//        Map<String, FreightDTO> productInfo = iOrderCartService.getProductInfo(dto);
//        System.out.println(productInfo);
//        PayTypeCouponDTO payTypeAndCoupon = iOrderCartService.getPayTypeAndCoupon(new Long[]{1000006l, 1000007l}, "456");
//        System.out.println(payTypeAndCoupon);

//        ResponseInfo<ShippingAddressInfoDTO> address = customerManageFeign.getAddress(1l);



//        Map<String,Integer> map = new HashMap<>();
//        map.put("10019",15);
//        map.put("10024",3);
//        String data = orderTreeFeign.allowJoinTree().getData();
//        System.out.println(data);


//  // ================================
//        String serialize = JsonUtils.serialize(Arrays.asList("1000006,1000007"));
//        ResponseInfo<Map<Long, List<String>>> mapResponseInfo = productManageFeign.queryPayTypeAndCouponType("1000006;1000007");
//        System.out.println(mapResponseInfo);
//
//        BigDecimal amout = new BigDecimal(0);
//        amout.add(new BigDecimal(1));
//        System.out.println(amout);
//        String data = eBankFeign.accountEntry("123456", "456789", new BigDecimal(5), "OrderManage", 32, "http://123", "subject", "tradeType",50,"00");
//        System.out.println(data);
//        ResponseInfo json = JSONObject.parseObject(data,ResponseInfo.class);
//        Map<String,Object> dataMap = new HashMap<>();
//        dataMap=  JSONObject.parseObject((String) json.getData(),dataMap.getClass());
//        System.out.println(JSONObject.toJSONString(dataMap));

    // ================================
//        Map<Long,Integer> map = new HashMap<>();
//        map.put(54l,4);
//
//        System.out.println(JsonUtils.serialize(map));
////
//        findFreightDTO findFreightDTO = new findFreightDTO();
//        findFreightDTO.setProvince("140000");
//        findFreightDTO.setCity("140200");
//        findFreightDTO.setMap(map);
//
//        ResponseInfo<List<FreightDTO>> freightDTO1 = productManageFeign.findFreightDTO(JsonUtils.serialize(findFreightDTO));
//        System.out.println(freightDTO1);

        // ================================


//        Map<String,String> map = new HashMap<>();
//        map.put("userNo","7839455605230198784");
//        String serialize = JsonUtils.serialize(map);
//        System.out.println(serialize);
//        volumeMarketFeign.queryListAll(serialize).getData().forEach(System.out::println);


//         volumeMarketFeign.queryByIds("28;35").getData().forEach(System.out::println);




//        ResponseInfo<Map<Long, List<String>>> mapResponseInfo = productManage.queryPayTypeAndCouponType(new Long[]{1000006l, 1000007l});
//        System.out.println(mapResponseInfo);

//        System.out.println(iOrderConsigneeService.add("1212456", Arrays.asList("5464132", "4564654"), 1));

//        System.out.println("消息开始发送");
//        delayProducer.sendDelay("66666666666666",5*1000);
//        rabbitTemplate.convertAndSend("othreOrder.forward.exchange", "othreOrder.test", "6666666666666666666666");



//        try {
//            System.out.println("在等待");
//            Thread.sleep(1000*10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        messageQueueService.send(MQConstant.HELLO_QUEUE_NAME, "message666666666666666666666");
//        System.out.println("开始发送消息");
//        messageQueueService.send(MQConstant.HELLO_QUEUE_NAME,"message666666666666666666666",5000);


//        String[] mySerchaHistory = searchHistoryMapper.getMySerchaHistory("456");
//        System.out.println(mySerchaHistory[0]);

//        orderItemMapper.delOrder("7837175263424774145");

//
//        redis.setHashCache("test","1_10","{1}");
//        redis.setHashCache("test","2_10","{xxxx}");

//        System.out.println(redis.getHashCache("test","2_10"));
//        System.out.println(redis.del("test"));


//
//        //JSON（[1,2,3,4]） 转 集合
//        JsonUtils.parseList("[1,2,3,4]", Integer.class).forEach(System.out::println);
//
//        Map<Integer,String> map = new HashMap<>();
//        map.put(1,"1");
//        map.put(2,"2");
//        map.put(3,"3");
//        String serialize = JsonUtils.serialize(map);
//        System.out.println(serialize);
//        //JSON
//        Map<Integer, String> integerStringMap = JsonUtils.parseMap(serialize, Integer.class, String.class);
//        System.out.println(integerStringMap);


    }

    @Test
    public void test2(){
//        String str = "{\n" +
//                "    \"1\":[\"4\",\"578\"],\n" +
//                "    \"2\":[\n" +
//                "      \"5\",\"678\"\n" +
//                "    ]\n" +
//                "}";
//        JsonUtils.nativeRead(str, new TypeReference<Map<Integer, Integer[]>>() {}).forEach((k, v) ->{
//            System.out.println(k +"\a"+ v[0] + "\a" + v[1]);
//        });


//        OrderCart orderCart = new OrderCart();
//        orderCart.setRemaks("备注");
//        OrderCartDTO orderCartDTO = new OrderCartDTO();
//
//
//        Dto2Entity.transalte(orderCart,orderCartDTO);
//
//        System.out.println(orderCartDTO);
    }


}
