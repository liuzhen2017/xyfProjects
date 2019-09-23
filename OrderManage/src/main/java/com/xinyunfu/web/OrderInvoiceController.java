package com.xinyunfu.web;


import com.xinyunfu.constant.Common;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.InvoiceInfo;
import com.xinyunfu.service.IInvoiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 订单发票表 前端控制器
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/orderInvoice")
public class OrderInvoiceController {

    @Autowired
    private IInvoiceInfoService iInvoiceInfoService;

    /**
     * 保存发票信息
     * @param invoice
     * @return 返回发票ID
     */
    @PostMapping("/saveInvoice")
    public ResponseInfo<Object> saveInvoice(@RequestHeader(Common.UID) String currentUserId,
                                            @RequestBody InvoiceInfo invoice){
        String id = iInvoiceInfoService.saveInvoice(currentUserId, invoice);
        return ResponseInfo.success(new HashMap<String,String>() {{ put("invoiceId",id); }});
    }


}
