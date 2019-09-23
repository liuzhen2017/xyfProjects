package com.xinyunfu.product.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.feign.ProductDockingFeign;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.*;
import com.xinyunfu.product.utils.JsonUtils;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IProDetailsService detailsService;
    @Autowired
    private IPropertyService propertyService;
    @Autowired
    private IPropertyValueService propertyValueService;
    @Autowired
    private IFreightService freightService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private IPackageService packageService;
    @Autowired
    private IProPayTypeService proPayTypeService;
    @Autowired
    private ProductDockingFeign productDockingFeign;
    @Autowired
    private IChannelRelationService channelRelationService;
    @Autowired
    private IPayTypeService payTypeService;


    /**
     * 新增商品
     *
     * @param product
     * @return proId
     */
    @PostMapping("/save")
    public Long saveProduct(@RequestBody Product product) {
        boolean flag = productService.save(product);
        if (flag)
            return product.getProId();
        return null;
    }

//    @PostMapping("/savaProductAll")
//    public ResponseInfo<String> savaProductAll(@RequestBody JSONObject json) {
//        Product product = json.toJavaObject(json, Product.class);
//        //新增product
//        Long proId = this.saveProduct(product);
//        if (proId == null) {
//            return ResponseInfo.error("新增product失败");
//        }
//        ProDetails proDetails = json.toJavaObject(json, ProDetails.class);
//        proDetails.setProId(proId);
//        //新增proDetails
//        detailsService.saveProDetails(proDetails);
//
//        ProChannelRelation proChannelRelation = json.toJavaObject(json, ProChannelRelation.class);
//        proChannelRelation.setProId(proId);
//        //新增proChannelRelation
//        if (!channelRelationService.save(proChannelRelation)) {
//            return ResponseInfo.error("新增proChannelRelation失败");
//        }
//        String proImageKey = "proImages";
//        if (!json.containsKey(proImageKey)) {
//            return ResponseInfo.error("没有新增商品图片");
//        }
//        List<ProImage> proImages = JSONArray.parseArray(json.getJSONArray(proImageKey).toJSONString(), ProImage.class);
//        if (proImages == null || proImages.isEmpty()) {
//            return ResponseInfo.error("没有新增商品图片");
//        }
//        for (ProImage proImage : proImages) {
//            proImage.setProId(proId);
//            if (!fileService.saveOrUpdate(proImage)) {
//                log.info("===========新增imgUrl={}失败=============", proImage.getImgUrl());
//                return ResponseInfo.error("新增商品图片失败");
//            }
//        }
//        String propertyKey = "properties";
//        if (!json.containsKey(propertyKey)) {
//            log.warn("===========商品没有属性==============");
//        }
//        List<ProProperty> properties = JSONArray.parseArray(json.getJSONArray(propertyKey).toJSONString(), ProProperty.class);
//        if (properties == null || !properties.isEmpty()) {
//
//        }
//            List<Long> propertyIds = new ArrayList<>();
//            for (ProProperty property : properties) {
//                property.setProId(proId);
//                Long propertyId = propertyService.saveProperty(property);
//                if (propertyId == null) {
//                    log.info("===========新增propertyName={}失败=============", property.getPropertyName());
//                    return ResponseInfo.error("新增商品属性失败");
//                }
//                propertyIds.add(propertyId);
//            }
//        }
//        String valuekey = "values";
//        if (json.containsKey(valuekey)) {
//            List<ProPropertyValue> values = JSONArray.parseArray(json.getJSONArray(valuekey).toJSONString(), ProPropertyValue.class);
//            if (values != null && !values.isEmpty()) {
//                List<>
//            }
//        }
//
//    }


    //======================================

    /**
     * 新增商品 京东调用
     *
     * @param product
     * @return proId
     */
    @PostMapping("/saveProduct")
    public ResponseInfo<String> save(@RequestBody Product product) {
        if (productService.save(product))
            return ResponseInfo.success("新增成功!");
        return ResponseInfo.error("新增失败!");
    }


    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProduct(@RequestBody Product product) {
        if (productService.saveOrUpdate(product)) {
            return ResponseInfo.success("修改成功");
        } else {
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
    public ResponseInfo<IPage<Product>> findProductByPage(@RequestBody Product product,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize) {
        IPage<Product> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        if (product.getProName() != null && StringUtils.isNotEmpty(product.getProName())) {
            wrapper.eq(Product::getProName, product.getProName());
        }
        if (product.getProTitle() != null && StringUtils.isNotEmpty(product.getProTitle())) {
            wrapper.eq(Product::getProTitle, product.getProTitle());
        }
        if (product.getStatus() != null) {
            wrapper.eq(Product::getStatus, product.getStatus());
        }
        if (product.getStoreId() != null) {
            wrapper.eq(Product::getStoreId, product.getStoreId());
        }
        if (product.getStoreName() != null && StringUtils.isNotEmpty(product.getStoreName())) {
            wrapper.eq(Product::getStoreName, product.getStoreName());
        }
//        wrapper.ne(Product::getStatus,2);
        return ResponseInfo.success(productService.page(pages, wrapper));
    }

    /**
     * 删除商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteProduct(@RequestParam("ids") Long[] ids) {
        return productService.deleteProduct(ids, 2);
    }

    /**
     * 下架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProduct(@RequestParam("ids") String ids) {
        return productService.updateStatus(ids, 1);
    }

    /**
     * 上架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProduct(@RequestParam("ids") String ids) {
        return productService.updateStatus(ids, 0);
    }

    /**
     * 上架京东商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelfJd")
    public ResponseInfo<String> reshelfJdProduct(@RequestParam("ids") String ids) {
        return productService.updateStatus(ids, 98);
    }

    /**
     * 修改商品状态为待审核
     *
     * @param ids
     * @return
     */
    @GetMapping("/updateToCheck")
    ResponseInfo<String> updateToCheck(@RequestParam("ids") String ids) {
        return productService.updateStatus(ids, 99);
    }

    /**
     * 根据proId查询单个商品信息
     */
    @PostMapping("/queryProductDto")
    public ResponseInfo<ProductDTO> findProductDtoById(@RequestBody ProIdDTO proIdDto) {
        if (proIdDto == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            ProductDTO productDTO = productService.findProductDtoById(proIdDto);
            if (productDTO == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(productDTO);
        }

    }

    /**
     * 根据三级分类id分页查询该分类下的所有商品
     */
    @PostMapping("/queryProDtosByChannelId")
    public ResponseInfo<IPage<ProDto>> findProDtoByChannelId(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            IPage<ProDto> pages = productService.getProDtoPageByChannelId(channelPageDTO);
            if (pages == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(pages);
        }
    }

    /**
     * 根据三级分类id查询该分类下的所有商品并按销量降序排列
     */
    @PostMapping("/queryProDtosByChannelIdOrderByAllSellStockDesc")
    public ResponseInfo<IPage<ProDto>> findProDtoOrderByAllSellStockDesc(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            IPage<ProDto> pages = productService.getProDtoPageByChannelIdOrderByAllSellStockDesc(channelPageDTO);
            if (pages == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(pages);
        }
    }


    /**
     * 根据三级分类id查询该分类下的所有商品并按价格降序排列
     */
    @PostMapping("/queryProDtosByChannelIdOrderByPriceDesc")
    public ResponseInfo<IPage<ProDto>> findProDtoOrderByPriceDesc(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            IPage<ProDto> pages = productService.getProDtoPageByChannelIdOrderByPriceDesc(channelPageDTO);
            if (pages == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(pages);
        }
    }


    /**
     * 根据三级分类id查询该分类下的所有商品并按价格升序排列
     */
    @PostMapping("/queryProDtosByChannelIdOrderByPriceAsc")
    public ResponseInfo<IPage<ProDto>> findProDtoOrderByPriceAsc(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            IPage<ProDto> pages = productService.getProDtoPageByChannelIdOrderByPriceAsc(channelPageDTO);
            if (pages == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(pages);
        }
    }


    /**
     * 查询支付方式和支持的券
     */
    @GetMapping("/queryPayTypeAndCouponType")
    public ResponseInfo<Map<Long, List<String>>> queryPayTypeAndCouponType(@RequestParam("str") String str) {
        if (StringUtils.isEmpty(str)) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            String[] split = str.split(";");
            List<Long> idsList = new ArrayList<>();
            for (String string : split) {
                idsList.add(Long.valueOf(string));
            }
            Map<Long, List<String>> map = productService.queryPayTypeAndCouponType(idsList);
            if (map.isEmpty())
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(map);
        }


    }

    /**
     * 根据skuId和数量查询邮费级其他商品信息
     */
    @GetMapping("/findFreightDTO")
    public ResponseInfo<Object> findFreightDTO(@RequestParam("str") String str) {
        if (StringUtils.isEmpty(str)) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            FindFreightDTO findFreightDTO = JsonUtils.parse(str, FindFreightDTO.class);
            Map<String, Integer> map = findFreightDTO.getMap();
            List<FreightDTO> list = new ArrayList<>();
            Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                FreightDTO freightDTO = new FreightDTO();
                long skuId = Long.valueOf(entry.getKey());
                int num = entry.getValue();
                ProSku proSku = skuService.findOneById(skuId).getData();
                String skuDesc = skuService.getSkuDesc(skuId);
                String proName = productService.getById(proSku.getProId()).getProName();
                Product product = productService.getById(proSku.getProId());
                BigDecimal postage;
                if (product.getSource() == 1) {
                    String addres = findFreightDTO.getAddres();
                    if (StringUtils.isNotEmpty(addres)) {
                        String[] split = addres.split("-");
                        JDSkuNumsParam skuNums = new JDSkuNumsParam(skuId, num);
                        List<JDSkuNumsParam> jdSkuNumsParams = new ArrayList<>();
                        jdSkuNumsParams.add(skuNums);
                        log.info("===========skuNums={}=======", skuNums);
                        String top = split.length > 3 ? split[3] : null;
                        JDPostParam jdPostParam = new JDPostParam(jdSkuNumsParams, split[0], split[1], split[2], top);
                        log.info("=================jdpostparam={}==============", jdPostParam);
                        ResponseInfo<String> res = productDockingFeign.findJDFreight(jdPostParam);
                        if (res.isSuccess()) {
                            log.info("=========data={}========", res.getData().getClass().getName());
                            postage = new BigDecimal(res.getData());
                        } else {
                            return new ResponseInfo(res.getCode(), res.getMessage(), res.getData());
                        }
                    } else {
                        postage = new BigDecimal("0");
                    }

                } else {
                    postage = freightService.queryPostageByIdAndCount(findFreightDTO.getProvince(), findFreightDTO.getCity(), proSku.getProId(), skuId, entry.getValue());
                }
                Store store = storeService.getById(product.getStoreId());
                Long storeId = store.getStoreId();
                String storeName = store.getStoreName();
                String imgUrl = fileService.findDefaultImageByProId(proSku.getProId());
                int stock;
                BigDecimal price;
                if (product.getProType() == 1) {
                    stock = proSku.getSeckillStock();
                    price = proSku.getSeckillPrice();
                } else {
                    stock = proSku.getStock();
                    price = proSku.getPrice();
                }
//                if (num>stock){
//                    log.warn("============skuId为skuId={}的商品库存不足=====",skuId);
//                    return new ResponseInfo("1111","skuId为"+skuId+"的商品库存不足,下单失败","库存不足");
//                }
                freightDTO.setPrice(price)
                        .setCostPrice(proSku.getCostPrice())
                        .setMinSellPrice(proSku.getMinSellPrice())
                        .setProId(proSku.getProId())
                        .setPostage(postage)
                        .setSkuId(skuId)
                        .setStock(stock)
                        .setStatus(proSku.getStatus())
                        .setImgUrl(imgUrl)
                        .setProName(proName)
                        .setStoreId(storeId)
                        .setSkuDesc(skuDesc)
                        .setStoreName(storeName)
                        .setSource(product.getSource());
                list.add(freightDTO);
            }
            if (list == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(list);
        }
    }

    /**
     * 查询所有来源为京东,状态为0的商品
     *
     * @return
     */
    @GetMapping("/queryJDProReshelf")
    public ResponseInfo<Map<String, Product>> queryJDProInstock() {
        return ResponseInfo.success(productService.queryJDProInstock());
    }

    /**
     * 根据proNo集合查询proId
     *
     * @param proNos
     * @return
     */
    @GetMapping("/queryProIdByProNo")
    public ResponseInfo<Map<String, Long>> queryProIdByProNo(String[] proNos) {
        return ResponseInfo.success(productService.queryProIdByProNo(proNos));
    }

    /**
     * 根据商品编号查询商品信息
     *
     * @param proNo
     * @return
     */
    @GetMapping("/queryProductByProNo")
    public ResponseInfo<Product> queryProductByProNo(@RequestParam("proNo") String proNo) {
        return ResponseInfo.success(productService.queryProductByProNo(proNo));
    }

    /**
     * 查询所有来源为京东,状态为0的商品
     *
     * @return
     */
    @GetMapping("/getJDProReshelf")
    public ResponseInfo<Map<Long, Product>> getJDProInstock() {
        return ResponseInfo.success(productService.getJDProInstock());
    }


    /**
     * 根据商品编号查询商品是否已存在
     */
    @GetMapping("/checkProByProNo")
    public ResponseInfo<Boolean> checkProByProNo(@RequestParam("proNo") String proNo) {
        return ResponseInfo.success(productService.checkProByProNo(proNo));
    }

    /**
     * 根据分类id查询所有的秒杀商品
     *
     * @param channelPageDTO
     * @return
     */
    @PostMapping("/findSeckillProDtoByChannelId")
    public ResponseInfo<PageVO<ProDto>> findSeckillProDtoByChannelId(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            Long channelId = channelPageDTO.getChannelId();
            List<ProDto> list = productService.queryProDtosListByChannelId(channelId);
            PageVO<ProDto> page = new PageVO<>(list, channelPageDTO.getPageSize());
            page.setCurrent_page(channelPageDTO.getPage());
            page.setSize(channelPageDTO.getPageSize());
            List<ProDto> currentPageData = page.currentPageData();
            page.setCurrentPageData(currentPageData);
            if (page == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(page);
        }

    }


    /**
     * 根据商品编号查询商品id
     *
     * @param proNo
     * @return
     */
    @GetMapping("/getProIdByProNo")
    public ResponseInfo<Long> getProIdByProNo(@RequestParam("proNo") String proNo) {
        return ResponseInfo.success(productService.getProIdByProNo(proNo));
    }


    @GetMapping("/selectProductById/{proId}")
    public Product selectProductById(@PathVariable("proId") Long proId) {
        return productService.getById(proId);
    }


    @PostMapping("/findRProductDTOByPage")
    ResponseInfo<Page<RProductDTO>> findRProductDTOByPage(@RequestBody RProductDTO rProductDTO,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize) {
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;
        return ResponseInfo.success(productService.getRProductDTO(rProductDTO, page, pageSize));

    }

    /**
     * 根据关键字查询商品分页列表
     *
     * @param pageByProNameDTO
     * @return
     */
    @PostMapping("/searchByProName")
    public ResponseInfo<IPage<ProDto>> findProDtoByProName(@RequestBody PageByProNameDTO pageByProNameDTO) {
        if (pageByProNameDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        }
        IPage<ProDto> page = productService.findProDtoByProName(pageByProNameDTO);
        if (page == null) {
            return new ResInfo(Res.NO_DATA);
        }
        return ResponseInfo.success(page);
    }


    @GetMapping("/findAllProDto")
    public ResponseInfo<AllProDto> findAllProDto(@RequestParam("proId") Long proId) {
        Product product = productService.findProductById(proId);
        ProDetails proDetails = detailsService.findProDetailsByProId(proId);
        ProChannelRelation proChannelRelation = channelRelationService.findChannelRelationByProId(proId);
        ProPackage proPackage = packageService.findProPackageByProId(proId);
        ProPayType proPayType = proPayTypeService.findProPayTypeByProId(proId);
        List<ProImage> proImages = fileService.findImageByProId(proId);
        List<ProProperty> proProperties = propertyService.findPropertyByProId(proId).getData();
        List<List<ProPropertyValue>> propertyValues = propertyValueService.findPropertyValueByProId(proId);
        List<String> proSkuJsons = skuService.findSkusByProId(proId);
        String payType = payTypeService.findPayTypeByPayTypeId(proPayType.getPayTypeId());
        return ResponseInfo.success(new AllProDto(product, proPayType, payType, proChannelRelation, proPackage, proDetails, proImages, proProperties, propertyValues, proSkuJsons));

    }


/**
 * edit by lhpu
 * ===========================================================================================================
 */


    /**
     * 根据来源、状态查询商品
     * 查询所有来源为京东,状态为99（待审核）的商品Id
     *
     * @return
     */
    @GetMapping("/getJDProductIds")
    public ResponseInfo<List<Long>> getJDProductIds(@RequestParam("resource") int source, @RequestParam("status") int status) {
        return ResponseInfo.success(productService.getJDProductIds(source, status));
    }


    @GetMapping("/offSaleByproNo")
    public ResponseInfo<String> offSaleByproNo(@RequestParam("proNo") String proNo) {
        if (productService.offSaleByproNo(proNo)) {
            return ResponseInfo.success("下架成功");
        }
        return ResponseInfo.error("下架失败");
    }


    @GetMapping("/offSaleByproId")
    public ResponseInfo<String> offSaleByproId(@RequestParam("proId") Long proId) {
        if (productService.offSaleByproId(proId)) {
            return ResponseInfo.success("下架成功");
        }
        return ResponseInfo.error("下架失败");
    }

    @GetMapping("/getProductBySkuNo")
    public ResponseInfo<Product> getProductBySkuNo(@RequestParam("skuNo") String skuNo) {
        return ResponseInfo.success(productService.getProductBySkuNo(skuNo));
    }


}



