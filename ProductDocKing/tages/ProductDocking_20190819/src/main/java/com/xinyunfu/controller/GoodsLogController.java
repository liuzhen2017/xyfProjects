package com.xinyunfu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.GoodsLog;
import com.xinyunfu.service.GoodsLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/goodsLog")
@Slf4j
public class GoodsLogController {

      @Autowired
      private GoodsLogService goodsLogService;


      /**
       * 分页查询
       *
       */
      @PostMapping("/getRecordList")
      public ResponseInfo<IPage<GoodsLog>> getRecordList(@RequestBody GoodsLog goodsLog,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("pageSize") Integer pageSize) {
            IPage<GoodsLog> pages = new Page<>(page, pageSize);
            LambdaQueryWrapper<GoodsLog> wrapper = new LambdaQueryWrapper<>();
            if (goodsLog.getDescription() != null && StringUtils.isNotEmpty(goodsLog.getDescription())) {
                  wrapper.eq(GoodsLog::getDescription, goodsLog.getDescription());
            }
            if (goodsLog.getStartTime() != null && StringUtils.isNotEmpty(goodsLog.getStartTime().toString())) {
                  wrapper.gt(GoodsLog::getStartTime, goodsLog.getStartTime());
            }
            if (goodsLog.getEndTime() != null && StringUtils.isNotEmpty(goodsLog.getEndTime().toString())) {
                  wrapper.lt(GoodsLog::getEndTime, goodsLog.getEndTime());
            }

            return ResponseInfo.success(goodsLogService.page(pages, wrapper));
      }


      @GetMapping("/getLatest")
      public ResponseInfo<String> getLatest(@RequestParam(required = false) String param){

            String latest = goodsLogService.getLatest();
            if(latest != null){
                  return ResponseInfo.success(latest);
            }else {
                  return ResponseInfo.error(null);
            }
      }


}
