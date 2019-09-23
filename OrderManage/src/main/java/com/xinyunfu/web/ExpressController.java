package com.xinyunfu.web;

import com.xinyunfu.dto.OrderDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author XRZ
 * @date 2019/8/15
 * @Description : 获取物流信息接口
 */
@RestController
@RequestMapping("/Express")
public class ExpressController {

    @Autowired
    private IExpressService iExpressService;

    /**
     *  根据订单号获取物流信息
     * @param vo
     * @return
     */
    @PostMapping("/getExpressInfoByOrderNo")
    public ResponseInfo<String> getExpressInfoByOrderNo(@RequestBody OrderDTO vo){
        return ResponseInfo.success(iExpressService.getExpressInfoByOrderNo(vo.getOrderNo()));
    }


}
