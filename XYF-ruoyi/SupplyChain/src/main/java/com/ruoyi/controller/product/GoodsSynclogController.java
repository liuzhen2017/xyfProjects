package com.ruoyi.controller.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.domain.CouponManger;
import com.ruoyi.util.Res;
import com.ruoyi.util.ResUitls;
import com.ruoyi.utils.ResponseInfo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.feign.ProductDockingFeign;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.GoodsSynclog;
import com.ruoyi.service.IGoodsSynclogService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品同步 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-12
 */
@Controller
@RequestMapping("/system/goodsSynclog")
public class GoodsSynclogController extends BaseController {
      @Autowired
      private ProductDockingFeign productDockingFeign;

      private String prefix = "system/goodsSynclog";

      protected SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      @Autowired
      private IGoodsSynclogService goodsSynclogService;

      @RequiresPermissions("system:goodsSynclog:view")
      @GetMapping()
      public String goodsSynclog() {
            return prefix + "/goodsSynclog";
      }

      /**
       * 查询商品同步列表
       */
      @RequiresPermissions("system:goodsSynclog:list")
      @PostMapping("/list")
      @ResponseBody
      public TableDataInfo list(GoodsSynclog goodsSynclog) {

            PageDomain pageDomain = TableSupport.buildPageRequest();
            Integer page = pageDomain.getPageNum();
            Integer pageSize = pageDomain.getPageSize();
            ResponseInfo<Page<GoodsSynclog>> resRecored = productDockingFeign.getRecordList(goodsSynclog, page, pageSize);
            return getDataTable(resRecored.getData().getRecords(), resRecored.getData().getTotal());

      }


      /**
       * 导出商品同步列表
       */
      @RequiresPermissions("system:goodsSynclog:export")
      @PostMapping("/export")
      @ResponseBody
      public AjaxResult export(GoodsSynclog goodsSynclog) {
            List<GoodsSynclog> list = goodsSynclogService.selectGoodsSynclogList(goodsSynclog);
            ExcelUtil<GoodsSynclog> util = new ExcelUtil<GoodsSynclog>(GoodsSynclog.class);
            return util.exportExcel(list, "goodsSynclog");
      }

      /**
       * 新增商品同步
       */
      @GetMapping("/add")
      public String add() {
            return prefix + "/add";
      }

      /**
       * 新增保存商品同步
       */
      @RequiresPermissions("system:goodsSynclog:add")
      @Log(title = "商品同步", businessType = BusinessType.INSERT)
      @PostMapping("/add")
      @ResponseBody
      public AjaxResult addSave(GoodsSynclog goodsSynclog) {
            return toAjax(goodsSynclogService.insertGoodsSynclog(goodsSynclog));
      }

      /**
       * 修改商品同步
       */
      @GetMapping("/edit/{id}")
      public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
            GoodsSynclog goodsSynclog = goodsSynclogService.selectGoodsSynclogById(id);
            mmap.put("goodsSynclog", goodsSynclog);
            return prefix + "/edit";
      }

      /**
       * 修改保存商品同步
       */
      @RequiresPermissions("system:goodsSynclog:edit")
      @Log(title = "商品同步", businessType = BusinessType.UPDATE)
      @PostMapping("/edit")
      @ResponseBody
      public AjaxResult editSave(GoodsSynclog goodsSynclog) {
            return toAjax(goodsSynclogService.updateGoodsSynclog(goodsSynclog));
      }

      /**
       * 删除商品同步
       */
      @RequiresPermissions("system:goodsSynclog:remove")
      @Log(title = "商品同步", businessType = BusinessType.DELETE)
      @PostMapping("/remove")
      @ResponseBody
      public AjaxResult remove(String ids) {
            return toAjax(goodsSynclogService.deleteGoodsSynclogByIds(ids));
      }


      /**
       * 商品同步
       */
      @RequiresPermissions("system:goodsSynclog:sync")
      @GetMapping("/sync")
      @ResponseBody
      public ResponseInfo<String> syncData(@RequestParam(required = false) String param) {

            ResponseInfo<String> res = productDockingFeign.getLatest(param);
            String resTime = res.getData();
            if (resTime != null) {
                  try {
                        Date date = dateformat.parse(resTime);

                        long resnum = date.getTime();
                        if (System.currentTimeMillis() - resnum > 3600000) {
                              //调用服务
                              productDockingFeign.syncData(param);
                              return ResponseInfo.success("调用同步服务成功");
                        }
                  } catch (ParseException e) {
                        logger.info("字符串转日期异常");
                        e.printStackTrace();
                  }

            }
            return ResponseInfo.error("每次调用同步服务至少间隔1小时，请稍后重试");
      }

}
