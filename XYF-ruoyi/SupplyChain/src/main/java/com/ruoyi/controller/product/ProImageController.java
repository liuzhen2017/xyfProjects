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
import com.ruoyi.domain.ProImage;
import com.ruoyi.service.IProImageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品图片 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
@Controller
@RequestMapping("/system/proImage")
public class ProImageController extends BaseController
{
    private String prefix = "system/proImage";
	
	@Autowired
	private IProImageService proImageService;
	
	@RequiresPermissions("system:proImage:view")
	@GetMapping()
	public String proImage()
	{
	    return prefix + "/proImage";
	}
	
	/**
	 * 查询商品图片列表
	 */
	@RequiresPermissions("system:proImage:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProImage proImage)
	{
		startPage();
        List<ProImage> list = proImageService.selectProImageList(proImage);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品图片列表
	 */
	@RequiresPermissions("system:proImage:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProImage proImage)
    {
    	List<ProImage> list = proImageService.selectProImageList(proImage);
        ExcelUtil<ProImage> util = new ExcelUtil<ProImage>(ProImage.class);
        return util.exportExcel(list, "proImage");
    }
	
	/**
	 * 新增商品图片
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品图片
	 */
	@RequiresPermissions("system:proImage:add")
	@Log(title = "商品图片", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProImage proImage)
	{		
		return toAjax(proImageService.insertProImage(proImage));
	}

	/**
	 * 修改商品图片
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ProImage proImage = proImageService.selectProImageById(id);
		mmap.put("proImage", proImage);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品图片
	 */
	@RequiresPermissions("system:proImage:edit")
	@Log(title = "商品图片", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProImage proImage)
	{		
		return toAjax(proImageService.updateProImage(proImage));
	}
	
	/**
	 * 删除商品图片
	 */
	@RequiresPermissions("system:proImage:remove")
	@Log(title = "商品图片", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proImageService.deleteProImageByIds(ids));
	}
	
}
