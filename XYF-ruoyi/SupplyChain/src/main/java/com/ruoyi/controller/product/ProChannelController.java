package com.ruoyi.controller.product;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.feign.ProductManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.domain.ProChannel;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 类目 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proChannel")
public class ProChannelController extends BaseController {


	@Autowired
    private ProductManageFeign productManageFeign;

	@GetMapping()
	public String proChannel(@RequestParam("id") String id,Model model){
	    model.addAttribute("id",id); //递归调用
	    return "system/proChannel/proChannel";
	}
	
	/**
	 * 查询类目列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProChannel proChannel) {
	    if(proChannel.isSelectAll()){ //如果搜索所有分类
	        proChannel.setFatherId("");
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        com.ruoyi.utils.ResponseInfo<Page<ProChannel>> res = productManageFeign.findProChannelByPage(proChannel,page, pageSize);
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
	}

    /**
     * 排序
     * @param channelId
     * @param fatherId
     * @param sortNumber
     * @param mode
     * @return
     */
	@GetMapping("/sort")
    @ResponseBody
	public Object sort(@RequestParam("channelId") Long channelId,
                       @RequestParam("fatherId") Long fatherId,
                       @RequestParam("sortNumber") Integer sortNumber,
                       @RequestParam("mode") Integer mode){
	    return productManageFeign.sort(channelId, fatherId, sortNumber, mode);
    }

	@ResponseBody
	@GetMapping("/findAll")
	public Object findAll(){
	    return productManageFeign.findAll();
    }
	
	/**
	 * 新增类目
	 */
	@GetMapping("/add")
	public String add() {
	    return "system/proChannel/add";
	}
	
	/**
	 * 新增保存类目
	 */
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProChannel proChannel) {
        String[] val = proChannel.getChannelTypeId().split(";");
        proChannel.setChannelTypeId(val[0]);
        proChannel.setChannelTypeName(val[1]);
        return toAjax(productManageFeign.saveProChannel(proChannel).getCode());
	}

	/**
	 * 修改类目
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model map){
		map.addAttribute("proChannel",productManageFeign.selectProChannelById(id).getData());
		return "system/proChannel/edit";
	}
	
	/**
	 * 修改保存类目
	 */
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProChannel proChannel) {
        String[] val = proChannel.getChannelTypeId().split(";");
        proChannel.setChannelTypeId(val[0]);
        proChannel.setChannelTypeName(val[1]);
        com.ruoyi.utils.ResponseInfo<String> responseInfo = productManageFeign.updateProChannel(proChannel);
        if(responseInfo.isSuccess()){
            return success();
        }
        return error(responseInfo.getMessage());
	}
	

    /**
     * 选择分类树
     */
    @GetMapping("/selectChannelTree/{channelId}")
    public String selectChannelTree(@PathVariable("channelId") Long channleId, Model model) {
        model.addAttribute("proChannel",productManageFeign.selectProChannelByChannelId(channleId).getData());
        return  "system/proChannelRelation/tree";
    }

    /**
     * 加载分类列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = productManageFeign.selectProChannelTree(new ProChannel()).getData();
        return ztrees;
    }

    /**
     * 选择商品
     * @return
     */
    @GetMapping("/selectGoods")
    public String selectGoods(){
        return "system/proChannel/selectGoods";
    }




	
}
