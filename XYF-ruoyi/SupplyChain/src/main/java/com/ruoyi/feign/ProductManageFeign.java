package com.ruoyi.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.domain.*;
import com.ruoyi.dto.*;
import com.ruoyi.util.Res;
import com.ruoyi.utils.ResponseInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author TYM
 * @date 2019/8/2
 * @Description :商品微服务
 */
@FeignClient(value = "ProductManageX", decode404 = true)
public interface ProductManageFeign {
//===========================商品crud=======================

    /**
     * 新增商品
     *
     * @param product
     * @return
     */
    @PostMapping("/product/save")
    public Long saveProduct(@RequestBody Product product);

    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    @PostMapping("/product/update")
    public ResponseInfo<String> updateProduct(@RequestBody Product product);

    /**
     * 分页查询商品
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/product/queryByPage")
    public ResponseInfo<Page<Product>> findProductByPage(@RequestBody Product product,
                                                         @RequestParam("page") Integer page,
                                                         @RequestParam("pageSize") Integer pageSize);

    /**
     * 删除商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/product/delete")
    public ResponseInfo<String> deleteProduct(@RequestParam("ids") Long[] ids);

    /**
     * 下架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/product/instock")
    public ResponseInfo<String> instockProduct(@RequestParam("ids") String ids);

    /**
     * 上架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/product/reshelf")
    public ResponseInfo<String> reshelfProduct(@RequestParam("ids") String ids);


    @GetMapping("/product/selectProductById/{proId}")
    public Product selectProductById(@PathVariable("proId") Long proId);

//==============================商品详情crud==========================================

    /**
     * 新增商品详情
     *
     * @param proDetails
     * @return
     */
    @PostMapping("/proDetails/save")
    public ResponseInfo<String> saveProDetails(@RequestBody ProDetails proDetails);

    /**
     * 修改商品详情
     *
     * @param proDetails
     * @return
     */
    @PostMapping("/proDetails/update")
    public ResponseInfo<String> updateProDetails(@RequestBody ProDetails proDetails);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/proDetails/queryByPage")
    public ResponseInfo<IPage<ProDetails>> findProDetailsByPage(@RequestBody ProDetails proDetails,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize);

    /**
     * 下架商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/proDetails/instock")
    public ResponseInfo<String> instockProDetails(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/proDetails/reshelf")
    public ResponseInfo<String> reshelfProdetails(@RequestParam("ids") Long[] ids);

    /**
     * 删除商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/proDetails/delete")
    public ResponseInfo<String> deleteProDetails(@RequestParam("ids") Long[] ids);

//============================商品属性crud===========================================

    /**
     * 新增商品属性
     *
     * @param proProperty
     * @return
     */
    @PostMapping("/property/save")
    public Long saveProerty(@RequestBody ProProperty proProperty);

    /**
     * 修改商品属性
     *
     * @param proProperty
     * @return
     */
    @PostMapping("/property/update")
    public ResponseInfo<String> updateProerty(@RequestBody ProProperty proProperty);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/property/queryByPage")
    public ResponseInfo<IPage<ProProperty>> findPropertyByPage(@RequestBody ProProperty proProperty,
                                                               @RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize);

    /**
     * 下架商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/property/instock")
    public ResponseInfo<String> instockProperty(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/property/reshelf")
    public ResponseInfo<String> reshelfProperty(@RequestParam("ids") Long[] ids);

    /**
     * 删除商品属性
     *
     * @param ids
     * @return
     */
    @GetMapping("/property/delete")
    public ResponseInfo<String> deleteProperty(@RequestParam("ids") Long[] ids);

//======================================商品属性值crud==========================================

    /**
     * 新增商品属性值
     *
     * @param
     * @return
     */
    @PostMapping("/propertyValue/save")
    public ResponseInfo<String> savePropertyValue(@RequestBody ProPropertyValue proPropertyValue);

    /**
     * 修改商品属性值
     *
     * @param
     * @return
     */
    @PostMapping("/propertyValue/update")
    public ResponseInfo<String> updatePropertyValue(@RequestBody ProPropertyValue proPropertyValue);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/propertyValue/queryByPage")
    public ResponseInfo<IPage<ProProperty>> findPropertyValueByPage(@RequestBody ProPropertyValue proPropertyValue,
                                                                    @RequestParam("page") Integer page,
                                                                    @RequestParam("pageSize") Integer pageSize);

    /**
     * 下架商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/propertyValue/instock")
    public ResponseInfo<String> instockPropertyValue(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/propertyValue/reshelf")
    public ResponseInfo<String> reshelfPropertyValue(@RequestParam("ids") Long[] ids);

    /**
     * 删除商品属性值
     *
     * @param ids
     * @return
     */
    @GetMapping("/propertyValue/delete")
    public ResponseInfo<String> deleteProertyValue(@RequestParam("ids") Long[] ids);

//======================================商品skuCRUD==========================================

    /**
     * 新增商品sku
     *
     * @param proSku
     * @return
     */
    @PostMapping("/sku/save")
    public ResponseInfo<String> saveProSku(@RequestBody String json);

    /**
     * 修改商品sku
     *
     * @param proSku
     * @return
     */
    @PostMapping("/sku/update")
    public ResponseInfo<String> updateProSku(@RequestBody String json);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/proSku/queryByPage")
    public ResponseInfo<Page<ProSku>> findProPropertyByPage(@RequestBody ProSku proSku,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("pageSize") Integer pageSize);

    /**
     * 下架商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/proSku/instock")
    public ResponseInfo<String> instockProSku(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/proSku/reshelf")
    public ResponseInfo<String> reshelfProSku(@RequestParam("ids") Long[] ids);

    /**
     * 删除商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/proSku/delete")
    public ResponseInfo<String> deleteProSku(@RequestParam("ids") Long[] ids);

//======================================商品分类关系crud==========================================

    /**
     * 批量新增商品分类关系
     *
     * @param proChannelRelation
     * @return
     */
    @PostMapping("/channelRelation/save")
    public ResponseInfo<String> saveProChannelRelations(@RequestBody ProChannelRelationDTO proChannelRelationDTO);

    /**
     * 修改商品分类关系
     *
     * @param proChannelRelation
     * @return
     */
    @PostMapping("/channelRelation/update")
    public ResponseInfo<String> updateProchannelRelation(@RequestBody ProChannelRelation proChannelRelation);

    /**
     * 分页查询
     *
     * @param proChannelRelation
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/channelRelation/queryByPage")
    public ResponseInfo<Page<ProChannelRelation>> findProChannelRelationByPage(@RequestBody ProChannelRelation proChannelRelation,
                                                                               @RequestParam("page") Integer page,
                                                                               @RequestParam("pageSize") Integer pageSize);

    /**
     * 下架商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/channelRelation/instock")
    public ResponseInfo<String> instockProChannelRelation(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/channelRelation/reshelf")
    public ResponseInfo<String> reshelfProChannelRelation(@RequestParam("ids") Long[] ids);

    /**
     * 删除商品分类关系
     *
     * @param ids
     * @return
     */
    @GetMapping("/channelRelation/deletePCR")
    public ResponseInfo<String> deleteProChannelRelation(@RequestParam("ids") String ids);

//========================app配置============================

//    /**
//     * 配置活动商品
//     *
//     * @param proChannelRelation
//     * @return
//     */
//    @PostMapping("/proChannelRelation/save")
//    public ResponseInfo<String> saveProChannelRelation(ProChannelRelation proChannelRelation);

    /**
     * 配置首页广告
     *
     * @return
     */
    @PostMapping("/banner/updateBannerStartAndEnd")
    public ResponseInfo<String> updateBannerStartAndEnd(@RequestBody ProBanner proBanner);

//====================================广告crud=============================================

    /**
     * 新增广告
     *
     * @param proBanner
     * @return
     */
    @PostMapping("/banner/save")
    public ResponseInfo<String> saveBanner(@RequestBody BannerDTO bannerDTO);

    /**
     * 修改广告
     *
     * @param proBanner
     * @return
     */
    @PostMapping("/banner/update")
    public ResponseInfo<String> updateBanner(@RequestBody ProBanner proBanner);

    /**
     * 分页查询
     *
     * @param proBanner
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/banner/queryByPage")
    public ResponseInfo<Page<ProBanner>> findBannerByPage(@RequestBody ProBanner proBanner,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据id删除广告
     *
     * @param ids
     * @return
     */
    @GetMapping("/banner/deleteProBanner")
    ResponseInfo<String> deleteProBanner(@RequestParam("ids") String ids);

//===============================分类crud=================================

    /**
     * 新增分类
     *
     * @param proChannel
     * @return
     */
    @PostMapping("/channel/save")
    public ResponseInfo<String> saveProChannel(@RequestBody ProChannel proChannel);

    /**
     * 修改分类
     *
     * @param proChannel
     * @return
     */
    @PostMapping("/channel/update")
    public ResponseInfo<String> updateProChannel(@RequestBody ProChannel proChannel);

    /**
     * 分页查询
     *
     * @param proChannel
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/channel/queryByPage")
    public ResponseInfo<Page<ProChannel>> findProChannelByPage(@RequestBody ProChannel proChannel,
                                                               @RequestParam("page") Integer page,
                                                               @RequestParam("pageSize") Integer pageSize);

//====================================================================


    /**
     * 新增商品图片
     *
     * @param
     * @return
     */
    @PostMapping("/proImage/save")
    public ResponseInfo<String> saveProImage(@RequestBody ProImage proImage);

    /**
     * 修改商品图片
     *
     * @param
     * @return
     */
    @PostMapping("/proImage/update")
    public ResponseInfo<String> updateProImage(@RequestBody ProImage proImage);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @returnIPage<ProImage>
     */
    @PostMapping("/proImage/queryByPage")
    public ResponseInfo<Page<ProImage>> findProImageByPage(@RequestBody ProImage proImage,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("pageSize") Integer pageSize);

    /**
     * 删除商品图片
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteProImage(@RequestParam("ids") Long[] ids);

    /**
     * 下架商品图片
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProImage(@RequestParam("ids") Long[] ids);

    /**
     * 上架商品图片
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProImage(@RequestParam("ids") Long[] ids);

    /**
     * @param skuId
     * @return
     */
    @GetMapping("/sku/selectProSkuById")
    ProSku selectProSkuById(Long skuId);

    //===================================================================

    /**
     * 新增商品支付类型
     *
     * @param proPayType
     * @return
     */
    @PostMapping("/proPayType/save")
    public ResponseInfo<String> saveProPayType(@RequestBody ProPayType proPayType);

    /**
     * 根据payType查询id
     *
     * @param payType
     * @return
     */
    @GetMapping("/proPayType/getId{payType}")
    public Long findIdByPayType(@PathVariable("payType") String payType);

    /**
     * 根据属性名和商品id查询属性id
     *
     * @param proId
     * @param propertyName
     * @return
     */
    @GetMapping("/proProperty/getId")
    public Long getId(@RequestParam("proId") Long proId, @RequestParam("propertyName") String propertyName);

    /**
     * 根据属性id和属性值查询属性值id
     *
     * @param propertyId
     * @param value
     * @return
     */
    @GetMapping("/propertyValue/getId")
    public Long getValueId(@RequestParam("propertyId") Long propertyId, @RequestParam("value") String value);


    @PostMapping("/package/save")
    public ResponseInfo<String> savePackage(@RequestBody ProPackage proPackage);

    @GetMapping("/channel/selectProChannelById")
    ResponseInfo<ProChannel> selectProChannelById(@RequestParam("id") Long id);

    @PostMapping("/channel/treeData")
    ResponseInfo<List<Ztree>> selectProChannelTree(@RequestBody ProChannel proChannel);


    @PostMapping("/product/findRProductDTOByPage")
    ResponseInfo<Page<RProductDTO>> findRProductDTOByPage(@RequestBody RProductDTO rProductDTO,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/product/selectProDtoById/{proId}")
    ResponseInfo<ProDto> selectProDtoById(@PathVariable("proId") Long proId);

    /**
     * 根据广告id查询广告
     *
     * @param bannerId
     * @return
     */
    @GetMapping("/banner/selectById")
    ResponseInfo<ProBanner> selectProBannerById(@RequestParam("bannerId") Long bannerId);

    /**
     * 新增商品分类关系
     *
     * @param proChannelRelation
     * @return
     */
    @PostMapping("/channelRelation/saveOne")
    ResponseInfo<String> saveProChannelRelation(ProChannelRelation proChannelRelation);

    /**
     * @param channleId
     * @return
     */
    @GetMapping("/channel/selectByChannelId")
    ResponseInfo<ProChannel> selectProChannelByChannelId(@RequestParam("channelId") Long channleId);

    /**
     * 删除分类
     *
     * @param ids
     * @return
     */
    @GetMapping("/channel/deleteProChannel")
    public ResponseInfo<String> deleteProChannel(@RequestParam("ids") String ids);

    /**
     * 上架京东商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/product/reshelfJd")
    public ResponseInfo<String> reshelfJdProduct(@RequestParam("ids") String ids);


    /**
     * 修改支付方式
     *
     * @param proPayType
     * @return
     */
    @PostMapping("/proPayType/update")
    ResponseInfo<String> updateProPayType(@RequestBody ProPayType proPayType);

    /**
     * 修改套餐
     *
     * @param proPackage
     * @return
     */
    @PostMapping("/package/update")
    ResponseInfo<String> updatePackage(@RequestBody ProPackage proPackage);

    /**
     * 根据商品id查询出商品的所有信息
     *
     * @param proId
     * @return
     */
    @GetMapping("/product/findAllProDto")
    ResponseInfo<AllProDto> selectAllProDtoById(@RequestParam("proId") Long proId);

    //===========增加商户===========================

    /**
     * 新增商户
     */
    @PostMapping("/store/add")
    ResponseInfo<String> add(Store store);


    //=========================商品分类类型=======================


    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/proChannelType/queryByPage")
    public ResponseInfo<Page<ProChannelType>> findProductByPage(@RequestBody ProChannelType proChannelType,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize);

    /**
     * 新增商品
     *
     * @param proChannelType
     * @return
     */
    @PostMapping("/proChannelType/save")
    public ResponseInfo<String> saveProduct(@RequestBody ProChannelType proChannelType);

    /**
     * 修改商品
     *
     * @param proChannelType
     * @return
     */
    @PostMapping("/proChannelType/update")
    public ResponseInfo<String> updateProduct(@RequestBody ProChannelType proChannelType);

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/proChannelType/findAll")
    public ResponseInfo<List<ProChannelType>> findAll();


    /**
     * 根据id查询
     *
     * @param channelTypeId
     * @return
     */
    @GetMapping("/proChannelType/findById")
    public ResponseInfo<ProChannelType> findById(@RequestParam("channelTypeId") Long channelTypeId);


    @GetMapping("/channel/sort")
    public ResponseInfo<String> sort(@RequestParam("channelId") Long channelId,
                                     @RequestParam("fatherId") Long fatherId,
                                     @RequestParam("sortNumber") Integer sortNumber,
                                     @RequestParam("mode") Integer mode);

    /**
     * 根据id查询商品分类关系
     * @param id
     * @return
     */
    @GetMapping("/channelRelation/selectById")
    ResponseInfo<ProChannelRelation> selectProChannelRelationById(@RequestParam("id") Long id);

    /**
     * 根据proId和groupNo查询skuId
     * @param proId
     * @param groupNo
     * @return
     */
    @GetMapping("/sku/getSkuIdByProIdAndGroupNo")
    ResponseInfo<Long> getSkuIdByProIdAndGroupNo(@RequestParam("proId") Long proId, @RequestParam("groupNo") String groupNo);
}
