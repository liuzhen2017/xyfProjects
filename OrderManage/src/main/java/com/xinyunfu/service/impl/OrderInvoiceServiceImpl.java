package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.dto.InvoiceDTO;
import com.xinyunfu.model.OrderInvoice;
import com.xinyunfu.mapper.OrderInvoiceMapper;
import com.xinyunfu.service.IOrderInvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单发票表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-03
 */
@Service
public class OrderInvoiceServiceImpl extends ServiceImpl<OrderInvoiceMapper, OrderInvoice> implements IOrderInvoiceService {

    @Autowired
    private OrderInvoiceMapper orderInvoiceMapper;

    /**
     * 根据发票状态 和发票类型 获取
     *
     * @param status
     * @param type
     * @return
     */
    @Override
    public Page<InvoiceDTO> getInvoceDTO(Page page,Integer status, Integer type) {
        return orderInvoiceMapper.getInvoceDTO(page,status, type);
    }
}
