package com.xinyunfu.feign;

import com.xinyunfu.dto.docking.*;
import com.xinyunfu.dto.product.JDSaleSkusParam;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/2
 * @Description :
 */
@FeignClient("ProductDocKing")
public interface ProductDocKingFeign {

//    /**
//     * 生成序列号，下单时用
//     * @param param
//     * @return
//     */
//    @PostMapping("/jdEntry/createSerialNum.do")
//    ResponseInfo<Integer> createSerialNum(@RequestBody String param);
//
//


    /**
     * 查询订单
     */
    @GetMapping("/jdEntry/getOrder.do")
    ResponseInfo<List<JDOrderDto>> getOrder(@RequestParam("orderId") long orderId);

    /**
     * 根据第三方订单号反查jd订单号 J
     */
    @GetMapping("/jdEntry/getOrderId.do")
    ResponseInfo<JDThirdOrderDto> getOrderId(@RequestParam("thirdOrder") String thirdOrder);


    /**
     * POST请求，预下单
     * @param preOrderParam
     * @return 返回第三方订单号
     */
    @PostMapping("/jdEntry/preOrder.do")
    ResponseInfo<String> preOrder(@RequestBody JDPreOrderParam preOrderParam);


    /**
     * 确认下单
     *
     * @param order_no 第三方订单号
     * @param type     更新上架真实销量：1表示需要，0表示不需要，默认值1
     * @return
     */
    @PatchMapping("/jdEntry/ensureOrder.do")
    ResponseInfo<String> ensureOrder(@RequestParam("order_no") long order_no,
                                     @RequestParam(value = "type", defaultValue = "1") String type);


    /**
     * 获取物流信息  JDLogisticsDto
     *
     * @param orderId
     * @return
     */
    @GetMapping("/jdEntry/getOrderTrack")
    ResponseInfo<JDLogisticsDto> getOrderTrack(@RequestParam("orderId") Long orderId);






    //=================================================================


    /**
     * 查询邮费
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryFreight")
    ResponseInfo queryFreight(@RequestBody JDPostParam param);

    /**
     * 查询商品是否可售
     *
     * @param skuIds
     * @return
     */
    @PostMapping("/preOrder/checkSale")
    ResponseInfo checkSale(@RequestBody JDSaleSkusParam skuIds);


    /**
     * 查询区域购买是否被限制
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryAreaLimit")
    ResponseInfo queryAreaLimit(@RequestBody JDAreaLimitParam param);

    /**
     * 查询商品是否有货
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryStock")
    ResponseInfo queryStock(@RequestBody JDStockParam param);






}
