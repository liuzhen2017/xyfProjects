//package com.ruoyi.feign;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.ruoyi.dto.InvoiceDTO;
//import com.ruoyi.domain.OrderItem;
//import com.ruoyi.util.Res;
//import com.ruoyi.util.ResUitls;
//import com.ruoyi.utils.ResponseInfo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//
///**
// * @author TYM
// * @date 2019/8/4
// * @Description :
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OrderManageFeignTest {
//
//    @Autowired
//    private OrderManageFeign orderManageFeign;
//
//    @Test
//    public void findOrderItemByChainId() {
//        OrderItem orderItem=new OrderItem();
//        orderItem.setStoreId(1L);
//        ResponseInfo<Object> res = orderManageFeign.findOrderItemByChainId(orderItem, 1, 3);
//        Res<OrderItem> res1 = ResUitls.getRes(res.getData(), OrderItem.class);
//        List<OrderItem> records = res1.getRecords();
//        System.out.println(records);
////        IPage<Object> feign = JsonUtils.feign(res.getData(), new TypeReference<IPage<Object>>() {});
////        ResponseInfo<IPage<OrderItem>> obj =orderManageFeign.findOrderItemByChainId(orderItem,1,3);
////        System.out.println(obj.toString());
//        System.out.println("==============请求成功==============");
//    }
//
//
//    @Test
//    public void getInvoceDTO() {
//        ResponseInfo<InvoiceDTO> res=orderManageFeign.getInvoceDTO(-1,-1,1,3);
//        Res<InvoiceDTO> res1 = ResUitls.getRes(res.getData(), InvoiceDTO.class);
//        System.out.println(res1);
//        System.out.println("==================="+res.toString());
//    }
//
//
//
//
//
//
//
//
//}