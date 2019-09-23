package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.Express;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物流公司表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */

public interface IExpressService extends IService<Express> {

    /**
     * 根据订单号获取物流信息
     * @param orderNo
     * @return
     */
    String getExpressInfoByOrderNo(String orderNo);

}
