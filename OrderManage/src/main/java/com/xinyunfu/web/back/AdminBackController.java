package com.xinyunfu.web.back;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.InvoiceStatus;
import com.xinyunfu.dto.InvoiceDTO;
import com.xinyunfu.dto.back.CommdityInfoDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.OrderCommodity;
import com.xinyunfu.model.OrderConsignee;
import com.xinyunfu.model.OrderInvoice;
import com.xinyunfu.model.OrderItem;
import com.xinyunfu.service.*;
import com.xinyunfu.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XRZ
 * @date 2019/8/3
 * @Description : 总后台相关接口
 */
@RestController
@RequestMapping("/AdminBack")
public class AdminBackController {

    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    private IOrderInvoiceService iOrderInvoiceService;
    @Autowired
    private IInvoiceInfoService iInvoiceInfoService;
    @Autowired
    private IOrderCommodityService iOrderCommodityService;
    @Autowired
    private IOrderConsigneeService iOrderConsigneeService;


    /**
     * 根据发票类型和 发票状态 获取 订单信息和发票信息的DTO
     * @param type      全部传 -1
     * @param status    全部传 -1
     * @param page      默认为1
     * @param pageSize  默认为10
     * @return
     */
    @GetMapping("/getInvoceDTO")
    public ResponseInfo<Page<InvoiceDTO>> getInvoceDTO(@RequestParam("type") Integer type,
                                                        @RequestParam("status") Integer status,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize){
        if(page == null || page < 1) page = 1;
        if(pageSize == null || pageSize < 1) pageSize = 10;
        return ResponseInfo.success(iOrderInvoiceService.getInvoceDTO(new Page(page, pageSize),type, status));
    }

    /**
     * 查询所有需要开发票的订单和商品信息
     * @param orderItem  订单状态，快递公司名称，快递单号
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/orderItem/invoiceOrder")
    public ResponseInfo<IPage<OrderItem>> findOrderItemInvoice(@RequestBody OrderItem orderItem,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("pageSize") Integer pageSize){
        if( null == orderItem || null == page || null == pageSize || page < 1 || pageSize < 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<>();
        if(orderItem.getStatus() != null){
            wrapper.eq(OrderItem::getStatus,orderItem.getStatus());
        }
        if(orderItem.getShippingCompName() != null){
            wrapper.eq(OrderItem::getShippingCompName,orderItem.getShippingCompName());
        }
        if(orderItem.getShippingSn() != null){
            wrapper.eq(OrderItem::getShippingSn,orderItem.getShippingSn());
        }
        wrapper.orderByDesc(OrderItem::getCreatedDate);
        IPage<OrderItem> res = iOrderItemService.page(new Page<>(page, pageSize), wrapper);
        return ResponseInfo.success(res);
    }

    /**
     * 批量修改发票状态
     *
     * @param currentUserId 用户的ID
     * @param ids    发票ID，使用,号拼接的字符串
     * @param status        需修改的状态 （未开=0，审核中=1，已开=2）
     * @return
     */
    @GetMapping("/batchUpdateInvoice")
    public ResponseInfo<String> batchUpdateInvoice(@RequestParam("currentUserId") String currentUserId,
                                                   @RequestParam("ids") String ids,
                                                   @RequestParam("status") Integer status){
        if( StringUtils.isEmpty(currentUserId) || StringUtils.isEmpty(ids) || null == status)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        List<OrderInvoice> collect = Arrays.stream(ids.split(",")).map(id -> {
            OrderInvoice invoice = new OrderInvoice();
            invoice.setId(Long.valueOf(id));
            invoice.setStatus(status);
            invoice.setUpdatedBy(currentUserId);
            if(status == InvoiceStatus.ALREADY){ //如果修改发票状态为已开
                invoice.setBillingDate(TimeUtils.getDate()); //记录开票日期
            }
            return invoice;
        }).collect(Collectors.toList());
        if(iOrderInvoiceService.updateBatchById(collect)){
            return ResponseInfo.success(null);
        }
        throw new CustomException(ExecptionEnum.ERROR_UPDATE_INVOICE_STATUS);
    }


    /**
     *根据订单编号获取商品信息
     * @param itemNo
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getCommditysByItemNo/{itemNo}")
    public ResponseInfo<CommdityInfoDTO> getCommditysByItemNo(@PathVariable("itemNo") String itemNo){
        List<OrderCommodity> list = iOrderCommodityService.list(new LambdaQueryWrapper<OrderCommodity>()
                .eq(OrderCommodity::getItemNo, itemNo));
        OrderConsignee consignee = iOrderConsigneeService.getOne(new LambdaQueryWrapper<OrderConsignee>().eq(OrderConsignee::getOrderNo, itemNo));
        OrderItem item = iOrderItemService.getOne(new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getItemNo, itemNo));
        item.setCreateTime(TimeUtils.getStr2(item.getCreatedDate()));
        return ResponseInfo.success(new CommdityInfoDTO(list,consignee,item));
    }

}
