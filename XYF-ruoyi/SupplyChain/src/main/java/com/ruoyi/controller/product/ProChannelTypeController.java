package com.ruoyi.controller.product;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.utils.ResponseInfo;
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
import com.ruoyi.domain.ProChannelType;
import com.ruoyi.service.IProChannelTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品类型分类 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-30
 */
@Controller
@RequestMapping("/system/proChannelType")
public class ProChannelTypeController extends BaseController
{
	@Autowired
	private ProductManageFeign productManageFeign;
	
	@RequiresPermissions("system:proChannelType:view")
	@GetMapping()
	public String proChannelType() {
	    return "system/proChannelType/proChannelType";
	}

    @GetMapping("/add")
    public String add() {
        return "system/proChannelType/add";
    }
	
	/**
	 * 查询商品类型分类列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProChannelType proChannelType) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<ProChannelType> data = productManageFeign.findProductByPage(proChannelType, page, pageSize).getData();
        return getDataTable(data.getRecords(),data.getTotal());
	}

	/**
	 * 新增保存商品类型分类
	 */
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProChannelType proChannelType) {
        ResponseInfo<String> res = productManageFeign.saveProduct(proChannelType);
        if(res.isSuccess()){
            return AjaxResult.success("新增成功！");
        }
        return AjaxResult.error("新增失败！请联系后台管理员！");
	}

	/**
	 * 修改商品类型分类
	 */
	@GetMapping("/edit/{channelTypeId}")
	public String edit(@PathVariable("channelTypeId") Long channelTypeId, ModelMap mmap){
        ResponseInfo<ProChannelType> res = productManageFeign.findById(channelTypeId);
        mmap.put("proChannelType", res.getData());
	    return "system/proChannelType/edit";
	}
	
	/**
	 * 修改保存商品类型分类
	 */
	@ResponseBody
	@PostMapping("/edit")
	public AjaxResult editSave(ProChannelType proChannelType) {
        ResponseInfo<String> res = productManageFeign.updateProduct(proChannelType);
        if(res.isSuccess()){
            return AjaxResult.success("修改成功！");
        }
        return AjaxResult.error("修改失败！请联系后台管理员！");
	}

	
}
