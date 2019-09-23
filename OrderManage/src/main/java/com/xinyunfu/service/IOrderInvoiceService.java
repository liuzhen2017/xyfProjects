package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.dto.InvoiceDTO;
import com.xinyunfu.model.OrderInvoice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单发票表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-03
 */
public interface IOrderInvoiceService extends IService<OrderInvoice> {

    /**
     * 根据发票状态 和发票类型 获取
     * @param status
     * @param type
     * @return
     */
    Page<InvoiceDTO> getInvoceDTO(Page page,Integer status, Integer type);

}
