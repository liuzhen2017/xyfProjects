package com.ruoyi.controller.product;

import com.alibaba.fastjson.JSON;
import com.ruoyi.domain.*;
import com.ruoyi.dto.ProDto;
import com.ruoyi.feign.ProductManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author TYM
 * @date 2019/8/5
 * @Description :
 */
@RestController
public class SaveOrUpdateController {

//    @Autowired
//    private ProductManageFeign productManageFeign;
//    @PostMapping("/pro/save")
//    @Transactional
//    public String savePro(String proJson){
//        ProDto proDto=JSON.parseObject(proJson, ProDto.class);
//
//        Product product=new Product();
//        product.setProName(proDto.getProName());
//        product.setProTitle(proDto.getProTitle());
//        product.setUnit(proDto.getUnit());
//        product.setWeight(proDto.getWeight());
//        //插入produ
//        productManageFeign.saveProduct(product);
//
//        List<Integer> type=proDto.getPayType();
//        String payType="";
//        for (int i:type
//             ) {
//            payType=payType+i+",";
//        }
////        payType.substring()
//        Long proId=product.getProId();
//        ProDetails proDetails=new ProDetails();
//        proDetails.setProId(proId);
//        proDetails.setSpecs(proDto.getSpecs());
//        proDetails.setDetails(proDto.getDetails());
//        //插入prodetails
//        productManageFeign.saveProDetails(proDetails);
//
//        ProChannelRelation proChannelRelation=new ProChannelRelation();
//        proChannelRelation.setProId(proId);
//        proChannelRelation.setChannelId(proDto.getChannelId());
//        //插入proChannelRelatiom
//        productManageFeign.saveProChannelRelation(proChannelRelation);
//
//        List<String> propertys=proDto.getPropertyName();
//        List<String> values=proDto.getPropertyValue();
//
//        for (int i=0;i<propertys.size()-1;i++
//             ) {
//            String propertyName=propertys.get(i);
//            ProProperty proProperty=new ProProperty();
//            proProperty.setProId(proId);
//            proProperty.setPropertyName(propertyName);
//            productManageFeign.saveProerty(proProperty);
//            Long propertyId=proProperty.getPropertyId();
//            String propertyValue=values.get(i);
//            String[] valueNames=propertyValue.split(";");
//            for (String valueName: valueNames
//                 ) {
//                ProPropertyValue proPropertyValue=new ProPropertyValue();
//                proPropertyValue.setPropertyId(propertyId);
//                proPropertyValue.setValueText(valueName);
//                productManageFeign.savePropertyValue(proPropertyValue);
//            }
//        }
//        String imgUrls=proDto.getImgUrl();
//        String[] str=imgUrls.split(";");
//        for (int i=0;i<str.length-1;i++
//             ) {
//            ProImage proImage=new ProImage();
//            proImage.setProId(proId);
//            proImage.setImgUrl(str[i]);
//            if (i==0)
//                proImage.setIsDefault(1);
//            else
//                proImage.setIsDefault(0);
//            productManageFeign.saveProImage(proImage);
//        }
//        return null;
}





























