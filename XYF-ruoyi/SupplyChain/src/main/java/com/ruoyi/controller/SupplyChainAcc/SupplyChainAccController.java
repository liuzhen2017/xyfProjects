package com.ruoyi.controller.SupplyChainAcc;

import java.util.List;

import com.ruoyi.service.ISupplyChainAccService;
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
import com.ruoyi.domain.SupplyChainAcc;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 供应链账户 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Controller
@RequestMapping("/system/supplyChainAcc")
public class SupplyChainAccController extends BaseController
{
    private String prefix = "system/supplyChainAcc";
	
	@Autowired
	private ISupplyChainAccService supplyChainAccService;
	
	@RequiresPermissions("system:supplyChainAcc:view")
	@GetMapping()
	public String supplyChainAcc()
	{
	    return prefix + "/supplyChainAcc";
	}
	
	/**
	 * 查询供应链账户列表
	 */
	@RequiresPermissions("system:supplyChainAcc:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SupplyChainAcc supplyChainAcc)
	{
		startPage();
        List<SupplyChainAcc> list = supplyChainAccService.selectSupplyChainAccList(supplyChainAcc);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出供应链账户列表
	 */
	@RequiresPermissions("system:supplyChainAcc:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SupplyChainAcc supplyChainAcc)
    {
    	List<SupplyChainAcc> list = supplyChainAccService.selectSupplyChainAccList(supplyChainAcc);
        ExcelUtil<SupplyChainAcc> util = new ExcelUtil<SupplyChainAcc>(SupplyChainAcc.class);
        return util.exportExcel(list, "supplyChainAcc");
    }
	
	/**
	 * 新增供应链账户
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存供应链账户
	 */
	@RequiresPermissions("system:supplyChainAcc:add")
	@Log(title = "供应链账户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SupplyChainAcc supplyChainAcc)
	{		
		return toAjax(supplyChainAccService.insertSupplyChainAcc(supplyChainAcc));
	}

	/**
	 * 修改供应链账户
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		SupplyChainAcc supplyChainAcc = supplyChainAccService.selectSupplyChainAccById(id);
		mmap.put("supplyChainAcc", supplyChainAcc);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存供应链账户
	 */
	@RequiresPermissions("system:supplyChainAcc:edit")
	@Log(title = "供应链账户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SupplyChainAcc supplyChainAcc)
	{		
		return toAjax(supplyChainAccService.updateSupplyChainAcc(supplyChainAcc));
	}
	
	/**
	 * 删除供应链账户
	 */
	@RequiresPermissions("system:supplyChainAcc:remove")
	@Log(title = "供应链账户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(supplyChainAccService.deleteSupplyChainAccByIds(ids));
	}
	
}
