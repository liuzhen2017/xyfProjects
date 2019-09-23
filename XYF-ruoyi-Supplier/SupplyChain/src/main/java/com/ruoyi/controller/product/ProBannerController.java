package com.ruoyi.controller.product;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.domain.ProBanner;
import com.ruoyi.domain.Product;
import com.ruoyi.dto.BannerDTO;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.service.IProBannerService;
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
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 广告 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proBanner")
public class ProBannerController extends BaseController
{
    private String prefix = "system/proBanner";
	
	@Autowired
	private IProBannerService proBannerService;

	@Autowired
    private ProductManageFeign productManageFeign;
	
	@RequiresPermissions("system:proBanner:view")
	@GetMapping()
	public String proBanner()
	{
	    return prefix + "/proBanner";
	}
	
	/**
	 * 查询广告列表
	 */
	@RequiresPermissions("system:proBanner:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProBanner proBanner)
	{
        String custNo=ShiroUtils.getSysUser().getCustNo();
//        Long storeId=productManageFeign.findStoreIdByOwnerId(custNo).getData();
        logger.info("==================custNo={}===================",custNo);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        com.ruoyi.utils.ResponseInfo<Page<ProBanner>> res = productManageFeign.findBannerByPage(proBanner, page, pageSize);
//        res.getData().getRecords().forEach( b -> {
//            if( null != b){
//                b.setStartDate(b.getStartDate().split("T")[0]);
//                b.setEndDate(b.getEndDate().split("T")[0]);
//            }
//        });
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
	}
	
	
	/**
	 * 导出广告列表
	 */
	@RequiresPermissions("system:proBanner:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProBanner proBanner)
    {
    	List<ProBanner> list = proBannerService.selectProBannerList(proBanner);
        ExcelUtil<ProBanner> util = new ExcelUtil<ProBanner>(ProBanner.class);
        return util.exportExcel(list, "proBanner");
    }
	
	/**
	 * 新增广告
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存广告
	 */
	@RequiresPermissions("system:proBanner:add")
	@Log(title = "广告", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(BannerDTO bannerDTO)
	{
		return toAjax(productManageFeign.saveBanner(bannerDTO).getCode());
	}

	/**
	 * 修改广告
	 */
	@GetMapping("/edit/{bannerId}")
	public String edit(@PathVariable("bannerId") Long bannerId, ModelMap mmap)
	{
		ProBanner proBanner = productManageFeign.selectProBannerById(bannerId).getData();
		mmap.put("proBanner", proBanner);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存广告
	 */
	@RequiresPermissions("system:proBanner:edit")
	@Log(title = "广告", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProBanner proBanner)
	{		
		return toAjax(productManageFeign.updateBanner(proBanner).getCode());
	}
	
	/**
	 * 删除广告
	 */
	@RequiresPermissions("system:proBanner:remove")
	@Log(title = "广告", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(productManageFeign.deleteProBanner(ids).getCode());
	}
	
}
