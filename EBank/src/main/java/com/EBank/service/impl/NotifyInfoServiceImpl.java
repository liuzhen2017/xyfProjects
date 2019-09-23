package com.EBank.service.impl;

import com.EBank.entity.NotifyInfo;
import com.EBank.mapper.NofityInfoMapper;
import com.EBank.service.NoticeInfoService;
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
public class NotifyInfoServiceImpl extends ServiceImpl<NofityInfoMapper, NotifyInfo> implements NoticeInfoService {


    /**
     * 分页查询
     * @return accFlow分页列表
     */
    public ResponseInfo<IPage<NotifyInfo>> list(Integer pageSize, Integer page) {
        LambdaQueryWrapper<NotifyInfo> queryWrapper = new LambdaQueryWrapper<>();
        Page<NotifyInfo> pages = new Page<NotifyInfo>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
    }


}