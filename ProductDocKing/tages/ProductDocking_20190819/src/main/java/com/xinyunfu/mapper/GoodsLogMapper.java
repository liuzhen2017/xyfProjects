package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.model.GoodsLog;
import org.apache.ibatis.annotations.Select;


public interface GoodsLogMapper extends BaseMapper<GoodsLog> {

      @Select("select end_time from goods_synclog order by end_time desc limit 1")
      String getLatest();

}
