package com.ruoyi.controller.product;

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
import com.ruoyi.domain.ProPackage;
import com.ruoyi.service.IProPackageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 套餐 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proPackage")
public class ProPackageController extends BaseController
{
    private String prefix = "system/proPackage";
	
	@Autowired
	private IProPackageService proPackageService;
	
	@RequiresPermissions("system:proPackage:view")
	@GetMapping()
	public String proPackage()
	{
	    return prefix + "/proPackage";
	}
	
	/**
	 * 查询套餐列表
	 */
	@RequiresPermissions("system:proPackage:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProPackage proPackage)
	{
		startPage();
        List<ProPackage> list = proPackageService.selectProPackageList(proPackage);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出套餐列表
	 */
	@RequiresPermissions("system:proPackage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProPackage proPackage)
    {
    	List<ProPackage> list = proPackageService.selectProPackageList(proPackage);
        ExcelUtil<ProPackage> util = new ExcelUtil<ProPackage>(ProPackage.class);
        return util.exportExcel(list, "proPackage");
    }
	
	/**
	 * 新增套餐
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存套餐
	 */
	@RequiresPermissions("system:proPackage:add")
	@Log(title = "套餐", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProPackage proPackage)
	{		
		return toAjax(proPackageService.insertProPackage(proPackage));
	}

	/**
	 * 修改套餐
	 */
	@GetMapping("/edit/{packageId}")
	public String edit(@PathVariable("packageId") Long packageId, ModelMap mmap)
	{
		ProPackage proPackage = proPackageService.selectProPackageById(packageId);
		mmap.put("proPackage", proPackage);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存套餐
	 */
	@RequiresPermissions("system:proPackage:edit")
	@Log(title = "套餐", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProPackage proPackage)
	{		
		return toAjax(proPackageService.updateProPackage(proPackage));
	}
	
	/**
	 * 删除套餐
	 */
	@RequiresPermissions("system:proPackage:remove")
	@Log(title = "套餐", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proPackageService.deleteProPackageByIds(ids));
	}
	
}
