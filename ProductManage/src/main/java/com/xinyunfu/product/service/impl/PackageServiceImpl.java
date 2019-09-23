package com.xinyunfu.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.entites.entite.CouponMarket;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.PageVODTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.mapper.ProPackageMapper;
import com.xinyunfu.product.mapper.ProSkuMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tym
 * @since 2019/7/9
 */
@Service
@Slf4j
public class PackageServiceImpl extends ServiceImpl<ProPackageMapper, ProPackage> implements IPackageService {

    @Autowired
    private ProPackageMapper packageMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private IProDetailsService proDetailsService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IFreightService freightService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProSkuMapper skuMapper;

    /*
     * 新增套餐
     */
    @Override
    public ResponseInfo<String> saveProPackage(ProPackage proPackage) {
        if (this.save(proPackage)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    /*
     * 修改套餐
     */
    @Override
    public ResponseInfo<String> update(ProPackage proPackage) {
        if (this.updateById(proPackage)) {
            return ResponseInfo.success("修改成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    //	/*
//	 * 删除套餐
//	 */
//	@Override
//	public ResponseInfo<String> deleteById(Integer packageId,Integer status) {
//		if (this.removeById(packageId)) {
//			return ResponseInfo.success("删除成功!");
//		}else {
//			return ResponseInfo.error("删除失败!");
//		}
//	}
    /*
     * 根据id查询套餐
     */
    @Override
    public ResponseInfo<ProPackage> findOneById(Integer packageId) {
        ProPackage proPackage = this.getById(packageId);
        if (proPackage.getStatus() == 0) {
            return ResponseInfo.success(proPackage);
        } else {
            return ResponseInfo.error(null);
        }
    }

    /*
     * 分页查询所有套餐
     */
    @Override
    public ResponseInfo<IPage<ProPackage>> findProductByPage(ProPackage proPackage, Integer page, Integer pageSize) {
        Page<ProPackage> pages = new Page<ProPackage>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
        QueryWrapper<ProPackage> queryWrapper = new QueryWrapper<>();
        IPage<ProPackage> page2 = this.page(pages, queryWrapper);
        return ResponseInfo.success(page2);
    }

    @Override
    public ResponseInfo<String> updateStatus(Integer[] ids, int status) {
        ProPackage proPackage = new ProPackage();
        proPackage.setStatus(status);
        List<Integer> idList = Arrays.asList(ids);
        UpdateWrapper<ProPackage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("package_id", idList);
        if (this.update(proPackage, updateWrapper)) {
            if (status == 0) {
                return ResponseInfo.success("启用成功!");
            } else if (status == 1) {
                return ResponseInfo.success("禁用成功!");
            } else {
                return ResponseInfo.success("删除成功!");
            }
        } else {
            if (status == 0) {
                return ResponseInfo.success("启用失败!");
            } else if (status == 1) {
                return ResponseInfo.success("禁用失败!");
            } else {
                return ResponseInfo.success("删除失败!");
            }
        }
    }

    //    @Override
//    public IPage<ProDto> findAllPackage(PageVODTO pageDto) {
//        log.info("====查询所有套餐商品=proName={}===", pageDto.getQueryParam());
//        LambdaQueryWrapper<ProPackage> wrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotEmpty(pageDto.getQueryParam())) {
//            wrapper.like(ProPackage::getPackageName, pageDto.getQueryParam()).eq(ProPackage :: getStatus,0).orderBy(true,true,ProPackage::getSortNumber);
//        }else {
//            wrapper.eq(ProPackage :: getStatus,0).orderBy(true,true,ProPackage::getSortNumber);
//        }
//        wrapper.orderByDesc(ProPackage::getSortNumber);
//        IPage<ProPackage> pages = new Page<>(pageDto.getPage() == null ? 1 : pageDto.getPage(), pageDto.getPageSize() == null ? 15 : pageDto.getPageSize());
//        IPage<ProPackage> packages = this.page(pages, wrapper);
//        log.info("====查询所有套餐商品=total={}==",packages.getTotal());
//        List<ProDto> proDtos = this.findProDtoByProPackages(packages.getRecords());
//        IPage<ProDto> proDtoPage=new Page<>();
//        proDtoPage.setSize(packages.getSize())
//                .setRecords(proDtos)
//                .setTotal(packages.getTotal())
//                .setCurrent(packages.getCurrent())
//                .setPages(packages.getPages());
//        return proDtoPage;
//
//    }
    @Override
    public IPage<ProDto> findAllPackage(PageVODTO pageDto) {

        return packageMapper.findAllPackage(new Page(pageDto.getPage(),pageDto.getPageSize()),pageDto.getQueryParam());
    }

    public List<ProDto> findProDtoByProPackages(List<ProPackage> packages) {
        List<ProDto> list = new ArrayList<>();
        for (ProPackage proPackage : packages) {
            ProDto proDto = new ProDto();
            long proId = proPackage.getProId();
            QueryWrapper<Product> wrapper = new QueryWrapper();
            wrapper.eq("pro_id", proId).eq("status", 0);
            Product product = productMapper.selectOne(wrapper);
            if (product == null)
                continue;
            log.warn("================proId={}的商品已下架或不存在=========", proId);
            String imageUrl = fileService.findDefaultImageByProId(proId);
            BigDecimal price = proPackage.getPackagePrice();
            proDto.setProId(proId)
                    .setProName(product.getProName())
                    .setImageUrl(imageUrl)
                    .setPrice(price)
                    .setSource(product.getSource());
            list.add(proDto);
        }
        return list;
    }

    /*
     *根据商品id查询套餐商品详情
     */
    @Override
    public ProductDTO findPackageProductDTOByProId(ProIdDTO proIdDTO) {
        Long proId = proIdDTO.getProId();
        Product product = productService.findProductById(proId);
        ProDetails proDetails = proDetailsService.findProDetailsByProId(proId);
        List<ProSku> skuList = skuService.findSkuByProId(proIdDTO);
        List<ProImage> imageList = fileService.findImageByProId(product.getProId());
        BigDecimal postage = freightService.queryPostage(proId);
        ProductDTO productDTO = new ProductDTO();
        int allSellStock = skuService.findAllSellStockByProId(proIdDTO);
        BigDecimal price = skuService.findDefaultPriceByProId(proIdDTO);
        productDTO.setProduct(product)
                .setProDetails(proDetails)
                .setImageList(imageList)
                .setPostage(postage)
                .setAllSellStock(allSellStock)
                .setPrice(price);
        return productDTO;
    }

    /**
     * 根据商品id查询套餐
     * @param proId
     * @return
     */
    @Override
    public ProPackage findProPackageByProId(Long proId) {
        QueryWrapper<ProPackage> wrapper=new QueryWrapper<>();
        wrapper.eq("pro_id",proId).eq("status",0);
        ProPackage proPackage= packageMapper.selectOne(wrapper);
        if (proPackage==null){
            log.info("=============商品id为proId={}的商品不是套餐==========",proId);
            return null;
        }
        return proPackage;
    }


}
