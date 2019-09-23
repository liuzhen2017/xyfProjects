package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProChannelRelationDTO;
import com.xinyunfu.product.mapper.ProChannelRelationMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.ProChannelRelation;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProImage;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IChannelRelationService;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChannelRelationServiceImpl extends ServiceImpl<ProChannelRelationMapper, ProChannelRelation>
        implements IChannelRelationService {

    @Autowired
    private ProChannelRelationMapper channelRelationMapper;
    @Autowired
    private ProductMapper productMapper;


    /*
     * 新增分类商品
     */
    public ResponseInfo<String> savePCR(ProChannelRelation proChannelRelation) {
        if (this.save(proChannelRelation)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }

    }

    /**
     * 上下架
     *
     * @param ids
     * @param status
     * @return
     */
    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, int status) {
        ProChannelRelation proChannelRelation = new ProChannelRelation();
        proChannelRelation.setStatus(status);
        UpdateWrapper<ProChannelRelation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("proId", ids);
        if (this.update(proChannelRelation, updateWrapper)) {
            if (status == 1) {
                return ResponseInfo.success("下架成功!");
            } else {
                return ResponseInfo.success("上架成功!");
            }
        } else {
            if (status == 1) {
                return ResponseInfo.error("下架失败");
            } else {
                return ResponseInfo.error("上架失败");
            }
        }
    }

    /**
     * 分页查询
     *
     * @param proChannelRelation
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ResponseInfo<PageVO<ProChannelRelation>> findChannelRelationByPage(ProChannelRelation proChannelRelation,
                                                                              Integer page, Integer pageSize) {
        page = (page == null ? 1 : page);
        pageSize = (pageSize == null ? 15 : pageSize);
        PageVO<ProChannelRelation> pages = new PageVO<>();
        List<ProChannelRelation> list = channelRelationMapper.selectChannelRelationList(proChannelRelation);
        pages.setCurrent_page(page);
        pages.setInstanceList(list);
        pages.setSize(pageSize);
        return ResponseInfo.success(pages);
    }

    /**
     * @return
     */
    @Override
    public List<ProChannelRelation> queryJDProChannelRelationReshelf() {
        List<Long> list = productMapper.selectProIdList();
        QueryWrapper<ProChannelRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("pro_id", list);
        return channelRelationMapper.selectList(queryWrapper);
    }

    /**
     * 删除
     *
     * @param ids
     * @param i
     * @return
     */
    @Override
    public ResponseInfo<String> deleteByIds(Long[] ids, int i) {
        ProChannelRelation proChannelRelation = new ProChannelRelation();
        proChannelRelation.setStatus(i);
        UpdateWrapper<ProChannelRelation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        if (this.update(proChannelRelation, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }

    @Override
    public boolean checkByPidAndCid(Long proId, Integer channelId) {
        return channelRelationMapper.checkByPidAndCid(proId, channelId) > 0;
    }

    /**
     * 批量新增商品分类关系
     *
     * @param proChannelRelationDTO
     * @return
     */
    @Override
    public boolean saveProChannelRelations(ProChannelRelationDTO proChannelRelationDTO) {
        boolean flag = true;
        String[] proIds = proChannelRelationDTO.getProIds().split(";");
        String[] proNames = proChannelRelationDTO.getProNames().split(";");
        Long channelId = proChannelRelationDTO.getChannelId();
        String channelName = proChannelRelationDTO.getChannelName();
        for (int i = 0; i < proIds.length; i++) {
            if (!this.save(new ProChannelRelation(Long.valueOf(proIds[i]), proNames[i], channelId, channelName))) {
                log.info("==============插入id为proId={}商品分类关系失败=============", proIds[i]);
                continue;
            }
        }
        return flag;
    }

    @Override
    public ProChannelRelation findChannelRelationByProId(Long proId) {
        QueryWrapper<ProChannelRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("pro_id", proId).eq("status", 0);
        ProChannelRelation proChannelRelation = this.getOne(wrapper);
        if (proChannelRelation == null) {
            log.info("============id为proId={}的商品暂无分类或已被禁用=========", proId);
        }
        return proChannelRelation;
    }

}
