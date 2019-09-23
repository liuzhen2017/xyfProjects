package com.xinyunfu.service;

import com.xinyunfu.model.InvoiceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单发票表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-01
 */
public interface IInvoiceInfoService extends IService<InvoiceInfo> {

    /**
     * 保存发票信息
     *      先放到redis缓存中（7）天，返回key
     * @param currentUserId
     * @param invoice
     * @return
     */
    String saveInvoice(String currentUserId, InvoiceInfo invoice);

    /**
     * 持久化发票信息
     * @param itemNos
     * @param id
     */
    void insert(List<String> itemNos, String id,String currentUserId);

}
