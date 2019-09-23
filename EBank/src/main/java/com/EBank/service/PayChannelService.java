package com.EBank.service;

import com.EBank.entity.PayChannel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface PayChannelService extends IService<PayChannel>{

    /**
     * innerAcctManger分页列表
     *
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<PayChannel>> list(Integer page, Integer pageSize);

}