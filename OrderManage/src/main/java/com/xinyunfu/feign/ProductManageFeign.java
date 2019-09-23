package com.xinyunfu.feign;

import com.xinyunfu.dto.findFreightDTO;
import com.xinyunfu.dto.product.FreightDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.utils.JsonUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author XRZ
 * @date 2019/7/17
 * @Description : 商品服务
 */
@FeignClient("ProductManage")
public interface ProductManageFeign {

    /**
     *  通过商品ID 获取 支付方式，可使用券的类型
     * @param ids "1000006;1000007"
     * @return Map<商品ID,["可使用的支付方式1,...","支持券的类型,..."]>
     */
    @GetMapping("/product/queryPayTypeAndCouponType")
    ResponseInfo<Map<Long,List<String>>> queryPayTypeAndCouponType(@RequestParam("str") String str);

    /**
     * 根据SKU ID获取该商品的所有信息
     * @param province 省份编号
     * @param city     市区编号
     * @param map       Map<sku_ID,购买的数量>
     * @return  ResponseInfo<List<FreightDTO>>
     */
    @GetMapping("/product/findFreightDTO")
    ResponseInfo<List<FreightDTO>> findFreightDTO(@RequestParam("str") String str);

    /**
     * 根据skuId查询商品类型,0普通商品,1秒杀商品,2套餐商品
     * @param ids SKUID,多个使用逗号拼接
     * @return Map<SKUID,商品类型>
     */
    @GetMapping("/sku/findProTypeBySkuId")
    ResponseInfo<Map<Long,Integer>> findProTypeBySkuId(@RequestParam("ids") String ids);

    /**
     * 减库存
     * @param str  Map<SKUID,数量>
     * @return
     */
    @PostMapping("/sku/subtractStock")
    Boolean subtractStock(@RequestBody Map<Long,Integer> map);

    /**
     * 归还库存
     * @param str Map<SKUID,数量>
     * @return
     */
    @PostMapping("/sku/addStock")
    Boolean addStock(@RequestBody Map<Long,Integer> map);

}
