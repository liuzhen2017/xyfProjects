package com.ruoyi.controller.product;

import java.util.List;

import com.ruoyi.domain.GroupNoDTO;
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
import com.ruoyi.domain.ProProperty;
import com.ruoyi.service.IProPropertyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品属性 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proProperty")
public class ProPropertyController extends BaseController
{
    private String prefix = "system/proProperty";
	
	@Autowired
	private IProPropertyService proPropertyService;
	
	@RequiresPermissions("system:proProperty:view")
	@GetMapping()
	public String proProperty()
	{
	    return prefix + "/proProperty";
	}
	
	/**
	 * 查询商品属性列表
	 */
	@RequiresPermissions("system:proProperty:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProProperty proProperty)
	{
		startPage();
        List<ProProperty> list = proPropertyService.selectProPropertyList(proProperty);
		return getDataTable(list);
	}

    @GetMapping("/selectProperty")
    public String selectProperty()
    {
        return "/system/proPropertyValue/selectProperty";
    }
	/**
	 * 导出商品属性列表
	 */
	@RequiresPermissions("system:proProperty:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProProperty proProperty)
    {
    	List<ProProperty> list = proPropertyService.selectProPropertyList(proProperty);
        ExcelUtil<ProProperty> util = new ExcelUtil<ProProperty>(ProProperty.class);
        return util.exportExcel(list, "proProperty");
    }
	
	/**
	 * 新增商品属性
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品属性
	 */
	@RequiresPermissions("system:proProperty:add")
	@Log(title = "商品属性", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProProperty proProperty)
	{		
		return toAjax(proPropertyService.insertProProperty(proProperty));
	}

	/**
	 * 修改商品属性
	 */
	@GetMapping("/edit/{propertyId}")
	public String edit(@PathVariable("propertyId") Long propertyId, ModelMap mmap)
	{
		ProProperty proProperty = proPropertyService.selectProPropertyById(propertyId);
		mmap.put("proProperty", proProperty);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品属性
	 */
	@RequiresPermissions("system:proProperty:edit")
	@Log(title = "商品属性", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProProperty proProperty)
	{		
		return toAjax(proPropertyService.updateProProperty(proProperty));
	}
	
	/**
	 * 删除商品属性
	 */
	@RequiresPermissions("system:proProperty:remove")
	@Log(title = "商品属性", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proPropertyService.deleteProPropertyByIds(ids));
	}

//    /**
//     * 根据商品id查询属性和属性值
//     * @param proId
//     * @return
//     */
//	@GetMapping("/queryGroupNoByProId/{proId}")
//    public List<String> queryGroupNoByProId(@PathVariable("proId") Long proId){
//	    return proPropertyService.queryGroupNoyProId(proId);
//    }






}
