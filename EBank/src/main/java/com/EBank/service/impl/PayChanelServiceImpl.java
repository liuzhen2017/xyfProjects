package com.EBank.service.impl;

import com.EBank.entity.PayChannel;
import com.EBank.mapper.PayChannelMapper;
import com.EBank.service.PayChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuzhen
 * accFlow Service
 */
@Slf4j
@Service
public class PayChanelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {


    /**
     * 分页查询
     * @param accFlow 请求参数
     * @return accFlow分页列表
     */
    public ResponseInfo<IPage<PayChannel>> list(Integer pageSize, Integer page) {
        LambdaQueryWrapper<PayChannel> queryWrapper = new LambdaQueryWrapper<>();
        Page<PayChannel> pages = new Page<PayChannel>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
    }


}