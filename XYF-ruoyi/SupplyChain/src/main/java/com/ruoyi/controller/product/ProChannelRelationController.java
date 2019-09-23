package com.ruoyi.controller.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.ProChannelRelation;
import com.ruoyi.dto.ProChannelRelationDTO;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.service.IProChannelRelationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类关系 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proChannelRelation")
public class ProChannelRelationController extends BaseController
{
    private String prefix = "system/proChannelRelation";
	
	@Autowired
	private IProChannelRelationService proChannelRelationService;
	@Autowired
    private ProductManageFeign productManageFeign;

	@RequiresPermissions("system:proChannelRelation:view")
	@GetMapping()
	public String proChannelRelation()
	{
	    return prefix + "/proChannelRelation";
	}
	
	/**
	 * 查询商品分类关系列表
	 */
	@RequiresPermissions("system:proChannelRelation:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProChannelRelation proChannelRelation)
	{
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        com.ruoyi.utils.ResponseInfo<Page<ProChannelRelation>> res = productManageFeign.findProChannelRelationByPage(proChannelRelation,page, pageSize);
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
	}
	
	
	/**
	 * 导出商品分类关系列表
	 */
	@RequiresPermissions("system:proChannelRelation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProChannelRelation proChannelRelation)
    {
    	List<ProChannelRelation> list = proChannelRelationService.selectProChannelRelationList(proChannelRelation);
        ExcelUtil<ProChannelRelation> util = new ExcelUtil<ProChannelRelation>(ProChannelRelation.class);
        return util.exportExcel(list, "proChannelRelation");
    }
	
	/**
	 * 新增商品分类关系
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品分类关系
	 */
	@RequiresPermissions("system:proChannelRelation:add")
	@Log(title = "商品分类关系", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProChannelRelationDTO proChannelRelationDTO)
	{		
		return toAjax(productManageFeign.saveProChannelRelations(proChannelRelationDTO).getCode());
	}

	/**
	 * 修改商品分类关系
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ProChannelRelation proChannelRelation = productManageFeign.selectProChannelRelationById(id).getData();
		mmap.put("proChannelRelation", proChannelRelation);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品分类关系
	 */
	@RequiresPermissions("system:proChannelRelation:edit")
	@Log(title = "商品分类关系", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProChannelRelation proChannelRelation)
	{		
		return toAjax(productManageFeign.updateProchannelRelation(proChannelRelation).isSuccess());
	}
	
	/**
	 * 删除商品分类关系
	 */
	@RequiresPermissions("system:proChannelRelation:remove")
	@Log(title = "商品分类关系", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
	    return toAjax(productManageFeign.deleteProChannelRelation(ids).getCode());
	}
	
}
