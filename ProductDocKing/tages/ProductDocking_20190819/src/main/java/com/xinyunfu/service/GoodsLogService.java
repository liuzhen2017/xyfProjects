package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.GoodsLog;

import java.sql.Timestamp;
import java.util.List;

public interface GoodsLogService extends IService<GoodsLog> {
      Boolean saveGoodsLog(GoodsLog goodsLog);

      String getLatest();

}
