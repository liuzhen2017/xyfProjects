package com.xinyunfu.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.JDProductDTO;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.PropertyAndSkuDTO;
import com.xinyunfu.product.dto.PropertyDTO;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.feign.ProductDockingFeign;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.*;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private IPropertyService propertyService;
    @Autowired
    private IPropertyValueService propertyValueService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductDockingFeign productDockingFeign;

    /**
     * 新增商品属性
     *
     * @param proProperty
     * @return
     */
    @PostMapping("/save")
    public Long saveProerty(@RequestBody ProProperty proProperty) {
        if (propertyService.save(proProperty) == true)
            return proProperty.getPropertyId();
        return null;
    }

    /**
     * 修改商品属性
     *
     * @param proProperty
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProerty(@RequestBody ProProperty proProperty) {
        if (propertyService.saveOrUpdate(proProperty)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProProperty>> findPropertyByPage(@RequestBody ProProperty proProperty,
                                                               @RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize) {
        IPage<ProProperty> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProProperty> wrapper = new LambdaQueryWrapper<>();
        if (proProperty.getProId() != null) {
            wrapper.eq(ProProperty::getProId, proProperty.getProId());
        }
        if (proProperty.getPropertyName() != null) {
            wrapper.eq(ProProperty::getPropertyName, proProperty.getPropertyName());
        }
        if (proProperty.getStatus() != null) {
            wrapper.eq(ProProperty::getStatus, proProperty.getStatus());
        }
        return ResponseInfo.success(propertyService.page(pages, wrapper));
    }

    /**
     * 下架商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProperty(@RequestParam("ids") Long[] ids) {
        return propertyService.updateStatus(ids, 1);
    }

    /**
     * 上架商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProperty(@RequestParam("ids") Long[] ids) {
        return propertyService.updateStatus(ids, 0);
    }

    /**
     * 删除商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/property/delete")
    public ResponseInfo<String> deleteProperty(@RequestParam("ids") Long[] ids) {
        return propertyService.deleteProperty(ids, 2);
    }

    @PostMapping("/findPropertyAndValueByProId")
    public ResponseInfo<PropertyAndSkuDTO> findPropertyAndValueByProId(@RequestBody ProIdDTO proIdDTO) {
        if (proIdDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            PropertyAndSkuDTO propertyAndSkuDTO = new PropertyAndSkuDTO();
            long proId = proIdDTO.getProId();
            List<ProProperty> list = propertyService.findPropertyByProId(proId).getData();
            List<PropertyDTO> propertyDTOS = new ArrayList<>();
            for (ProProperty proProperty : list) {
                List<ProPropertyValue> list1 = propertyValueService.findPropertyValueByPropertyId(proProperty.getPropertyId());
                PropertyDTO propertyDTO = new PropertyDTO();
                propertyDTO.setProperty(proProperty).setProPropertyValues(list1);
                propertyDTOS.add(propertyDTO);
            }
            String imgUrl = fileService.findDefaultImageByProId(proId);
            List<ProSku> proSkus = skuService.findSkuByProId(proIdDTO);
            if (productService.getById(proId).getSource()==1){
                for (ProSku proSku : proSkus) {
                    ResponseInfo<?> productDto = productDockingFeign.getProductDto(proId,proSku.getPrice(),proSku.getCostPrice(),proSku.getSkuNo(),"75");
                    if(!productDto.isSuccess()){
                        return new ResponseInfo<>(productDto.getCode(),productDto.getMessage(),null);
                    }
//                    JDProductDTO dto =(JDProductDTO)productDto.getData();
                    JDProductDTO dto = JSONObject.parseObject(JSONObject.toJSONString(productDto.getData()),JDProductDTO.class);
                    proSku.setPrice(dto.getPrice());
                }
            }
            propertyAndSkuDTO.setPropertyDTOS(propertyDTOS).setProSkus(proSkus).setImgUrl(imgUrl);
            if (propertyAndSkuDTO == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(propertyAndSkuDTO);
        }
    }

//    @PostMapping("/saveOrUpdate")
//    public boolean saveOrUpdate(@RequestBody ProProperty proProperty) {
//        return propertyService.saveOrUpdate(proProperty);
//    }

    /**
     * 查询所有来源为京东,状态为0的商品的属性
     *
     * @return
     */
    @GetMapping("/queryJDPropertyReshelf")
    public ResponseInfo<List<ProProperty>> queryJDPropertyReshelf() {
        return ResponseInfo.success(propertyService.queryJDPropertyReshelf());
    }

    /**
     * 根据商品id查询商品属性
     *
     * @param proId
     * @return
     */
    @GetMapping("/queryPropertyByProId")
    public ResponseInfo<List<ProProperty>> queryPropertyByProId(@RequestParam("proId") Long proId) {
        if (proId==null){
            return new ResInfo(Res.ERROR_PARAM);
        }else {
            if (propertyService.findPropertyByProId(proId).getData()==null){
                return new ResInfo(Res.NO_DATA);
            }
            return propertyService.findPropertyByProId(proId);
        }
    }

    /**
     * 根据商品id和属性名查询属性对象
     *
     * @param proId
     * @param propertyName
     * @return
     */
    @GetMapping("/queryPropertyByProIdAndName")
    public ResponseInfo<ProProperty> queryPropertyByProIdAndName(@RequestParam("proId") Long proId, @RequestParam("propertyName") String propertyName) {
        ProProperty property = propertyService.queryPropertyByProIdAndName(proId, propertyName);
        if (property == null)
            return new ResInfo(Res.NO_DATA);
        else
            return ResponseInfo.success(property);
    }


    /**
     * 根据商品id和属性名查询属性对象是否已存在
     *
     * @param proId
     * @param propertyName
     * @return
     */
    @GetMapping("/checkPropertyByProIdAndName")
    public ResponseInfo<Boolean> checkPropertyByProIdAndName(@RequestParam("proId") long proId, @RequestParam("propertyName") String propertyName) {
        return ResponseInfo.success(propertyService.checkPropertyByProIdAndName(proId, propertyName));
    }


    /**
     * edit by lhpu
     * ==============================================================================
     */

    /**
     * 新增property
     */
    @PostMapping("/addProperty")
    public ResponseInfo<String> addProperty(@RequestBody ProProperty proProperty) {
        return propertyService.saveProProperty(proProperty);
    }


    /**
     * 根据商品id查询商品属性
     *
     * @param proId
     * @return
     */
    @GetMapping("/getAttrListByProId")
    public ResponseInfo<List<ProProperty>> getAttrListByProId(@RequestParam("proId") Long proId) {

        return ResponseInfo.success(propertyService.getAttrListByProId(proId));
    }

}
