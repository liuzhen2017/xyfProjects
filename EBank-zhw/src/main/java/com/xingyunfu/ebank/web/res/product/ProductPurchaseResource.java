package com.xingyunfu.ebank.web.res.product;

import com.xingyunfu.ebank.dto.product.ProductRecordAddDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddRespDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddV2DTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.service.product.ProductPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 产品购买管理<br/>
 * 产品包括：商品，套餐
 */
@Slf4j
@RestController
@RequestMapping("/ebank/product")
public class ProductPurchaseResource {

    @Autowired private ProductPurchaseService productPurchaseService;

    /**
     * 购买产品，立即支付, 调用支付中心，获取支付URL
     */
    @PostMapping
    public ProductRecordAddRespDTO purchase(@RequestBody@Validated ProductRecordAddDTO productRecordAdd) throws Exception {
        log.info("REST request to purchase. productRecordAdd: {}", productRecordAdd);
        ProductRecordAddV2DTO product = new ProductRecordAddV2DTO(productRecordAdd);
        ProductRecordAddRespDTO resp = productPurchaseService.purchase(product);
        log.info("REST request to purchase. success! result: {}", resp);
        return resp;
    }

    /**
     * 验证订单是否成功
     */
    @GetMapping
    public Boolean purchase(@RequestParam String orderNo) throws Exception {
        log.info("REST request to check purchase is success. orderNo: {}", orderNo);
        Boolean success = productPurchaseService.purchase(orderNo);
        log.info("REST request to check purchase is success. success! result: {}", success);
        return success;
    }

    /**
     * 快捷支付使用授权码确认支付
     */
    @GetMapping("/fast")
    public void fastPaymentConfirm(@RequestParam String orderNo, @RequestParam String verifyCode) throws Exception {
        log.info("REST request to fast payment confirm. orderNo: {}, verifyCode: {}", orderNo, verifyCode);
        productPurchaseService.fastPaymentConfirm(orderNo, verifyCode);
        log.info("REST request to fast payment confirm. success!");
    }
}
