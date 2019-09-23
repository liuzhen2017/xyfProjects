package com.ruoyi.controller.product;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.domain.Product;
import com.ruoyi.domain.SysDept;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.util.Res;
import com.ruoyi.util.ResUitls;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.ProChannel;
import com.ruoyi.service.IProChannelService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 类目 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proChannel")
public class ProChannelController extends BaseController
{
    private String prefix = "system/proChannel";
	

	@Autowired
    private ProductManageFeign productManageFeign;
	@RequiresPermissions("system:proChannel:view")
	@GetMapping()
	public String proChannel()
	{
	    return prefix + "/proChannel";
	}
	
	/**
	 * 查询类目列表
	 */
	@RequiresPermissions("system:proChannel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProChannel proChannel)
	{
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        com.ruoyi.utils.ResponseInfo<Page<ProChannel>> res = productManageFeign.findProChannelByPage(proChannel,page, pageSize);
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
	}
	
	
	/**
	 * 导出类目列表
	 */
	@RequiresPermissions("system:proChannel:export")
    @PostMapping("/export")
    @ResponseBody
//    public AjaxResult export(ProChannel proChannel)
//    {
//    	List<ProChannel> list = proChannelService.selectProChannelList(proChannel);
//        ExcelUtil<ProChannel> util = new ExcelUtil<ProChannel>(ProChannel.class);
//        return util.exportExcel(list, "proChannel");
//    }
	
	/**
	 * 新增类目
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存类目
	 */
	@RequiresPermissions("system:proChannel:add")
	@Log(title = "类目", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProChannel proChannel)
	{		
		return toAjax(productManageFeign.saveProChannel(proChannel).getCode());
	}

	/**
	 * 修改类目
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		mmap.put("proChannel",productManageFeign.selectProChannelById(id).getData());
        System.out.println(" json ="+mmap.get("proChannel"));
		return prefix + "/edit";
	}
	
	/**
	 * 修改保存类目
	 */
	@RequiresPermissions("system:proChannel:edit")
	@Log(title = "类目", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProChannel proChannel)
	{
        System.out.println(proChannel.toString());
        com.ruoyi.utils.ResponseInfo<String> responseInfo = productManageFeign.updateProChannel(proChannel);
        if(responseInfo.isSuccess()){
            return success();
        }
        return error(responseInfo.getMessage());

	}
	
	/**
	 * 删除类目
	 */
	@RequiresPermissions("system:proChannel:remove")
	@Log(title = "类目", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(productManageFeign.deleteProChannel(ids).getCode());
	}

    /**
     * 选择分类树
     */
    @GetMapping("/selectChannelTree/{channelId}")
    public String selectChannelTree(@PathVariable("channelId") Long channleId, Model model)
    {
        model.addAttribute("proChannel",productManageFeign.selectProChannelByChannelId(channleId).getData());
        return  "system/proChannelRelation/tree";
    }

    /**
     * 加载分类列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = productManageFeign.selectProChannelTree(new ProChannel()).getData();
        return ztrees;
    }




	
}
