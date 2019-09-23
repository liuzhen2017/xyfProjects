package com.EBank.mapper;

import com.EBank.entity.PayChannel;
import com.EBank.entity.TransactionSendLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuzhen
 * accFlow Mapper
 */
@Mapper
public interface TransactionSendLogMapper extends BaseMapper<TransactionSendLog> {
}