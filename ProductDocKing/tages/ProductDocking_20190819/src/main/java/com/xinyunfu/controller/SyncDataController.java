package com.xinyunfu.controller;


import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.service.SyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/syncData/")
@Slf4j
public class SyncDataController {
      @Autowired
      private SyncDataService syncDataService;

      @Autowired
      private ProductManageFeign productManageFeign;

      @GetMapping("loadData.do")
      public void syncData(@RequestParam(required = false,value = "param") String param) {

            syncDataService.loadData();
      }

      @RequestMapping("syncGoodsList.do")
      public void syncGoosList(String param) {

            syncDataService.syncGoodsList();
      }


}
