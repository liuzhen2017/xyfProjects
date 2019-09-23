package com.ruoyi.controller.product;

import java.util.List;

import com.ruoyi.feign.ProductManageFeign;
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
import com.ruoyi.domain.ProPayType;
import com.ruoyi.service.IProPayTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品支付类型关联 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proPayType")
public class ProPayTypeController extends BaseController
{
    private String prefix = "system/proPayType";
	
	@Autowired
	private IProPayTypeService proPayTypeService;
	@Autowired
    private ProductManageFeign productManageFeign;
	
	@RequiresPermissions("system:proPayType:view")
	@GetMapping()
	public String proPayType()
	{
	    return prefix + "/proPayType";
	}
	
	/**
	 * 查询商品支付类型关联列表
	 */
	@RequiresPermissions("system:proPayType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProPayType proPayType)
	{
		startPage();
        List<ProPayType> list = proPayTypeService.selectProPayTypeList(proPayType);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品支付类型关联列表
	 */
	@RequiresPermissions("system:proPayType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProPayType proPayType)
    {
    	List<ProPayType> list = proPayTypeService.selectProPayTypeList(proPayType);
        ExcelUtil<ProPayType> util = new ExcelUtil<ProPayType>(ProPayType.class);
        return util.exportExcel(list, "proPayType");
    }
	
	/**
	 * 新增商品支付类型关联
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品支付类型关联
	 */
	@RequiresPermissions("system:proPayType:add")
	@Log(title = "商品支付类型关联", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProPayType proPayType)
	{		
	    return toAjax(productManageFeign.saveProPayType(proPayType).getCode());
	}

	/**
	 * 修改商品支付类型关联
	 */
	@GetMapping("/edit/{proId}")
	public String edit(@PathVariable("proId") Long proId, ModelMap mmap)
	{
		ProPayType proPayType = proPayTypeService.selectProPayTypeById(proId);
		mmap.put("proPayType", proPayType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品支付类型关联
	 */
	@RequiresPermissions("system:proPayType:edit")
	@Log(title = "商品支付类型关联", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProPayType proPayType)
	{		
		return toAjax(proPayTypeService.updateProPayType(proPayType));
	}
	
	/**
	 * 删除商品支付类型关联
	 */
	@RequiresPermissions("system:proPayType:remove")
	@Log(title = "商品支付类型关联", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proPayTypeService.deleteProPayTypeByIds(ids));
	}
	
}
