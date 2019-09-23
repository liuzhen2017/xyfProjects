package com.ruoyi.controller.ebank;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.dto.PageDTO;
import com.ruoyi.dto.RProductDTO;
import com.ruoyi.dto.TransferMergePageParamDTO;
import com.ruoyi.dto.TransferMergeResultDTO;
import com.ruoyi.feign.EbankFeign;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.EbankTransferMerge;
import com.ruoyi.service.IEbankTransferMergeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 合并后向用户转账 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-05
 */
@Controller
@RequestMapping("/system/ebankTransferMerge")
public class EbankTransferMergeController extends BaseController {
    private String prefix = "system/ebankTransferMerge";

    @Autowired
    private IEbankTransferMergeService ebankTransferMergeService;

    @Autowired
    private EbankFeign ebankFeign;

    @RequiresPermissions("system:ebankTransferMerge:view")
    @GetMapping()
    public String ebankTransferMerge() {
        return prefix + "/ebankTransferMerge";
    }

    /**
     * 查询合并后向用户转账列表
     */
    @RequiresPermissions("system:ebankTransferMerge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TransferMergePageParamDTO transferMergePageParamDTO) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        transferMergePageParamDTO.setPageNo(page);
        transferMergePageParamDTO.setPageSize(pageSize);
        if (transferMergePageParamDTO.getStartTime() == null) {
            transferMergePageParamDTO.setStartTime(0L);
        }
        if (transferMergePageParamDTO.getEndTime() == null) {
            transferMergePageParamDTO.setEndTime(System.currentTimeMillis());
        }
        com.ruoyi.utils.ResponseInfo<PageDTO<TransferMergeResultDTO>> res = ebankFeign.findEbankTransferMergePage(transferMergePageParamDTO);
        return getDataTable(res.getData().getList(), res.getData().getTotalCount());
    }


    /**
     * 导出合并后向用户转账列表
     */
    @RequiresPermissions("system:ebankTransferMerge:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EbankTransferMerge ebankTransferMerge) {
        List<EbankTransferMerge> list = ebankTransferMergeService.selectEbankTransferMergeList(ebankTransferMerge);
        ExcelUtil<EbankTransferMerge> util = new ExcelUtil<EbankTransferMerge>(EbankTransferMerge.class);
        return util.exportExcel(list, "ebankTransferMerge");
    }

    /**
     * 新增合并后向用户转账
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存合并后向用户转账
     */
    @RequiresPermissions("system:ebankTransferMerge:add")
    @Log(title = "合并后向用户转账", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EbankTransferMerge ebankTransferMerge) {
        return toAjax(ebankTransferMergeService.insertEbankTransferMerge(ebankTransferMerge));
    }

    /**
     * 修改合并后向用户转账
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        EbankTransferMerge ebankTransferMerge = ebankTransferMergeService.selectEbankTransferMergeById(id);
        mmap.put("ebankTransferMerge", ebankTransferMerge);
        return prefix + "/edit";
    }

    /**
     * 修改保存合并后向用户转账
     */
    @RequiresPermissions("system:ebankTransferMerge:edit")
    @Log(title = "合并后向用户转账", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EbankTransferMerge ebankTransferMerge) {
        return toAjax(ebankTransferMergeService.updateEbankTransferMerge(ebankTransferMerge));
    }

    /**
     * 删除合并后向用户转账
     */
    @RequiresPermissions("system:ebankTransferMerge:remove")
    @Log(title = "合并后向用户转账", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ebankTransferMergeService.deleteEbankTransferMergeByIds(ids));
    }

}
