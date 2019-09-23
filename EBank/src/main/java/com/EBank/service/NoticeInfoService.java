package com.EBank.service;

import com.EBank.entity.NotifyInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface NoticeInfoService extends IService<NotifyInfo>{

    /**
     * innerAcctManger分页列表
     *
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<NotifyInfo>> list(Integer page, Integer pageSize);

}