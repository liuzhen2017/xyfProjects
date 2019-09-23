package com.ruoyi.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.domain.GoodsSynclog;
import com.ruoyi.domain.ShelfGoodsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.utils.ResponseInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * 商品同步服务
 */
@FeignClient(value = "ProductDocKing", decode404 = true)
public interface ProductDockingFeign {

      /**
       * 商户商品上架
       */
      @PostMapping("/jdEntry/shelfGoods.do")
      ResponseInfo<String> shelfGoods(@RequestBody List<ShelfGoodsParam> shelfGoodsParam);

      /**
       * 商户商品下架
       */
      @DeleteMapping("/jdEntry/deleteSG.do")
      ResponseInfo<String> deleteSG(@RequestParam("skuIds") String skuIds, @RequestParam("shelf_id") int shelf_id);


      /**
       * 查询同步记录列表
       */
      @PostMapping("/goodsLog/getRecordList")
      ResponseInfo<Page<GoodsSynclog>> getRecordList(@RequestBody GoodsSynclog goodsLog,
                                                     @RequestParam("page") Integer page,
                                                     @RequestParam("pageSize") Integer pageSize);

      /**
       * 同步JD商品
       */
      @GetMapping("/syncData/loadData.do")
      void syncData(@RequestParam(required = false, value = "param") String param);


      /**
       * 获取同步列表的最新时间
       */
      @GetMapping("/goodsLog/getLatest")
      ResponseInfo<String> getLatest(@RequestParam(required = false, value = "param") String param);

}
