package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.ProPropertyMapper;
import com.xinyunfu.product.mapper.ProPropertyValueMapper;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.model.ProPropertyValue;
import com.xinyunfu.product.service.IPropertyService;
import com.xinyunfu.product.service.IPropertyValueService;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PropertyValueServiceImpl extends ServiceImpl<ProPropertyValueMapper, ProPropertyValue> implements IPropertyValueService {

    @Autowired
    private ProPropertyValueMapper proPropertyValueMapper;
    @Autowired
    private ProPropertyMapper propertyMapper;
    @Autowired
    private IPropertyService propertyService;

    /*
     * 根据多个属性id查询属性值
     */
    @Override
    public ResponseInfo<List<List<ProPropertyValue>>> findPropertyValueByPropertyIds(List<Long> ids) {
        List<List<ProPropertyValue>> list = new ArrayList<>();
        for (Long id : ids) {
            list.add(this.findPropertyValueByPropertyId(id));
        }
        return ResponseInfo.success(list);
    }

    /*
     * 根据单个属性id查询属性值
     */
    public List<ProPropertyValue> findPropertyValueByPropertyId(Long propertyId) {
        QueryWrapper<ProPropertyValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("property_id", propertyId).eq("status",0);
        List<ProPropertyValue> list = proPropertyValueMapper.selectList(queryWrapper);
        if (list==null || list.size()==0){
            log.info("===========属性id为propertyId={}的属性没有属性值===========",propertyId);
        }
        return list;
    }

    /**
     * 查询所有来源为京东,状态为0的商品的属性值
     *
     * @return
     */
    @Override
    public List<ProPropertyValue> queryJDPropertyValueReshelf() {
        List<ProProperty> proProperties = propertyService.queryJDPropertyReshelf();
        List<Long> list = new ArrayList<>();
        for (ProProperty proProperty : proProperties
        ) {
            list.add(proProperty.getPropertyId());
        }
        QueryWrapper<ProPropertyValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("property_id", list);
        return proPropertyValueMapper.selectList(queryWrapper);
    }

    @Override
    public ProPropertyValue queryPropertyValueByPropertyIdValueText(Long propertyId, String valueText) {
        QueryWrapper<ProPropertyValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("property_id", propertyId).eq("value_text", valueText);
        return proPropertyValueMapper.selectOne(queryWrapper);
    }

    /**
     * 删除
     *
     * @param ids
     * @param i
     * @return
     */
    @Override
    public ResponseInfo<String> deletePropertyValue(Long[] ids, int i) {
        ProPropertyValue propertyValue = new ProPropertyValue();
        propertyValue.setStatus(i);
        UpdateWrapper<ProPropertyValue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("value_id", ids);
        if (this.update(propertyValue, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }

    /*
     * 新增
     */
    @Override
    public ResponseInfo<String> savePPV(ProPropertyValue proPropertyValue) {
        if (proPropertyValueMapper.insert(proPropertyValue) > 0) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    /*
     * 修改
     */
    @Override
    public ResponseInfo<String> updatePPV(ProPropertyValue proPropertyValue) {
        if (this.updateById(proPropertyValue)) {
            return ResponseInfo.success("修改成功!");
        } else {
            return ResponseInfo.error("修改失败!");
        }
    }

    /*
     * 修改sku状态,0启用,1禁用,2删除
     */
    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, Integer status) {
        ProPropertyValue propertyValue = new ProPropertyValue();
        propertyValue.setStatus(status);
        UpdateWrapper<ProPropertyValue> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("value_id", ids);
        if (this.update(propertyValue, updateWrapper)) {
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

    @Override
    public ResponseInfo<PageVO<ProPropertyValue>> queryByPage(ProPropertyValue proPropertyValue, Integer page,
                                                              Integer pageSize) {
        page = (page == null ? 1 : page);
        pageSize = (pageSize == null ? 15 : pageSize);
        PageVO<ProPropertyValue> pages = new PageVO<>();
        List<ProPropertyValue> list = proPropertyValueMapper.selectPropertyValueList(proPropertyValue);
        pages.setCurrent_page(page);
        pages.setInstanceList(list);
        pages.setSize(pageSize);
        return ResponseInfo.success(pages);
    }

    @Override
    public Long getValueId(Long propertyId, String value) {
        return proPropertyValueMapper.getValueId(propertyId, value);
    }

    @Override
    public List<List<ProPropertyValue>> findPropertyValueByProId(Long proId) {
        List<Long> propertyIds = propertyMapper.findPropertyIdsByProId(proId);
        if (propertyIds == null || propertyIds.size()==0) {
            log.info("===============id为proId={}的商品属性不存在或已被禁用===========", proId);
            return null;
        } else {
            List<List<ProPropertyValue>> propertyValues=findPropertyValueByPropertyIds(propertyIds).getData();
            if (propertyValues==null || propertyValues.size()==0){
                log.info("===========id为proId={}的商品没有属性值或已被禁用===========",proId);
            }
            return propertyValues;
        }
    }

    /**
     * edit by lhpu
     * =============================================================
     */


    @Override
    public boolean checkByPropertyIdAndValueText(long propertyId, String valueText) {
        return proPropertyValueMapper.checkByPropertyIdAndValueText(propertyId, valueText) > 0;
    }

    @Override
    public List<ProPropertyValue> getPropertyValueByPropertyId(Long propertyId) {

        QueryWrapper<ProPropertyValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("property_id", propertyId);
        List<ProPropertyValue> list = proPropertyValueMapper.selectList(queryWrapper);
        return list;
    }


}
