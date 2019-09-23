package com.ruoyi.controller.CouponManger;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.feign.VolumeMarketFeign;
import com.ruoyi.util.Res;
import com.ruoyi.util.ResUitls;
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
import com.ruoyi.domain.CouponManger;
import com.ruoyi.service.ICouponMangerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 优惠券 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
@Controller
@RequestMapping("/system/couponManger")
public class CouponMangerController extends BaseController
{
    private String prefix = "system/couponManger";
	
	@Autowired
	private ICouponMangerService couponMangerService;
	@Autowired
	VolumeMarketFeign volumeMarketFeign;
	@RequiresPermissions("system:couponManger:view")
	@GetMapping()
	public String couponManger()
	{
	    return prefix + "/couponManger";
	}
	@GetMapping("/selectCouponManger")
	public String selectCouponManger()
	{
		return prefix + "/selectCouponManger";
	}

	
	/**
	 * 查询优惠券列表
	 */
	@RequiresPermissions("system:couponManger:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CouponManger couponManger)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer page = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		JSONObject jb =JSONObject.parseObject(JSONObject.toJSONString(couponManger));
		jb.put("page",page);
		jb.put("pageSize",pageSize);
		com.ruoyi.utils.ResponseInfo<Object> result  =volumeMarketFeign.queryLists(JSONObject.toJSONString(jb));
		Res<CouponManger> res= ResUitls.getRes(result.getData(),CouponManger.class);
		return getDataTable(res.getRecords(),Long.parseLong(res.getTotal()+""));
	}
	
	
	/**
	 * 导出优惠券列表
	 */
	@RequiresPermissions("system:couponManger:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CouponManger couponManger)
    {
    	List<CouponManger> list = couponMangerService.selectCouponMangerList(couponManger);
        ExcelUtil<CouponManger> util = new ExcelUtil<CouponManger>(CouponManger.class);
        return util.exportExcel(list, "couponManger");
    }
	
	/**
	 * 新增优惠券
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存优惠券
	 */
	@RequiresPermissions("system:couponManger:add")
	@Log(title = "优惠券", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CouponManger couponManger)
	{
		com.ruoyi.utils.ResponseInfo<Boolean> responseInfo =volumeMarketFeign.add(couponManger);
		if (responseInfo.isSuccess()) {
			return success();
		}
		return error(responseInfo.getMessage());
	}

	/**
	 * 修改优惠券
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		com.ruoyi.utils.ResponseInfo responseInfo = volumeMarketFeign.queryById(id);
		mmap.put("couponManger", responseInfo.getData());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存优惠券
	 */
	@RequiresPermissions("system:couponManger:edit")
	@Log(title = "优惠券", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CouponManger couponManger)
	{
		com.ruoyi.utils.ResponseInfo<Boolean>  responseInfo = volumeMarketFeign.update(couponManger);
		if(responseInfo.isSuccess()){
		  return 	success();
		}
		return error(responseInfo.getMessage());

	}
	
	/**
	 * 删除优惠券
	 */
	@RequiresPermissions("system:couponManger:remove")
	@Log(title = "优惠券", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return  error("不允许删除!");// toAjax(volumeMarketFeign.(ids));
	}
	
}
