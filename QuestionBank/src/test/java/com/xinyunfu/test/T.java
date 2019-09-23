package com.xinyunfu.test;


import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.Runner;
import com.xinyunfu.constant.Common;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IAnswerRecordService;
import com.xinyunfu.service.ISubjectService;
import com.xinyunfu.service.impl.AnswerRecordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;


/**
 * @author XRZ
 * @date 2019/7/6 0006
 * @Description :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Runner.class )
public class T {


    @Autowired
    private VolumeMarketFeign volumeMarketFeign;
    @Autowired
    private ISubjectService iSubjectService;

    @Autowired
    private AnswerRecordServiceImpl AnswerRecordServiceImpl;

    @Test
    public void test(){


//        Map<String,Object> map = new HashMap<>();
//        map.put("orderNo","123456");
//        map.put("userNo","456");
//        map.put("subjectIndex","1");
//
//        ResponseInfo<Object> res = volumeMarketFeign.activationCoupon(new JSONObject(map));
//        System.out.println(res.getCode());


//        int[] subject = iSubjectService.getSubject();
//
//        for (int i : subject) {
//            System.out.println(i);
//        }

//        orderItemMapper.delOrder("7837175263424774145");

        




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
//            System.out.println(k +"\t"+ v[0] + "\t" + v[1]);
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
