package com.xinyunfu.web.back;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.GoodsSource;
import com.xinyunfu.constant.InvoiceStatus;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.InvoiceDTO;
import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.dto.back.BackOrderDTO;
import com.xinyunfu.dto.back.SelectOrderDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.*;
import com.xinyunfu.service.*;
import com.xinyunfu.utils.RedisCommonUtil;
import com.xinyunfu.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/2
 * @Description : 供应商后台相关接口
 */
@RestController
@RequestMapping("/Supplier")
public class SupplierOrderController {

    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private IOrderInvoiceService iOrderInvoiceService;
    @Autowired
    private IInvoiceInfoService iInvoiceInfoService;
    @Autowired
    private IExpressService iExpressService;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;


    /**
     * 获取物流公司信息
     * @return
     */
    @GetMapping("/getExpresss")
    public ResponseInfo<List<Express>> getExpresss(){
        if(redis.exists(Redis.KEY_EXPRESS)){
            return ResponseInfo.success((List<Express>)redis.getCache(Redis.KEY_EXPRESS));
        }
        List<Express> list = iExpressService.list(null);
        if(!CollectionUtils.isEmpty(list)){
            redis.setCache(Redis.KEY_EXPRESS,list);
        }
        return ResponseInfo.success(list);
    }

    /**
     * 确认发货
     * @param currentUserId
     * @param orderNo           订单编号
     * @param shippingCompName  快递公司名称
     * @param shippingSn        快递单号
     * @return
     */
    @GetMapping("/confirmDelivery")
    public ResponseInfo<String> confirmDelivery(@RequestParam("currentUserId") String currentUserId,
                                                @RequestParam("orderNo") String orderNo,
                                                @RequestParam("shippingCompName") String shippingCompName,
                                                @RequestParam("shippingSn") String shippingSn){
        if(StringUtils.isEmpty(currentUserId) || StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(shippingCompName) || StringUtils.isEmpty(shippingSn))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        iOrderItemService.confirmDelivery(currentUserId,orderNo,shippingCompName,shippingSn);
        return ResponseInfo.success(null);
    }

    /**
     * 查询供应链的所有订单
     * @param orderItem
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/orderItem/list")
    public ResponseInfo<Page<BackOrderDTO>> findOrderItemByChainId(@RequestBody SelectOrderDTO vo,
                                                                        @RequestParam("page") Integer page,
                                                                        @RequestParam("pageSize") Integer pageSize){
        return ResponseInfo.success(iOrderItemService.findOrderItemByChainId(vo,page,pageSize));
    }



    /**
     * 根据子订单id查询发票信息
     * @param orderNo
     * @return
     */
    @GetMapping("/orderInvoice/findByOrderItemId")
    public ResponseInfo<InvoiceDTO> findOrderInvoiceByOrderItemId(@RequestParam("orderNo")String orderNo){
        if(StringUtils.isEmpty(orderNo))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        OrderItem item = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getItemNo, orderNo));
        if(null == item)
            throw new CustomException(ExecptionEnum.ORDER_ITEM_UNDEFINED);
        OrderInvoice invoice = iOrderInvoiceService.getOne(new LambdaQueryWrapper<OrderInvoice>().eq(OrderInvoice::getItemNo, orderNo));
        if(null == invoice)
            throw new CustomException(ExecptionEnum.BY_ORDERNO_GET_INVOICEINFO);
        InvoiceInfo info = iInvoiceInfoService.getById(invoice.getInvoiceId());
        if(null == info)
            throw new CustomException(ExecptionEnum.BY_INVOICEID_GET_INVOICEINFO);
        InvoiceDTO vo = new InvoiceDTO();
        vo.setType(info.getType());
        vo.setEmail(info.getEmail());
        vo.setUnitName(info.getUnitName());
        vo.setTaxpayerNumber(info.getTaxpayerNumber());
        vo.setAmount(item.getAmount());
        vo.setPayAmount(item.getPayAmount());
        vo.setItemNo(item.getItemNo());
        vo.setStatus(item.getStatus());
        vo.setInvoiceId(invoice.getInvoiceId());
        vo.setInvoiceIdStatus(invoice.getStatus());
        return ResponseInfo.success(vo);
    }

    /**
     * 修改订单发票状态为待审核
     * @param orderId
     * @return
     */
    @GetMapping("/oederItem/updateInvoice")
    public ResponseInfo<String> updateInvoice(@RequestParam("currentUserId") String currentUserId,
                                              @RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        OrderInvoice invoice = new OrderInvoice();
        invoice.setStatus(InvoiceStatus.EXAMINE);
        if (!iOrderInvoiceService.update(invoice,new LambdaQueryWrapper<OrderInvoice>().eq(OrderInvoice::getItemNo,orderNo)))
            throw new CustomException(ExecptionEnum.ERROR_UPDATE_INVOICE_STATUC);
        return ResponseInfo.success(null);
    }

    /**
     * 审核发票,修改发票状态为已开
     * @param orderId
     * @return
     */
    @GetMapping("/orderItem/checkInvoice")
    public ResponseInfo<String> checkInvoice(@RequestParam("currentUserId") String currentUserId,
                                             @RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        OrderInvoice invoice = new OrderInvoice();
        invoice.setStatus(InvoiceStatus.ALREADY);
        invoice.setBillingDate(TimeUtils.getDate()); //记录开票日期
        if (!iOrderInvoiceService.update(invoice,new LambdaQueryWrapper<OrderInvoice>().eq(OrderInvoice::getItemNo,orderNo)))
            throw new CustomException(ExecptionEnum.ERROR_UPDATE_INVOICE_STATUC);
        return ResponseInfo.success(null);
    }



}
