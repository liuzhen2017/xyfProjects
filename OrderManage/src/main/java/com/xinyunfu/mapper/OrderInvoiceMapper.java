package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.dto.InvoiceDTO;
import com.xinyunfu.model.OrderInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单发票表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-03
 */
public interface OrderInvoiceMapper extends BaseMapper<OrderInvoice> {

    Page<InvoiceDTO> getInvoceDTO(@Param("page") Page page,@Param("status") Integer status, @Param("type") Integer type);

}
