package com.xinyunfu.product.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProChannelType;
import com.xinyunfu.product.service.IProChannelTypeService;
import com.xinyunfu.product.utils.ResInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类类型 信息操作处理
 * 
 * @author TYM
 * @date 2019-08-30
 */
@RestController
@RequestMapping("/proChannelType")
public class ProChannelTypeController
{

	
	@Autowired
	private IProChannelTypeService proChannelTypeService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProChannelType>> findProductByPage(@RequestBody ProChannelType proChannelType,
                                                                 @RequestParam("page") Integer page,
                                                                 @RequestParam("pageSize") Integer pageSize) {

        LambdaQueryWrapper<ProChannelType> wrapper = new LambdaQueryWrapper<>();
        if (proChannelType.getChannelTypeName() != null && StringUtils.isNotEmpty(proChannelType.getChannelTypeName())) {
            wrapper.like(ProChannelType::getChannelTypeName, proChannelType.getChannelTypeName());
        }

        return ResponseInfo.success(proChannelTypeService.page(new Page(page,pageSize), wrapper));
    }

    /**
     * 新增商品
     *
     * @param proChannelType
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProduct(@RequestBody ProChannelType proChannelType) {
        boolean flag = proChannelTypeService.save(proChannelType);
        if (flag)
            return ResponseInfo.success("新增成功");
        return ResponseInfo.error("新增失败");
    }

    /**
     * 修改商品
     *
     * @param proChannelType
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProduct(@RequestBody ProChannelType proChannelType) {
        if (proChannelTypeService.saveOrUpdate(proChannelType)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }

    @GetMapping("/findAll")
    public ResponseInfo<List<ProChannelType>> findAll(){
        List<ProChannelType> list= proChannelTypeService.findAll();
        if (list==null || list.isEmpty()){
            return new ResInfo(Res.NO_DATA);
        }
        return ResponseInfo.success(list);

    }

    /**
     * 根据id查询
     * @param channelTypeId
     * @return
     */
    @GetMapping("/findById")
    public ResponseInfo<ProChannelType> findById(@RequestParam("channelTypeId")Long channelTypeId){
        if (channelTypeId==null){
            return new ResInfo(Res.ERROR_PARAM);
        }else{
            QueryWrapper<ProChannelType> wrapper=new QueryWrapper<>();
            wrapper.eq("channel_type_id",channelTypeId);
            ProChannelType proChannelType=proChannelTypeService.getOne(wrapper);
            if (proChannelType==null){
                return new ResInfo(Res.NO_DATA);
            }
            return ResponseInfo.success(proChannelType);
        }

    }




}
