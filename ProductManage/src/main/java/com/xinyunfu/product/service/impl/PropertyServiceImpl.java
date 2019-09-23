package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.v2.log.log4j.Log4jMLog;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.ProPropertyMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.ProChannelRelation;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.service.IPropertyService;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PropertyServiceImpl extends ServiceImpl<ProPropertyMapper, ProProperty> implements IPropertyService {

    @Autowired
    private ProPropertyMapper proPropertyMapper;
    @Autowired
    private ProductMapper productMapper;

    /*
     * 根据商品id查询属性
     */
    @Override
    public ResponseInfo<List<ProProperty>> findPropertyByProId(Long proId) {
        QueryWrapper<ProProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("status", 0);
        List<ProProperty> list = proPropertyMapper.selectList(queryWrapper);
        if (list == null || list.isEmpty()) {
            log.info("===========id为proId={}的商品属性不存在或已被禁用=========", proId);
        }
        return ResponseInfo.success(list);
    }

    @Override
    public List<List<ProProperty>> findPropertyByProIds(List<Long> ids) {
        List<List<ProProperty>> lists = new ArrayList<>();
        for (Long proId : ids) {
            List<ProProperty> list = this.findPropertyByProId(proId).getData();
            lists.add(list);
        }
        return lists;
    }

    /*
     * 新增
     */
    @Override
    public ResponseInfo<String> saveProProperty(ProProperty proProperty) {
        if (this.save(proProperty)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    @Override
    public boolean addProperty(ProProperty proProperty) {
        return proPropertyMapper.addProperty(proProperty) > 0;
    }

    /*
     * 修改
     */
    @Override
    public ResponseInfo<String> updateProProperty(ProProperty proProperty) {
        if (proPropertyMapper.insert(proProperty) > 0) {
            return ResponseInfo.success("修改成功!");
        } else {
            return ResponseInfo.error("修改失败!");
        }
    }

    /*
     * 修改状态
     */
    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, int status) {
        ProProperty proProperty = new ProProperty();
        proProperty.setStatus(status);
        UpdateWrapper<ProProperty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("propertyvalue_id", ids);
        if (this.update(proProperty, updateWrapper)) {
            if (status == 0) {
                return ResponseInfo.success("启用成功!");
            } else if (status == 1) {
                return ResponseInfo.success("禁用成功!");
            } else {
                return ResponseInfo.success("删除成功!");
            }
        } else {
            if (status == 0) {
                return ResponseInfo.error("启用失败!");
            } else if (status == 1) {
                return ResponseInfo.error("禁用失败!");
            } else {
                return ResponseInfo.error("删除失败!");
            }
        }

    }

    /*
     * 分页查询
     */
    @Override
    public ResponseInfo<PageVO<ProProperty>> queryByPage(ProProperty proProperty, Integer page, Integer pageSize) {
        page = (page == null ? 1 : page);
        pageSize = (pageSize == null ? 15 : pageSize);
        PageVO<ProProperty> pages = new PageVO<>();
        List<ProProperty> list = proPropertyMapper.selectPropertyList(proProperty);
        pages.setCurrent_page(page);
        pages.setInstanceList(list);
        pages.setSize(pageSize);
        return ResponseInfo.success(pages);
    }

    @Override
    public List<ProProperty> queryJDPropertyReshelf() {
        List<Long> list = productMapper.selectProIdList();
        QueryWrapper<ProProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("proId", list);
        return proPropertyMapper.selectList(queryWrapper);
    }

    @Override
    public ProProperty queryPropertyByProIdAndName(Long proId, String propertyName) {
        QueryWrapper<ProProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("property_name", propertyName);
        return proPropertyMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean checkPropertyByProIdAndName(long proId, String propertyName) {
        return proPropertyMapper.checkPropertyByProIdAndName(proId, propertyName) > 0;
    }

    /**
     * 删除
     *
     * @param ids
     * @param i
     * @return
     */
    @Override
    public ResponseInfo<String> deleteProperty(Long[] ids, int i) {
        ProProperty proProperty = new ProProperty();
        proProperty.setStatus(i);
        UpdateWrapper<ProProperty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("property_id", ids);
        if (this.update(proProperty, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }


    @Override
    public Long saveProperty(ProProperty property) {
        if (this.save(property)) {
            return property.getPropertyId();
        } else {
            return null;
        }
    }

    /**
     * edit by lhpu
     * ====================================================================
     */

    /**
     * 根据商品id查询属性
     */
    @Override
    public List<ProProperty> getAttrListByProId(Long proId) {
        QueryWrapper<ProProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId);
        return proPropertyMapper.selectList(queryWrapper);
    }




}
