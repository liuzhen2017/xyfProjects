package com.EBank.service.impl;

import com.EBank.entity.TransactionSendLog;
import com.EBank.mapper.TransactionSendLogMapper;
import com.EBank.service.TransactionSendLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuzhen
 * accFlow Service
 */
@Slf4j
@Service
public class TransactionSendLogServiceImpl extends ServiceImpl<TransactionSendLogMapper, TransactionSendLog> implements TransactionSendLogService {


//    /**
//     * 分页查询
//     * @param accFlow 请求参数
//     * @return accFlow分页列表
//     */
//    public ResponseInfo<IPage<PayChannel>> list(Integer pageSize,Integer page) {
//        LambdaQueryWrapper<PayChannel> queryWrapper = new LambdaQueryWrapper<>();
//        Page<PayChannel> pages = new Page<PayChannel>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
//        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
//    }


}