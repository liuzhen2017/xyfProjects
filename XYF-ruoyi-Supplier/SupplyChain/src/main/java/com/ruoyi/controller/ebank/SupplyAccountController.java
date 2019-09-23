package com.ruoyi.controller.ebank;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.AccountAddDTO;
import com.ruoyi.domain.EbankAccount;
import com.ruoyi.feign.EbankFeign;
import com.ruoyi.feign.ProductDockingFeign;
import com.ruoyi.service.IEbankAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.utils.ResponseInfo;

import java.util.List;

/**
 * 每个转账账户的虚拟账号 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/system/SupplyAccount")
public class SupplyAccountController extends BaseController {
      private String prefix = "system/SupplyAccount";

      @Autowired
      private IEbankAccountService ebankAccountService;

      @Autowired
      private EbankFeign ebankFeign;

      @Autowired
      private ProductDockingFeign productDockingFeign;

      @RequiresPermissions("system:ebankAccount:view")
      @GetMapping()
      public String ebankAccount() {
            return prefix + "/ebankAccount";
      }


      @GetMapping("selectSupplyChain")
      public String selectSupplyChain() {
            return prefix + "/selectSupplyChain";
      }


      /**
       * 查询每个转账账户的虚拟账号列表
       */
      @RequiresPermissions("system:ebankAccount:list")
      @PostMapping("/list")
      @ResponseBody
      public TableDataInfo list(EbankAccount ebankAccount) {
            startPage();
            List<EbankAccount> list = ebankAccountService.selectEbankAccountList(ebankAccount);
            return getDataTable(list);
      }


      /**
       * 导出每个转账账户的虚拟账号列表
       */
      @RequiresPermissions("system:ebankAccount:export")
      @PostMapping("/export")
      @ResponseBody
      public AjaxResult export(EbankAccount ebankAccount) {
            List<EbankAccount> list = ebankAccountService.selectEbankAccountList(ebankAccount);
            ExcelUtil<EbankAccount> util = new ExcelUtil<EbankAccount>(EbankAccount.class);
            return util.exportExcel(list, "ebankAccount");
      }

      /**
       * 新增每个转账账户的虚拟账号
       */
      @GetMapping("/add")
      public String add() {
            return prefix + "/add";
      }

      /**
       * 新增保存每个转账账户的虚拟账号
       */
      @RequiresPermissions("system:ebankAccount:add")
      @Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.INSERT)
      @PostMapping("/add")
      @ResponseBody
      public AjaxResult addSave(AccountAddDTO accountAdd) {
            ResponseInfo result = ebankFeign.addAccount(accountAdd);
            return toAjax(result.getCode());
      }

      /**
       * 修改每个转账账户的虚拟账号
       */
      @GetMapping("/edit/{id}")
      public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
            EbankAccount ebankAccount = ebankAccountService.selectEbankAccountById(id);
            mmap.put("ebankAccount", ebankAccount);
            return prefix + "/edit";
      }

      /**
       * 修改保存每个转账账户的虚拟账号
       */
      @RequiresPermissions("system:ebankAccount:edit")
      @Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.UPDATE)
      @PostMapping("/edit")
      @ResponseBody
      public AjaxResult editSave(EbankAccount ebankAccount) {
            return toAjax(ebankAccountService.updateEbankAccount(ebankAccount));
      }

      /**
       * 删除每个转账账户的虚拟账号
       */
      @RequiresPermissions("system:ebankAccount:remove")
      @Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.DELETE)
      @PostMapping("/remove")
      @ResponseBody
      public AjaxResult remove(String ids) {
            return toAjax(ebankAccountService.deleteEbankAccountByIds(ids));
      }

}
