package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.mapper.GoodsLogMapper;
import com.xinyunfu.model.GoodsLog;
import com.xinyunfu.service.GoodsLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class GoodsLogServiceImpl extends ServiceImpl<GoodsLogMapper, GoodsLog>
          implements GoodsLogService {

      @Autowired
      private GoodsLogMapper goodsLogMapper;
      /**
       * 新增同步日志
       */
      @Override
      public Boolean saveGoodsLog(GoodsLog goodsLog) {
            return this.save(goodsLog);
      }

      //@Override
      //public List<GoodsLog> getRecordList(GoodsLog goodsLog) {
      //      LambdaQueryWrapper<GoodsLog> wrapper = new LambdaQueryWrapper<>();
      //      if (goodsLog.getDescription() != null && StringUtils.isNotEmpty(goodsLog.getDescription())) {
      //            wrapper.eq(GoodsLog::getDescription, goodsLog.getDescription());
      //      }
      //      if (goodsLog.getStartTime() != null && StringUtils.isNotEmpty(goodsLog.getStartTime().toString())) {
      //            wrapper.gt(GoodsLog::getStartTime, goodsLog.getStartTime());
      //      }
      //      if (goodsLog.getEndTime() != null && StringUtils.isNotEmpty(goodsLog.getEndTime().toString())) {
      //            wrapper.lt(GoodsLog::getEndTime, goodsLog.getEndTime());
      //      }
      //      return goodsLogMapper.selectList(wrapper);
      //}

      @Override
      public String getLatest() {

            return goodsLogMapper.getLatest();
      }
}
