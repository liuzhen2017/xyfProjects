package com.ruoyi.controller.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.*;
import com.ruoyi.dto.AllProDto;
import com.ruoyi.dto.ProDto;
import com.ruoyi.dto.RProductDTO;
import com.ruoyi.dto.UpperShelfDto;
import com.ruoyi.feign.ProductDockingFeign;
import com.ruoyi.feign.ProductManageFeign;
import com.ruoyi.service.IProductService;
import com.ruoyi.util.DescartesUtil;
import com.ruoyi.utils.ResponseInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("system/product")
public class ProductController extends BaseController {
    private String prefix = "system/product";

    @Autowired
    private IProductService productService;
    @Autowired
    private ProductManageFeign productManageFeign;

    @Autowired
    private ProductDockingFeign productDockingFeign;

    @RequiresPermissions("system:product:view")
    @GetMapping()
    public String product() {
        return prefix + "/product";
    }

    @RequiresPermissions("system:product:updateSKU")
    @GetMapping("/updateSKU")
    public String updateSKU() {
        return prefix + "/updateSKU";
    }

    /**
     * 查询商品列表
     */
    @RequiresPermissions("system:product:checkList")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RProductDTO rProductDTO) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        ResponseInfo<Page<RProductDTO>> res = productManageFeign.findRProductDTOByPage(rProductDTO, page, pageSize);
        return getDataTable(res.getData().getRecords(), res.getData().getTotal());
    }


    @GetMapping("/selectProduct")
    public String selectProduct() {
        return "system/proDetails/selectProduct";
    }

    @GetMapping("/proChannelRelation")
    public String proChannelRelation() {
        return "/system/proDetails/proChannelRelation";
    }

    @GetMapping("/selectProductBySign")
    public String selectProductBySign() {
        return "/system/util/selectProductBySign";
    }

    @GetMapping("/selectProductAll")
    public String selectProductAll() {
        return "system/util/selectProductAll";
    }

    /**
     * 导出商品列表
     */
//    @RequiresPermissions("system:product:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(RProductDTO rProductDTO) {
//        List<RProductDTO> list = productManageFeign.selectProductList(rProductDTO);
//        ExcelUtil<RProductDTO> util = new ExcelUtil<RProductDTO>(RProductDTO.class);
//        return util.exportExcel(list, "rProductDTO");
//    }

    /**
     * 新增商品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品
     */
    @RequiresPermissions("system:product:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @Transactional
    public AjaxResult addSave(ProDto proDto) {
        if (proDto.getTcPrice() != null && proDto.getTcPrice().compareTo(new BigDecimal(230)) == -1) {
            return AjaxResult.error("套餐商品价格不能低于230,请重新输入");
        }
        for (int i = 0; i < proDto.getCostPrice().size(); i++) {
            if (proDto.getProType() == 2 && proDto.getPrice().get(i).compareTo(new BigDecimal(230)) == -1) {
                return AjaxResult.error("套餐商品价格不能低于230,请重新输入");
            } else {
                continue;
            }
        }

        Product product = new Product();
        //插入product
        product.setProName(proDto.getProName())
                .setProNo(proDto.getProNo())
                .setProTitle(proDto.getProTitle())
                .setUnit(proDto.getUnit())
                .setWeight(proDto.getWeight())
                .setProType(proDto.getProType())
                .setCouponType(proDto.getCouponType())
                .setCouponTypeName(proDto.getCouponTypeName());
        Long proId = productManageFeign.saveProduct(product);
//        Long proId = productManageFeign.getProIdByProName(proDto.getProName,proDto.getProTitle());

        //插入proPayType
        List<Integer> type = proDto.getPayType();
        String payType = "";
        for (int i : type
        ) {
            payType = payType + i + ",";
        }
        payType = payType.substring(0, payType.length() - 1);
        Long id = productManageFeign.findIdByPayType(payType);
        ProPayType proPayType = new ProPayType();
        proPayType.setProId(proId);
        proPayType.setPayTypeId(id);
        productManageFeign.saveProPayType(proPayType);

        if (proDto.getProType() == 2) {
            ProPackage proPackage = new ProPackage();
            proPackage.setProId(proId);
            proPackage.setPackageName(proDto.getTcName());
            proPackage.setPackagePrice(new BigDecimal(proDto.getTcPrice().toString()));
            productManageFeign.savePackage(proPackage);
        }
        //插入proDetails
        ProDetails proDetails = new ProDetails();
        proDetails.setProId(proId);
        proDetails.setSpecs(proDto.getSpecs());
        proDetails.setDetails(proDto.getDetails());
        productManageFeign.saveProDetails(proDetails);

        //插入proChannelRelatiom
        ProChannelRelation proChannelRelation = new ProChannelRelation();
        proChannelRelation.setProId(proId);
        proChannelRelation.setProName(proDto.getProName());
        proChannelRelation.setChannelId(proDto.getChannelId());
        proChannelRelation.setChannelName(proDto.getChannelName());
        productManageFeign.saveProChannelRelation(proChannelRelation);

        List<String> propertys = proDto.getPropertyName();
        int ss = propertys.size();
        List<String> values = proDto.getPropertyValue();

        //插入属性和属性值
        List<Long> propertyIds = new ArrayList<>();
        for (int i = 0; i < propertys.size(); i++
        ) {
            String propertyName = propertys.get(i);
            ProProperty proProperty = new ProProperty();
            proProperty.setProId(proId);
            proProperty.setPropertyName(propertyName);
            Long propertyId = productManageFeign.saveProerty(proProperty);
            propertyIds.add(propertyId);
            String propertyValue = values.get(i);
            String[] valueNames = propertyValue.split(";");
            for (String valueName : valueNames
            ) {
                ProPropertyValue proPropertyValue = new ProPropertyValue();
                proPropertyValue.setPropertyId(propertyId);
                proPropertyValue.setValueText(valueName);
                productManageFeign.savePropertyValue(proPropertyValue);
            }
        }

        //插入proImage
        String imgUrls = proDto.getImgUrl();
        String[] str = URLDecoder.decode(imgUrls).split(";");
        for (int i = 0; i < str.length; i++
        ) {
            ProImage proImage = new ProImage();
            proImage.setProId(proId);
            proImage.setImgUrl(str[i]);
            if (i == 0)
                proImage.setIsDefault(1);
            else
                proImage.setIsDefault(0);
            productManageFeign.saveProImage(proImage);
        }
        ProSku proSku = null;

        //添加sku
        String imgUrl = proDto.getSkuImgFiles();
        if (StringUtils.isNotEmpty(imgUrl)) {
            imgUrl = URLDecoder.decode(imgUrl);
        }
        String img[] = imgUrl.split(";");
        for (int i = 0; i < proDto.getCostPrice().size(); i++) {
            proSku = new ProSku();
            String groupNo = "";
            for (int j = 0; j < propertyIds.size(); j++
            ) {
                groupNo = groupNo + propertyIds.get(j) + ":" + productManageFeign.getValueId(propertyIds.get(j), proDto.getPro().get(i * ss + j)) + ",";
            }
            groupNo = groupNo.substring(0, groupNo.length() - 1);
            proSku.setGroupNo(groupNo);
            proSku.setProId(proId);
            proSku.setCostPrice(proDto.getCostPrice().get(i));
            proSku.setMarketPrice(proDto.getMarketPrice().get(i));
            proSku.setImgUrl(img[i]);

            proSku.setPrice(proDto.getPrice().get(i));
            proSku.setStock(proDto.getStock().get(i));
            if (proDto.getProType() == 0) {
                proSku.setMinSellPrice(proDto.getMinSellPrice().get(i));
            }
            if (proDto.getProType() == 1) {
                proSku.setSeckillPrice(proDto.getKillPrice().get(i));
                proSku.setSeckillStock(proDto.getKillStock().get(i));
            }
            String json = JSON.toJSONString(proSku);
            productManageFeign.saveProSku(json);
            //保存sku

        }
        return AjaxResult.success();
    }

    /**
     * 修改商品
     */
    @GetMapping("/edit/{proId}")
    public String edit(@PathVariable("proId") Long proId, ModelMap mmap) {
        ResponseInfo<AllProDto> res = productManageFeign.selectAllProDtoById(proId);
        AllProDto allProDto = res.getData();
        logger.info("======queryProduct=={}==", JSONObject.toJSONString(res.getData()));
        allProDto.setPropertyValuesStr(JSONObject.toJSONString(allProDto.getPropertyValues()));
        allProDto.setProPropertiesStr(JSONObject.toJSONString(allProDto.getProProperties()));
        allProDto.setProImagesStr(JSONObject.toJSONString(allProDto.getProImages()));
        allProDto.setPayTypeStr(allProDto.getPayType());
        mmap.put("allProDto", allProDto);
        return prefix + "/edit";
    }

    @PostMapping("/getSetting")
    @ResponseBody
    public List<String> getSetting(@RequestParam(value = "list") String list) {
        List<List<String>> lists = new ArrayList<>();
        JSONArray jsonArray = JSONObject.parseArray(list);
        List<String> resultList = new ArrayList<>();
        String string = null;
        if (jsonArray == null || jsonArray.isEmpty()) {
            return null;
        }
        //如果只有一条,直接返回,不走先逻辑
        if (jsonArray.size() == 1) {
            return Arrays.asList(jsonArray.getString(0).replace("；", "").split(";"));
        }
        resultList = DescartesUtil.createSkuList(Arrays.asList(jsonArray.getString(0).replace("；", "").split(";")), Arrays.asList(jsonArray.getString(1).replace("；", "").split(";")));

        String tempStr[] = null;
        for (int i = 2; i < jsonArray.size(); i++) {
            tempStr = jsonArray.getString(i).replace("；", "").split(";");
            resultList = DescartesUtil.createSkuList(resultList, Arrays.asList(tempStr));
        }
        return resultList;
    }

    /**
     * 修改保存商品
     */
    @RequiresPermissions("system:product:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProDto proDto) {
        for (int i = 0; i < proDto.getCostPrice().size(); i++) {
            if (proDto.getProType() == 2 && proDto.getPrice().get(i).compareTo(new BigDecimal(230)) == -1) {
                return AjaxResult.error("套餐商品价格过低,请重新输入");
            } else {
                continue;
            }
        }
//        String[] propertyId = proDto.getPropertyIds().split(";");
//        String[] valueId = proDto.getValueIds().split(";");
//        String[] imgId = proDto.getImgIds().split(";");
//        String[] skuId = proDto.getSkuIds().split(";");
        Product product = new Product();
        //修改product
        product.setProId(proDto.getProId())
                .setProName(proDto.getProName())
                .setProNo(proDto.getProNo())
                .setSortNumber(proDto.getSortNumber())
                .setProTitle(proDto.getProTitle())
                .setUnit(proDto.getUnit())
                .setWeight(proDto.getWeight())
                .setProType(proDto.getProType())
                .setCouponType(proDto.getCouponType())
                .setCouponTypeName(proDto.getCouponTypeName());
        productManageFeign.updateProduct(product);

        //修改proPayType
        List<Integer> type = proDto.getPayType();
        String payType = "";
        for (int i : type
        ) {
            payType = payType + i + ",";
        }
        payType = payType.substring(0, payType.length() - 1);
        Long id = productManageFeign.findIdByPayType(payType);
        ProPayType proPayType = new ProPayType();
        proPayType.setId(proDto.getProPayTypeId()).setProId(proDto.getProId()).setPayTypeId(id);
        productManageFeign.updateProPayType(proPayType);
        //修改套餐
        if (proDto.getProType() == 2) {
            ProPackage proPackage = new ProPackage();
            proPackage.setPackageId(proDto.getPackageId());
            proPackage.setProId(proDto.getProId());
            proPackage.setPackageName(proDto.getTcName());
            proPackage.setPackagePrice(new BigDecimal(proDto.getTcPrice().toString()));
            productManageFeign.updatePackage(proPackage);
        }
        //修改proDetails
        ProDetails proDetails = new ProDetails();
        proDetails.setId(proDto.getDetailsId());
        proDetails.setProId(proDto.getProId());
        proDetails.setSpecs(proDto.getSpecs());
        proDetails.setDetails(proDto.getDetails());
        productManageFeign.updateProDetails(proDetails);

        //修改proChannelRelatiom
        ProChannelRelation proChannelRelation = new ProChannelRelation();
        proChannelRelation.setId(proDto.getProChannelRelationId());
        proChannelRelation.setProId(proDto.getProId());
        proChannelRelation.setChannelId(proDto.getChannelId());
        productManageFeign.updateProchannelRelation(proChannelRelation);

        List<String> propertys = proDto.getPropertyName();
        int ss = propertys.size();
        List<String> values = proDto.getPropertyValue();

        //修改属性和属性值
        List<Long> propertyIds = new ArrayList<>();
        for (int i = 0; i < propertys.size(); i++
        ) {
            String propertyName = propertys.get(i);
            ProProperty proProperty = new ProProperty();
            if (i <= proDto.getPropertyIds().size() - 1) {
                proProperty.setPropertyId(Long.valueOf(proDto.getPropertyIds().get(i)));
            }
            proProperty.setProId(proDto.getProId());
            proProperty.setPropertyName(propertyName);
            productManageFeign.updateProerty(proProperty);
            propertyIds.add(proProperty.getPropertyId());
            String propertyValue = values.get(i);
            String[] valueNames = propertyValue.split(";");
            for (int j = 0; j < valueNames.length; j++
            ) {
                ProPropertyValue proPropertyValue = new ProPropertyValue();
                if (j <= proDto.getValueIds().get(i).split(";").length - 1) {
                    proPropertyValue.setValueId(Long.valueOf((proDto.getValueIds().get(i).split(";"))[j]));
                }
                proPropertyValue.setPropertyId(Long.valueOf(proDto.getPropertyIds().get(i)));
                proPropertyValue.setValueText(valueNames[j]);
                productManageFeign.updatePropertyValue(proPropertyValue);
            }
        }

        //修改proImage
        String imgUrls = proDto.getImgUrl();
        String[] str = URLDecoder.decode(imgUrls).split(";");
        for (int i = 0; i < str.length; i++
        ) {
            ProImage proImage = new ProImage();
            proImage.setId(Long.valueOf(proDto.getImgIds().get(i)));
            proImage.setProId(proDto.getProId());
            proImage.setImgUrl(str[i]);
            if (i == 0)
                proImage.setIsDefault(1);
            else
                proImage.setIsDefault(0);
            productManageFeign.updateProImage(proImage);
        }
        ProSku proSku = null;

        //修改sku
        String imgUrl = proDto.getSkuImgFiles();
        if (StringUtils.isNotEmpty(imgUrl)) {
            imgUrl = URLDecoder.decode(imgUrl);
        }
        String img[] = imgUrl.split(";");
        for (int i = 0; i < proDto.getCostPrice().size(); i++) {
            proSku = new ProSku();
            String groupNo = "";
            for (int j = 0; j < propertyIds.size(); j++
            ) {
                groupNo = groupNo + propertyIds.get(j) + ":" + productManageFeign.getValueId(propertyIds.get(j), proDto.getPro().get(i * ss + j)) + ",";
            }
            groupNo = groupNo.substring(0, groupNo.length() - 1);
            Long skuId = productManageFeign.getSkuIdByProIdAndGroupNo(proDto.getProId(), groupNo).getData();
            if (skuId != null) {
                proSku.setSkuId(skuId);
            }
//            proSku.setSkuId(Long.valueOf(skuId[i]));
            proSku.setGroupNo(groupNo);
            proSku.setProId(proDto.getProId());
            proSku.setPrice(proDto.getPrice().get(i));
            proSku.setCostPrice(proDto.getCostPrice().get(i));
            proSku.setMarketPrice(proDto.getMarketPrice().get(i));
            proSku.setImgUrl(img[i]);
            proSku.setPrice(proDto.getPrice().get(i));
            proSku.setStock(proDto.getStock().get(i));
            if (proDto.getProType() == 0) {
                proSku.setMinSellPrice(proDto.getMinSellPrice().get(i));
            }
            if (proDto.getProType() == 1) {
                proSku.setSeckillPrice(proDto.getKillPrice().get(i));
                proSku.setSeckillStock(proDto.getKillStock().get(i));
            }
            String json = JSON.toJSONString(proSku);
            productManageFeign.updateProSku(json);
            //保存sku

        }
        return AjaxResult.success();
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("system:product:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long[] ids) {
        return toAjax(productManageFeign.deleteProduct(ids).getCode());
    }


    /**
     * JD批量商品上架
     */
    @RequiresPermissions("system:product:upperShelf")
    @Log(title = "商品上架", businessType = BusinessType.UPDATE)
    @PostMapping("/upperShelf")
    @ResponseBody
    public AjaxResult upperShelf(@RequestBody UpperShelfDto upperShelf) {
        List<ShelfGoodsParam> shelfGoods = upperShelf.getList();
        ResponseInfo<String> result = productDockingFeign.shelfGoods(shelfGoods);
        if (result.isSuccess()) {
            return success("上架成功！");
        }
        return error("很抱歉！上架失败!");
    }


    /**
     * JD批量商品下架
     */
    @RequiresPermissions("system:product:lowerShelf")
    @Log(title = "商品下架", businessType = BusinessType.UPDATE)
    @DeleteMapping("/lowerShelf")
    @ResponseBody
    public AjaxResult lowerShelf(@RequestParam("skuIds") String skuIds, @RequestParam("shelf_id") int shelf_id) {
        ResponseInfo<String> result = productDockingFeign.deleteSG(skuIds, shelf_id);
        if (result.isSuccess()) {
            return success("上架成功！");
        }
        return error("很抱歉！下架失败!");
    }


    /**
     * 修改商品状态
     *
     * @param ids    商品ID 多个使用逗号拼接
     * @param status
     * @return
     */
    @GetMapping("/updateStatus")
    @ResponseBody
    public AjaxResult thisUpperShelf(@RequestParam("ids") String ids, @RequestParam("status") String status) {
        if (StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(status)) {
            if (status.equals("0")) { //修改为上架
                if (productManageFeign.reshelfProduct(ids).isSuccess())
                    return success("商品上架成功！");
                return error("商品上架失败!");
            } else if (status.equals("1")) { //修改为下架
                if (productManageFeign.instockProduct(ids).isSuccess())
                    return success("商品下架成功！");
                return error("商品下架失败!");
            } else if (status.equals("98")) { //修改为京东上架
                if (productManageFeign.reshelfJdProduct(ids).isSuccess())
                    return success("商品上架京东成功！");
                return error("商品上架京东失败!");
            }
        }
        return error("请求参数有误！");
    }

}
