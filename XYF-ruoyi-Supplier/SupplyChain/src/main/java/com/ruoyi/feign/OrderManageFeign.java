package com.ruoyi.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.dto.*;
import com.ruoyi.domain.OrderItem;
import com.ruoyi.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author TYM
 * @date 2019/8/2
 * @Description :订单微服务
 */
@FeignClient("OrderManageX")
public interface OrderManageFeign {


    /**
     * 获取物流公司信息
     * @return
     */
    @GetMapping("/Supplier/getExpresss")
    public ResponseInfo<Object> getExpresss();


    /**
     * 根据发票类型和 发票状态 获取 订单信息和发票信息的DTO
     * @param type      全部传 -1
     * @param status    全部传 -1
     * @param page      默认为1
     * @param pageSize  默认为10
     * @return Page<InvoiceDTO>
     */
    @GetMapping("/AdminBack/getInvoceDTO")
    public ResponseInfo<Page<InvoiceDTO>> getInvoceDTO(@RequestParam("type") Integer type,
                                                       @RequestParam("status") Integer status,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("pageSize") Integer pageSize);

    /**
     * 查询所有需要开发票的订单和商品信息 (需填充商品信息)
     * @param orderItem  订单状态，快递公司名称，快递单号
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/AdminBack/invoiceOrder")
    public ResponseInfo<Object> findOrderItemInvoice(@RequestBody OrderItem orderItem,
                                                               @RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize);

    /**
     * 确认发货
     * @param currentUserId
     * @param orderNo           订单编号
     * @param shippingCompName  快递公司名称
     * @param shippingSn        快递单号
     * @return
     */
    @GetMapping("/Supplier/confirmDelivery")
    public ResponseInfo<String> confirmDelivery(@RequestParam("currentUserId") String currentUserId,
                                                @RequestParam("orderNo") String orderNo,
                                                @RequestParam("shippingCompName") String shippingCompName,
                                                @RequestParam("shippingSn") String shippingSn);

    /**
     * 查询供应链的所有订单
     * @param orderItem  商家ID，订单状态，快递公司名称，快递单号
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/Supplier/orderItem/list")
    public ResponseInfo<Page<BackOrderDTO>> findOrderItemByChainId(@RequestBody SelectOrderDTO vo,
                                                                        @RequestParam("page") Integer page,
                                                                        @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据子订单id查询发票信息
     * @param orderNo
     * @return
     */
    @GetMapping("/Supplier/orderInvoice/findByOrderItemId")
    public ResponseInfo<InvoiceDTO> findOrderInvoiceByOrderItemId(@RequestParam("orderNo")String orderNo);

    /**
     * 修改订单发票状态为待审核
     * @param
     * @return
     */
    @GetMapping("/Supplier/oederItem/updateInvoice")
    public ResponseInfo<String> updateInvoice(@RequestParam("currentUserId") String currentUserId,
                                              @RequestParam("orderNo") String orderNo);

    /**
     * 审核发票,修改发票状态为已开
     * @param
     * @return
     */
    @GetMapping("/Supplier/orderItem/checkInvoice")
    public ResponseInfo<String> checkInvoice(@RequestParam("currentUserId") String currentUserId,
                                             @RequestParam("orderNo") String orderNo);


    /**
     * 批量修改发票状态
     *
     * @param currentUserId 用户的ID
     * @param ids    发票ID，使用,号拼接的字符串
     * @param status        需修改的状态
     * @return
     */
    @GetMapping("/AdminBack/batchUpdateInvoice")
    public ResponseInfo<String> batchUpdateInvoice(@RequestParam("currentUserId") String currentUserId,
                                                   @RequestParam("ids") String ids,
                                                   @RequestParam("status") Integer status);



    /**
     *根据订单编号获取商品信息
     * @param itemNo
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/AdminBack/getCommditysByItemNo/{itemNo}")
    public ResponseInfo<CommdityInfoDTO> getCommditysByItemNo(@PathVariable("itemNo") String itemNo);














}
