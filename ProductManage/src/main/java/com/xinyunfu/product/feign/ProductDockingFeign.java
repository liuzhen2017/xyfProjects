package com.xinyunfu.product.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.JDPostParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author TYM
 * @date 2019/8/9
 * @Description :
 */
@FeignClient("ProductDocking")
public interface ProductDockingFeign {
    /**
     * 多数参数都可以从 proSku中获取
     *
     * @param proId     proId
     * @param price     出售价格：proSku中的 price
     * @param costPirce 协议价：proSku中的 costPirce
     * @param skuId     jd skuId，对应 proSku中的 skuNo
     * @param shelfId   上架位置，需要指定
     * @return
     */
    @GetMapping("/productDto/query.do")
    ResponseInfo getProductDto(@RequestParam("proId") Long proId,
                               @RequestParam("price") BigDecimal price,
                               @RequestParam("costPirce") BigDecimal costPirce,
                               @RequestParam("skuId") String skuId,
                               @RequestParam("shelfId") String shelfId);

    /**
     * 根据地区和skuId查询京东商品的邮费
     *
     * @param jdPostParam
     * @return
     */
    @PostMapping("/preOrder/queryFreight")
    ResponseInfo findJDFreight(@RequestBody JDPostParam jdPostParam);

    /**
     * 根据skuId查询京东商品是否可售
     * @param skuIds
     * @return
     */
    @PostMapping("/preOrder/checkSale")
    ResponseInfo<String> checkSale(@RequestBody String skuIds);

    @PostMapping("/preOrder/queryAreaLimit")
    ResponseInfo<String> queryAreaLimit(@RequestBody String str);






}
