package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProChannelRelationDTO;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProChannelRelation;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.service.IChannelRelationService;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/channelRelation")
public class ChannelRelationController {

    @Autowired
    private IChannelRelationService channelRelationService;

    /**
     * 批量新增商品分类关系
     *
     * @param proChannelRelationDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProChannelRelations(@RequestBody ProChannelRelationDTO proChannelRelationDTO) {
        if (channelRelationService.saveProChannelRelations(proChannelRelationDTO))
            return ResponseInfo.success("新增成功!");
        else
            return ResponseInfo.error("新增失败!");
    }
    /**
     * 新增商品分类关系
     *
     * @param proChannelRelation
     * @return
     */
    @PostMapping("/saveOne")
    public ResponseInfo<String> saveProChannelRelation(@RequestBody ProChannelRelation proChannelRelation) {
        if (channelRelationService.save(proChannelRelation))
            return ResponseInfo.success("新增成功!");
        else
            return ResponseInfo.error("新增失败!");
    }

    /**
     * 修改商品分类关系
     *
     * @param proChannelRelation
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProProerty(@RequestBody ProChannelRelation proChannelRelation) {
        if (channelRelationService.saveOrUpdate(proChannelRelation) == true)
            return ResponseInfo.success("修改成功!");
        else
            return ResponseInfo.error("修改失败!");
    }

    /**
     * 分页查询
     *
     * @param proChannelRelation
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProChannelRelation>> findChannelRelationByPage(@RequestBody ProChannelRelation proChannelRelation,
                                                                             @RequestParam("page") Integer page,
                                                                             @RequestParam("pageSize") Integer pageSize) {
        IPage<ProChannelRelation> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProChannelRelation> wrapper = new LambdaQueryWrapper<>();
        if (proChannelRelation.getProId() != null) {
            wrapper.eq(ProChannelRelation::getProId, proChannelRelation.getProId());
        }
        if (proChannelRelation.getChannelId() != null) {
            wrapper.eq(ProChannelRelation::getChannelId, proChannelRelation.getChannelId());
        }
        if (proChannelRelation.getStatus() != null) {
            wrapper.eq(ProChannelRelation::getStatus, proChannelRelation.getStatus());
        }
        if (StringUtils.isNotEmpty(proChannelRelation.getChannelName())) {
            wrapper.like(ProChannelRelation::getChannelName, proChannelRelation.getChannelName());
        }
        if (StringUtils.isNotEmpty(proChannelRelation.getProName())) {
            wrapper.like(ProChannelRelation::getProName, proChannelRelation.getProName());
        }
        return ResponseInfo.success(channelRelationService.page(pages, wrapper));
    }

    /**
     * 下架商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/proChannelRelation/instock")
    public ResponseInfo<String> instockProChannelRelation(@RequestParam("ids") Long[] ids) {
        return channelRelationService.updateStatus(ids, 1);
    }

    /**
     * 上架商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/proChannelRelation/reshelf")
    public ResponseInfo<String> reshelfProChannelRelation(@RequestParam("ids") Long[] ids) {
        return channelRelationService.updateStatus(ids, 1);
    }

    /**
     * 删除商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/property/delete")
    public ResponseInfo<String> deleteProChannelRelation(@RequestParam("ids") Long[] ids) {
        return channelRelationService.deleteByIds(ids, 2);
    }

    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody ProChannelRelation proChannelRelation) {
        return channelRelationService.saveOrUpdate(proChannelRelation);
    }

    /**
     * 查询所有来源为京东,状态为0的商品的分类
     *
     * @return
     */
    @GetMapping("/queryJDProChannelRelationReshelf")
    public ResponseInfo<List<ProChannelRelation>> queryJDProChannelRelationReshelf() {
        return ResponseInfo.success(channelRelationService.queryJDProChannelRelationReshelf());
    }


      /*
       *新增商品分类关系
       */
      @PostMapping("/add")
      public ResponseInfo<String> add(@RequestBody ProChannelRelation proChannelRelation) {
            return channelRelationService.savePCR(proChannelRelation);
      }


      /**
       * 根据proId和channelId查询当前关系是否存在
       *
       * @return
       */
      @GetMapping("/checkByPidAndCid")
      public ResponseInfo<Boolean> checkByPidAndCid(@RequestParam("proId") Long proId, @RequestParam("channelId") Integer channelId) {
            return ResponseInfo.success(channelRelationService.checkByPidAndCid(proId, channelId));
      }


    /**
     * 后台删除商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/deletePCR")
    public ResponseInfo<String> deletePCR(@RequestParam("ids") String ids){
        String[] str1 = ids.split(",");
        Long[] str2 = new Long[str1.length];
        for (int i = 0; i < str1.length; i++) {
            str2[i] = Long.valueOf(str1[i]);
        }
        return channelRelationService.deleteByIds(str2,2);
    }


    /**
     * 根据id查询商品分类关系
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    ResponseInfo<ProChannelRelation> selectProChannelRelationById(@RequestParam("id") Long id){
        if (id==null){
            return new ResInfo(Res.ERROR_PARAM);
        }else{
            ProChannelRelation proChannelRelation=channelRelationService.getById(id);
            if (proChannelRelation==null){
                return new ResInfo(Res.NO_DATA);
            }
            return ResponseInfo.success(proChannelRelation);
        }
    }









}
