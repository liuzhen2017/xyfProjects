package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.model.ProPropertyValue;
import com.xinyunfu.product.service.IPropertyValueService;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propertyValue")
public class PropertyValueController {

    @Autowired
    private IPropertyValueService propertyValueService;

    /**
     * 新增商品属性值
     *
     * @param proPropertyValue
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProertyValue(@RequestBody ProPropertyValue proPropertyValue) {
        if (propertyValueService.saveOrUpdate(proPropertyValue) == true)
            return ResponseInfo.success("新增成功!");
        else
            return ResponseInfo.error("新增失败!");
    }

    /**
     * 修改商品属性值
     *
     * @param proPropertyValue
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProertyValue(@RequestBody ProPropertyValue proPropertyValue) {
        if (propertyValueService.saveOrUpdate(proPropertyValue) == true)
            return ResponseInfo.success("修改成功!");
        else
            return ResponseInfo.error("修改失败!");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProPropertyValue>> findPropertyValueByPage(@RequestBody ProPropertyValue proPropertyValue,
                                                                          @RequestParam("page") Integer page,
                                                                          @RequestParam("pageSize") Integer pageSize) {
        IPage<ProPropertyValue> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProPropertyValue> wrapper = new LambdaQueryWrapper<>();
        if (proPropertyValue.getPropertyId() != null) {
            wrapper.eq(ProPropertyValue::getPropertyId, proPropertyValue.getPropertyId());
        }
        if (proPropertyValue.getValueText() != null) {
            wrapper.eq(ProPropertyValue::getValueText, proPropertyValue.getValueText());
        }
        if (proPropertyValue.getStatus() != null) {
            wrapper.eq(ProPropertyValue::getStatus, proPropertyValue.getStatus());
        }
        return ResponseInfo.success(propertyValueService.page(pages, wrapper));
    }

    /**
     * 下架商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockPropertyValue(@RequestParam("ids") Long[] ids) {
        return propertyValueService.updateStatus(ids, 1);
    }

    /**
     * 上架商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfPropertyValue(@RequestParam("ids") Long[] ids) {
        return propertyValueService.updateStatus(ids, 0);
    }

    /**
     * 删除商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteProertyValue(@RequestParam("ids") Long[] ids) {
        return propertyValueService.deletePropertyValue(ids, 2);
    }

    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody ProPropertyValue propertyValue) {
        return propertyValueService.saveOrUpdate(propertyValue);
    }

    /**
     * 查询所有来源为京东,状态为0的商品的属性值
     *
     * @return
     */
    @GetMapping("/queryJDPropertyValueReshelf")
    public ResponseInfo<List<ProPropertyValue>> queryJDPropertyValueReshelf() {
        return ResponseInfo.success(propertyValueService.queryJDPropertyValueReshelf());
    }

    /**
     * 根据属性id和属性值查询属性值对象
     *
     * @param propertyId
     * @param valueText
     * @return
     */
    @GetMapping("/queryPropertyValueByPropertyIdValueText")
    public ResponseInfo<ProPropertyValue> queryPropertyValueByPropertyIdValueText(@RequestParam("propertyId") Long propertyId,
                                                                                  @RequestParam("valueText") String valueText) {
        ProPropertyValue propertyValue = propertyValueService.queryPropertyValueByPropertyIdValueText(propertyId, valueText);
        if (propertyValue == null)
            return new ResInfo(Res.NO_DATA);
        else
            return ResponseInfo.success(propertyValue);
    }


    /**
     * 根据属性id和属性值查询属性值id
     * @param propertyId
     * @param value
     * @return
     */
    @GetMapping("/getId")
    public Long getValueId(@RequestParam("propertyId")Long propertyId,@RequestParam("value") String value){
        return propertyValueService.getValueId(propertyId,value);
    }







    /**
     * edit by lhpu
     * ==========================================================
     */

    /**
     * 根据属性id和属性值查询属性值对象是否存在
     *
     * @param propertyId
     * @param valueText
     * @return
     */
    @GetMapping("/checkByPropertyIdAndValueText")
    public ResponseInfo<Boolean> checkByPropertyIdAndValueText(
              @RequestParam("propertyId") long propertyId, @RequestParam("valueText") String valueText) {

        return ResponseInfo.success(propertyValueService.checkByPropertyIdAndValueText(propertyId, valueText));

    }


    /**
     * 根据属性id查询属性值对象
     *
     * @param propertyId
     * @return
     */
    @GetMapping("/getPropertyValueByPropertyId")
    public ResponseInfo<List<ProPropertyValue>> getPropertyValueByPropertyId(@RequestParam("propertyId") Long propertyId) {
        List<ProPropertyValue> pvList = propertyValueService.getPropertyValueByPropertyId(propertyId);
        return ResponseInfo.success(pvList);
    }


    /**
     * edit by lhpu
     * ========================================================================
     */

    /*
     *新增属性值
     */
    @PostMapping("/add")
    public ResponseInfo<String> add(@RequestBody ProPropertyValue proPropertyValue) {
        return propertyValueService.savePPV(proPropertyValue);
    }





}
