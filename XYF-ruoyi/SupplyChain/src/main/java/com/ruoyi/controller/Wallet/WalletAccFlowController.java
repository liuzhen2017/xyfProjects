package com.ruoyi.controller.Wallet;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.domain.SysUser;
import com.ruoyi.domain.UserWallet;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.feign.WalletAcctManageFeign;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.service.ISysUserService;
import com.ruoyi.utils.ResponseInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.WalletAccFlow;
import com.ruoyi.service.IWalletAccFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 每笔转账的转入转出记录 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-20
 */
@Controller
@RequestMapping("/system/walletAccFlow")
public class WalletAccFlowController extends BaseController
{
    private String prefix = "system/walletAccFlow";

    @Autowired
    private IWalletAccFlowService walletAccFlowService;
    @Autowired
    private WalletAcctManageFeign walletAcctManageFeign;



    @RequiresPermissions("system:walletAccFlow:view")
    @GetMapping()
    public String walletAccFlow(Model model)
    {
        ResponseInfo<UserWallet> system = walletAcctManageFeign.getAccountInfo("system");
        ResponseInfo<UserWallet> inner = walletAcctManageFeign.getAccountInfo("inner");
        model.addAttribute("system",system.getData());
        model.addAttribute("inner",inner.getData());
        return prefix + "/walletAccFlow";

    }

    /**
     *  处理提现
     * @param id
     * @param type
     * @return
     */
    @ResponseBody
    @GetMapping("/withdrawApproval")
    public Object withdrawApproval(@RequestParam("id") String id,
                                   @RequestParam("type") Integer type,
                                   @RequestParam("message") String message){
        return walletAcctManageFeign.withdrawApproval(id, type,message);
    }



    /**
     * 查询每笔转账的转入转出记录列表
     */
    @RequiresPermissions("system:walletAccFlow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WalletAccFlow walletAccFlow)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        walletAccFlow.setShowDate(walletAccFlow.getCreateTime()+";"+walletAccFlow.getLastModifyTime());
        walletAccFlow.setCreateTime(null);
        walletAccFlow.setLastModifyTime(null);
        com.ruoyi.utils.ResponseInfo<Page<WalletAccFlow>> res = walletAcctManageFeign.getWalletAccFlowPage(walletAccFlow, page, pageSize);
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
    }


    /**
     * 导出每笔转账的转入转出记录列表
     */
    @RequiresPermissions("system:walletAccFlow:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WalletAccFlow walletAccFlow)
    {
        walletAccFlow.setShowDate(walletAccFlow.getCreateTime()+";"+walletAccFlow.getLastModifyTime());
        walletAccFlow.setCreateTime(null);
        walletAccFlow.setLastModifyTime(null);
        com.ruoyi.utils.ResponseInfo<Page<WalletAccFlow>> res = walletAcctManageFeign.getWalletAccFlowPage(walletAccFlow, 1, 10000);
        ExcelUtil<WalletAccFlow> util = new ExcelUtil<WalletAccFlow>(WalletAccFlow.class);
        return util.exportExcel(res.getData().getRecords(), "流水记录");
    }



}
