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
import com.ruoyi.domain.ProSku;
import com.ruoyi.service.IProSkuService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * sku——商品库存量单位 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proSku")
public class ProSkuController extends BaseController
{
    private String prefix = "system/proSku";
	
	@Autowired
	private IProSkuService proSkuService;
	@Autowired
    private ProductManageFeign productManageFeign;
	
	@RequiresPermissions("system:proSku:view")
	@GetMapping()
	public String proSku()
	{
	    return prefix + "/proSku";
	}
    @GetMapping("/selectPropertyValue")
    public String selectPropertyValue()
    {
        return prefix + "/selectPropertyValue";
    }
	/**
	 * 查询sku——商品库存量单位列表
	 */
	@RequiresPermissions("system:proSku:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProSku proSku)
	{
		startPage();
        List<ProSku> list = proSkuService.selectProSkuList(proSku);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出sku——商品库存量单位列表
	 */
	@RequiresPermissions("system:proSku:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProSku proSku)
    {
    	List<ProSku> list = proSkuService.selectProSkuList(proSku);
        ExcelUtil<ProSku> util = new ExcelUtil<ProSku>(ProSku.class);
        return util.exportExcel(list, "proSku");
    }
	
	/**
	 * 新增sku——商品库存量单位
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存sku——商品库存量单位
	 */
//	@RequiresPermissions("system:proSku:add")
//	@Log(title = "sku——商品库存量单位", businessType = BusinessType.INSERT)
//	@PostMapping("/add")
//	@ResponseBody
//	public AjaxResult addSave(ProSku proSku)
//	{
//		return toAjax(productManageFeign.saveProSku(proSku).getCode());
//	}

	/**
	 * 修改sku——商品库存量单位
	 */
	@GetMapping("/edit/{skuId}")
	public String edit(@PathVariable("skuId") Long skuId, ModelMap mmap)
	{
		ProSku proSku = productManageFeign.selectProSkuById(skuId);
		mmap.put("proSku", proSku);
	    return prefix + "/edit";
	}
//
//	/**
//	 * 修改保存sku——商品库存量单位
//	 */
//	@RequiresPermissions("system:proSku:edit")
//	@Log(title = "sku——商品库存量单位", businessType = BusinessType.UPDATE)
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(ProSku proSku)
//	{
//		return toAjax(proSkuService.updateProSku(proSku));
//	}
	
	/**
	 * 删除sku——商品库存量单位
	 */
	@RequiresPermissions("system:proSku:remove")
	@Log(title = "sku——商品库存量单位", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(Long[] ids)
	{		
	    return toAjax(productManageFeign.deleteProSku(ids).getCode());
	}
	
}
