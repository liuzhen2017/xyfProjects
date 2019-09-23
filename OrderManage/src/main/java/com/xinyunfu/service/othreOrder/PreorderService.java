package com.xinyunfu.service.othreOrder;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.GoodsSource;
import com.xinyunfu.dto.AddOrderDTO;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.dto.docking.JDPreOrderDto;
import com.xinyunfu.dto.docking.JDPreOrderParam;
import com.xinyunfu.dto.docking.JDSkuParam;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.ProductDocKingFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author XRZ
 * @date 2019/8/2
 * @Description : 预下单服务
 */
@Slf4j
@Service
public class PreorderService {

    @Value("${othreOrder.email}")
    private String email;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private ProductDocKingFeign productDocKingFeign;

    /**
     * 预下单
     * @param currentUserId
     * @param vo
     * @param infoMap
     * @return Map<Long,Long> Map<商家ID,第三方订单号>
     */
    public Map<Long,Long> preOrder(String currentUserId, AddOrderDTO vo, Map<Long, FreightDTO> infoMap){
        //获取地址对象
        ResponseInfo<ShippingAddressInfoDTO> res = customerManageFeign.getAddress(vo.getAddressId());
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_LOG_NOTSHOW,res.getCode(),res.getMessage());
        // Map<商家ID，商品信息>
        Map<Long, List<FreightDTO>> collect = infoMap
                .values()
                .stream()
                .collect(Collectors.groupingBy(FreightDTO::getStoreId));
        //Map<商家ID，备注信息>
        Map<Long,String> remarkMap = new HashMap<>();
        //获取sku和购买的数量
        Map<Long,Integer> goodsMap = new HashMap<>();
        vo.getGoodsVos().forEach( goodsDTO -> {
            remarkMap.put(goodsDTO.getStoreId(),goodsDTO.getRemarks());
            goodsDTO.getCom().forEach( c -> {
                goodsMap.put(Long.valueOf(c.getSkuId()),c.getNum());
            });
        });
        Map<Long,Long> map = new HashMap<>(); //记录返回值
        collect.forEach( (k,v) -> {
            //根据不同的来源执行预下单
            Integer source = v.get(0).getSource();
            if(source == GoodsSource.JD){
                map.put(k,this.JD(remarkMap.get(k),v,goodsMap,res.getData()));
            }else if(source == GoodsSource.YYT){
                map.put(k,this.YYT());
            }else if(source == GoodsSource.OTHRE){
                map.put(k,this.OTHRE());
            }
            log.info("[下单操作]==========>订单来源：{},商品信息：{}",source,v);
        });
        return map;
    }


    /**
     * 京东预下单
     *
     * @param remark    备注
     * @param list      商品信息集合
     * @param goodsMap  Map<购物车ID,购买的数量>
     * @param addr      地址对象
     * @return 第三方订单号
     */
    private Long JD(String remark,List<FreightDTO> list, Map<Long,Integer> goodsMap,ShippingAddressInfoDTO addr){
        log.info("[下单操作]==========>京东预下单开始！");
        //转换为京东SKU信息
        List<JDSkuParam> jdSkuParamList = new ArrayList<>();
        list.forEach( c -> {
            JDSkuParam jdSkuParam = new JDSkuParam();
            jdSkuParam.setSkuId(c.getSkuId());
            jdSkuParam.setNum(goodsMap.get(c.getSkuId()));
            jdSkuParam.setBNeedAnnex(false);    //是否需要附件
            jdSkuParam.setBNeedGift(false);     //是否需要赠品
            jdSkuParamList.add(jdSkuParam);
        });
        JDPreOrderParam jd = new JDPreOrderParam();
        jd.setName(addr.getName());     //收货人
        jd.setProvince(Long.parseLong(addr.getAreaInfo().getProvince().getAreaCode())); //省编号
        jd.setCity(Long.parseLong(addr.getAreaInfo().getCity().getAreaCode()));         //市编号
        jd.setCounty(Long.parseLong(addr.getAreaInfo().getRegion().getAreaCode()));     //区编号
        jd.setProvinceName(addr.getAreaInfo().getProvince().getAreaLongName()); //省编号名字
        jd.setCityName(addr.getAreaInfo().getCity().getAreaLongName());         //市编号名字
        jd.setCountyName(addr.getAreaInfo().getRegion().getAreaLongName());     //区编号名字
        if( null != addr.getAreaInfo().getTown()){
          jd.setTownName(addr.getAreaInfo().getTown().getAreaLongName());
        }
        jd.setAddress(addr.getAddress());              //地址信息
        jd.setZip(addr.getPostCode());                 //邮编
        jd.setPhone("");                               //座机号 暂无传空
        jd.setMobile(Long.parseLong(addr.getPhone())); //手机号码
        jd.setEmail(email);                            //使用 自定义邮箱接收
        jd.setRemark(remark);                          //备注信息
        jd.setExt("");                                 //订单自定义字段（暂无）
        jd.setSku(jdSkuParamList.stream().toArray(JDSkuParam[]::new));  //商品信息
        ResponseInfo<String> res = productDocKingFeign.preOrder(jd);
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_SHOW,res.getCode(),res.getMessage());
        log.info("[下单操作]==========>调用京东接口预下单成功！第三方订单号为："+res.getData());
        return Long.valueOf(res.getData());
    }

    /**
     * 怡亚通预下单
     *
     * @return 第三方订单号
     */
    private Long YYT(){
        log.info("[下单操作]==========>怡亚通预下单开始！暂无处理！");
        return -1l;
    }

    /**
     * 其他预下单
     *
     */
    private Long OTHRE(){
        log.info("[下单操作]==========>其他预下单开始！暂无处理！");
        return -1l;
    }

}
