package com.EBank.service.impl;

import com.EBank.entity.TransactionRecvLog;
import com.EBank.mapper.TransactionRecvLogMapper;
import com.EBank.service.TransactionRecvLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuzhen
 * accFlow Service
 */
@Slf4j
@Service
public class TransactionRecvLogServiceImpl extends ServiceImpl<TransactionRecvLogMapper, TransactionRecvLog> implements TransactionRecvLogService {


//    /**
//     * 分页查询
//     * @param accFlow 请求参数
//     * @return accFlow分页列表
//     */
//    @Autowired
//    public ResponseInfo<IPage<TransactionRecvLog>> list(Integer pageSize,Integer page) {
//        LambdaQueryWrapper<TransactionRecvLog> queryWrapper = new LambdaQueryWrapper<>();
//        Page<TransactionRecvLog> pages = new Page<TransactionRecvLog>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
//        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
//    }


}