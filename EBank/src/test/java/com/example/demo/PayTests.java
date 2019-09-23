package com.example.demo;

import com.EBank.EBankApplication;
import com.EBank.feign.OrderManage;
import com.EBank.feign.OrderTreeFeign;
import com.EBank.util.PayMangerUtil;
import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EBankApplication.class )

@Slf4j
public class PayTests {

    @Autowired
    OrderManage orderManage;
    @Autowired
    OrderTreeFeign orderTreeFeign;
    @Test
    public void testOrder() {
//       String stringResponseInfo = orderManage.payCallback("7839390945587068928","000151",new BigDecimal(1200),true);
//        log.info(JSONObject.toJSONString(stringResponseInfo));
    }
    @Test
    public void testOrderTree() {
//        ResponseInfo<String> stringResponseInfo = orderTreeFeign.joinOrderTree("7839375286373384192",1,"10000",2);
//        log.info(JSONObject.toJSONString(stringResponseInfo));
    }

}
