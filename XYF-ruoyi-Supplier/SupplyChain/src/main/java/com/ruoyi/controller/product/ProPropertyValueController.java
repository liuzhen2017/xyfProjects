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
import com.ruoyi.domain.ProPropertyValue;
import com.ruoyi.service.IProPropertyValueService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品属性值 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proPropertyValue")
public class ProPropertyValueController extends BaseController
{
    private String prefix = "system/proPropertyValue";
	
	@Autowired
	private IProPropertyValueService proPropertyValueService;
	
	@RequiresPermissions("system:proPropertyValue:view")
	@GetMapping()
	public String proPropertyValue()
	{
	    return prefix + "/proPropertyValue";
	}
	
	/**
	 * 查询商品属性值列表
	 */
	@RequiresPermissions("system:proPropertyValue:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProPropertyValue proPropertyValue)
	{
		startPage();
        List<ProPropertyValue> list = proPropertyValueService.selectProPropertyValueList(proPropertyValue);
		return getDataTable(list);
	}


	/**
	 * 导出商品属性值列表
	 */
	@RequiresPermissions("system:proPropertyValue:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProPropertyValue proPropertyValue)
    {
    	List<ProPropertyValue> list = proPropertyValueService.selectProPropertyValueList(proPropertyValue);
        ExcelUtil<ProPropertyValue> util = new ExcelUtil<ProPropertyValue>(ProPropertyValue.class);
        return util.exportExcel(list, "proPropertyValue");
    }
	
	/**
	 * 新增商品属性值
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品属性值
	 */
	@RequiresPermissions("system:proPropertyValue:add")
	@Log(title = "商品属性值", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProPropertyValue proPropertyValue)
	{		
		return toAjax(proPropertyValueService.insertProPropertyValue(proPropertyValue));
	}

	/**
	 * 修改商品属性值
	 */
	@GetMapping("/edit/{valueId}")
	public String edit(@PathVariable("valueId") Long valueId, ModelMap mmap)
	{
		ProPropertyValue proPropertyValue = proPropertyValueService.selectProPropertyValueById(valueId);
		mmap.put("proPropertyValue", proPropertyValue);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品属性值
	 */
	@RequiresPermissions("system:proPropertyValue:edit")
	@Log(title = "商品属性值", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProPropertyValue proPropertyValue)
	{		
		return toAjax(proPropertyValueService.updateProPropertyValue(proPropertyValue));
	}
	
	/**
	 * 删除商品属性值
	 */
	@RequiresPermissions("system:proPropertyValue:remove")
	@Log(title = "商品属性值", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proPropertyValueService.deleteProPropertyValueByIds(ids));
	}
	
}
