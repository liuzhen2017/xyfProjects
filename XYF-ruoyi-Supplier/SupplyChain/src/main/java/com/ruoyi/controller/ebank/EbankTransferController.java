package com.ruoyi.controller.ebank;

import java.util.List;
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
import com.ruoyi.domain.EbankTransfer;
import com.ruoyi.service.IEbankTransferService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 向用户转账 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Controller
@RequestMapping("/ruoyi/ebankTransfer")
public class EbankTransferController extends BaseController
{
    private String prefix = "ruoyi/ebankTransfer";
	
	@Autowired
	private IEbankTransferService ebankTransferService;
	
	@RequiresPermissions("ruoyi:ebankTransfer:view")
	@GetMapping()
	public String ebankTransfer()
	{
	    return prefix + "/ebankTransfer";
	}
	
	/**
	 * 查询向用户转账列表
	 */
	@RequiresPermissions("ruoyi:ebankTransfer:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EbankTransfer ebankTransfer)
	{
		startPage();
        List<EbankTransfer> list = ebankTransferService.selectEbankTransferList(ebankTransfer);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出向用户转账列表
	 */
	@RequiresPermissions("ruoyi:ebankTransfer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EbankTransfer ebankTransfer)
    {
    	List<EbankTransfer> list = ebankTransferService.selectEbankTransferList(ebankTransfer);
        ExcelUtil<EbankTransfer> util = new ExcelUtil<EbankTransfer>(EbankTransfer.class);
        return util.exportExcel(list, "ebankTransfer");
    }
	
	/**
	 * 新增向用户转账
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存向用户转账
	 */
	@RequiresPermissions("ruoyi:ebankTransfer:add")
	@Log(title = "向用户转账", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(EbankTransfer ebankTransfer)
	{		
		return toAjax(ebankTransferService.insertEbankTransfer(ebankTransfer));
	}

	/**
	 * 修改向用户转账
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		EbankTransfer ebankTransfer = ebankTransferService.selectEbankTransferById(id);
		mmap.put("ebankTransfer", ebankTransfer);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存向用户转账
	 */
	@RequiresPermissions("ruoyi:ebankTransfer:edit")
	@Log(title = "向用户转账", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(EbankTransfer ebankTransfer)
	{		
		return toAjax(ebankTransferService.updateEbankTransfer(ebankTransfer));
	}
	
	/**
	 * 删除向用户转账
	 */
	@RequiresPermissions("ruoyi:ebankTransfer:remove")
	@Log(title = "向用户转账", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ebankTransferService.deleteEbankTransferByIds(ids));
	}
	
}
