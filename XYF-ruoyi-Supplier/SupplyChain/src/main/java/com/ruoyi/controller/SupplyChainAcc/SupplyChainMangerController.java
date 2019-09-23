package com.ruoyi.controller.SupplyChainAcc;

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
import com.ruoyi.domain.SupplyChainManger;
import com.ruoyi.service.ISupplyChainMangerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 供应链 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Controller
@RequestMapping("/system/supplyChainManger")
public class SupplyChainMangerController extends BaseController
{
    private String prefix = "system/supplyChainManger";
	
	@Autowired
	private ISupplyChainMangerService supplyChainMangerService;
	
	@RequiresPermissions("system:supplyChainManger:view")
	@GetMapping()
	public String supplyChainManger()
	{
	    return prefix + "/supplyChainManger";
	}
	
	/**
	 * 查询供应链列表
	 */
	@RequiresPermissions("system:supplyChainManger:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SupplyChainManger supplyChainManger)
	{
		startPage();
        List<SupplyChainManger> list = supplyChainMangerService.selectSupplyChainMangerList(supplyChainManger);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出供应链列表
	 */
	@RequiresPermissions("system:supplyChainManger:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SupplyChainManger supplyChainManger)
    {
    	List<SupplyChainManger> list = supplyChainMangerService.selectSupplyChainMangerList(supplyChainManger);
        ExcelUtil<SupplyChainManger> util = new ExcelUtil<SupplyChainManger>(SupplyChainManger.class);
        return util.exportExcel(list, "supplyChainManger");
    }
	
	/**
	 * 新增供应链
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存供应链
	 */
	@RequiresPermissions("system:supplyChainManger:add")
	@Log(title = "供应链", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SupplyChainManger supplyChainManger)
	{		
		return toAjax(supplyChainMangerService.insertSupplyChainManger(supplyChainManger));
	}

	/**
	 * 修改供应链
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		SupplyChainManger supplyChainManger = supplyChainMangerService.selectSupplyChainMangerById(id);
		mmap.put("supplyChainManger", supplyChainManger);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存供应链
	 */
	@RequiresPermissions("system:supplyChainManger:edit")
	@Log(title = "供应链", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SupplyChainManger supplyChainManger)
	{		
		return toAjax(supplyChainMangerService.updateSupplyChainManger(supplyChainManger));
	}
	
	/**
	 * 删除供应链
	 */
	@RequiresPermissions("system:supplyChainManger:remove")
	@Log(title = "供应链", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(supplyChainMangerService.deleteSupplyChainMangerByIds(ids));
	}
	
}
