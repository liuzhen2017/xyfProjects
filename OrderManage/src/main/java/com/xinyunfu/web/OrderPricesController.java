package com.xinyunfu.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单价格清单表 前端控制器
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@RestController
@RequestMapping("/orderPrices")
public class OrderPricesController {


//    @Autowired
//    private IOrderPricesService iOrderPricesService;
//
//    /**
//     * 获取我的 价格清单
//     * @param currentUserId 当前请求用户
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    @PostMapping("/getMyOrderPrices")
//    public ResponseInfo<IPage<OrderPrices>> getMyOrderPrices(@RequestHeader(Common.UID) String currentUserId,
//                                                             @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
//                                                             @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
//        if(StringUtils.isNotEmpty(currentUserId) ){
//            IPage<OrderPrices> prices = iOrderPricesService.getMyOrderPrices(currentUserId, page, pageSize);
//            if(prices.getRecords().size() > 0){
//                return ResponseInfo.success(prices);
//            }
//            return new ResInfo(Res.NO_DATA);
//        }
//        return new ResInfo(Res.ERROR_PARAM);
//    }
//
//
//    /**
//     * 根据主订单编号获取 价格清单
//     * @param currentUserId 当前请求用户
//     * @param orderNo
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    @PostMapping("/getOrderPricesByMasterNo/{orderNo}")
//    public ResponseInfo<IPage<OrderPrices>> getOrderPricesByMasterNo(@RequestHeader(Common.UID) String currentUserId,
//                                                                     @PathVariable("orderNo") String orderNo,
//                                                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
//                                                                     @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
//        if(StringUtils.isNotEmpty(currentUserId) && StringUtils.isNotEmpty(orderNo) ){
//            IPage<OrderPrices> pages = iOrderPricesService.getOrderPricesByMasterNo(orderNo, page, pageSize);
//            if(pages.getRecords().size() > 0){
//                return ResponseInfo.success(pages);
//            }
//            return new ResInfo(Res.NO_DATA);
//        }
//        return new ResInfo(Res.ERROR_PARAM);
//    }
//
//    /**
//     * 根据子订单编号获取 价格清单
//     * @param currentUserId 当前请求用户
//     * @param orderNo
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    @PostMapping("/getOrderPricesByItemNo")
//    public ResponseInfo<IPage<OrderPrices>> getOrderPricesByItemNo(@RequestHeader(Common.UID) String currentUserId,
//                                                                   @RequestBody OrderDTO vo){
//        if(StringUtils.isNotEmpty(currentUserId) && StringUtils.isNotEmpty(vo.getOrderNo()) ){
//            IPage<OrderPrices> pages = iOrderPricesService.getOrderPricesByItemNo(vo.getOrderNo(), vo.getPage(), vo.getPageSize());
//            if(pages.getRecords().size() > 0){
//                return ResponseInfo.success(pages);
//            }
//            return new ResInfo(Res.NO_DATA);
//        }
//        return new ResInfo(Res.ERROR_PARAM);
//    }

}
