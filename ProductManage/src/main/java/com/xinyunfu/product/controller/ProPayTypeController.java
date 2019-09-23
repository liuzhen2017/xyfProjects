package com.xinyunfu.product.controller;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProPayType;
import com.xinyunfu.product.service.IProPayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TYM
 * @date 2019/8/7
 * @Description :
 */
@RestController
@RequestMapping("/proPayType")
public class ProPayTypeController {

    @Autowired
    private IProPayTypeService proPayTypeService;

    @PostMapping("/save")
    public ResponseInfo<String> save(@RequestBody ProPayType proPayType) {
        if (proPayTypeService.save(proPayType))
            return ResponseInfo.success("新增成功");
        else
            return ResponseInfo.error("新增失败!");
    }

    /**
     * 根据payType查询id
     *
     * @param payType
     * @return
     */
    @GetMapping("/getId{payType}")
    public Long findIdByPayType(@PathVariable("payType") String payType) {
        return proPayTypeService.findIdByPayType(payType);
    }

    /**
     * 修改支付方式
     * @param proPayType
     * @return
     */
    @PostMapping("/update")
    ResponseInfo<String> updateProPayType(@RequestBody ProPayType proPayType){
        if (proPayTypeService.saveOrUpdate(proPayType)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }


}
