package com.xinyunfu.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.feign.ProductDockingFeign;
import com.xinyunfu.product.mapper.*;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.*;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductDockingFeign productDockingFeign;
    @Autowired
    private ProDetailsMapper proDetailsMapper;
    @Autowired
    private ProChannelRelationMapper proChannelRelationMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private ProChannelMapper channelMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProPayTypeMapper proPayTypeMapper;
    @Autowired
    private PayTypeMapper payTypeMapper;
    @Autowired
    private IProDetailsService proDetailsService;
    @Autowired
    private IFreightService freightService;
    @Autowired
    private ProSkuMapper skuMapper;
    @Autowired
    private ProPackageMapper packageMapper;
    @Autowired
    private ProImageMapper proImageMapper;

    /*
     * 分页查询
     */
    @Override
    public PageVO<Product> findProductByPage(Product product, Integer page, Integer pageSize) {
//        PageVO<Product> pages=new PageVO<>();
//        List<Product> list=productMapper.select(product);
//        pages.setCurrent_page(page);
//        pages.setInstanceList(list);
//        pages.setSize(pageSize);
//        return pages;
        return null;
    }

    /*
     * 新增产品
     */
    @Override
    public ResponseInfo<String> saveProduct(Product product) {
        if (productMapper.insert(product) > 0) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }

    }

    /*
     * 修改产品
     */
    @Override
    public ResponseInfo<String> updateProduct(Product product) {
        try {
            this.updateById(product);
            return ResponseInfo.success("修改成功!");
        } catch (Exception e) {
            return ResponseInfo.error("修改失败!");
        }
    }

    /*
     * 根据id删除产品
     */
    @Override
    public ResponseInfo<String> deleteProduct(Long[] ids, Integer status) {
        Product product = new Product();
        product.setStatus(status);
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("pro_id", ids);
        if (this.update(product, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }

    /*
     * 产品上下架
     */
    @Override
    public ResponseInfo<String> updateStatus(String ids, Integer status) {
        Product product = new Product();
        product.setStatus(status);
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("pro_id", ids.split(","));
        if (this.update(product, updateWrapper)) {
            if (status == 1) {
                return ResponseInfo.success("下架成功!");
            } else if (status == 0) {
                return ResponseInfo.success("上架成功!");
            } else {
                return ResponseInfo.success("上架京东成功!");
            }
        } else {
            if (status == 1) {
                return ResponseInfo.error("下架失败");
            } else if (status == 0) {
                return ResponseInfo.error("上架失败");
            } else {
                return ResponseInfo.error("上架京东失败!");
            }
        }

    }

    /*
     * 根据id查询产品
     */
    public Product findProductById(Long proId) {
        Product product = this.getById(proId);
        if (product != null)
            return product;
        else
            throw new RuntimeException("商品已下架");


    }

    /*
     * 根据三级分类id查询所有状态为上架的产品
     */
    @Override
    public List<Product> findProductByChannelId(Long channelId) {
        List<Product> productList = new ArrayList<>();
        QueryWrapper<ProChannelRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("channel_id", channelId).eq("status", 0);
        List<ProChannelRelation> list = proChannelRelationMapper.selectList(queryWrapper);
        for (ProChannelRelation proCR : list) {
            productList.add(this.findProductByProId(proCR.getProId()));
        }
        if (productList != null && productList.size() != 0)
            return productList;
        else
            throw new RuntimeException("该分类暂无商品");
    }

    public Product findProductByProId(Long proId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("status", 0);
        return productMapper.selectOne(queryWrapper);
    }

    /**
     * @param channelPageDTO
     * @return
     */
    @Override
    public ThirdChannelDTO queryProDtosByChannelId(ChannelPageDTO channelPageDTO) {

        List<ProDto> list = new ArrayList<>();
        List<Product> products = this.findProductByChannelId(channelPageDTO.getChannelId());
        ProDto proDto = null;
        if (products != null && products.size() != 0) {
            this.queryProDtosListByChannelId(channelPageDTO.getChannelId());
            if (list == null || list.size() == 0)
                throw new RuntimeException("暂无商品!");
            else {
                Map<String, Long> map = new HashMap<>();
                ProChannel proChannel = channelMapper.findChannelByChannelId(channelPageDTO.getChannelId());
                String imgs = proChannel.getImageUrl();
                String[] imgUrls = imgs.split(";");
//                String ids = proChannel.getProIds();
//                String[] proIds = ids.split(";");
                for (int i = 0; i < imgUrls.length; i++) {
//                    map.put(imgUrls[i], Long.valueOf(proIds[i]));
                }
                PageVO<ProDto> page = new PageVO<>(list, channelPageDTO.getPageSize());
                page.setCurrent_page(channelPageDTO.getPage());
                page.setSize(channelPageDTO.getPageSize());
                List<ProDto> currentPageData = page.currentPageData();
                page.setCurrentPageData(currentPageData);
                return new ThirdChannelDTO(page, map);
            }
        } else {
            throw new RuntimeException("暂无商品!");
        }


    }

    /*
     * 查询支付方式和支持的券类型
     */
    @Override
    public Map<Long, List<String>> queryPayTypeAndCouponType(List<Long> ids) {
        Map<Long, List<String>> map = new HashMap<>();
        for (Long proId : ids) {
            Product product = this.getById(proId);
            ProPayType proPayType = proPayTypeMapper.selectByProId(proId);
            if (null == proPayType && StringUtils.isEmpty(product.getCouponType()))
                continue;
            Long payTypeId = proPayType.getPayTypeId();
            QueryWrapper<PayType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", payTypeId);
            PayType payType = payTypeMapper.selectOne(queryWrapper);
            List<String> list = new ArrayList<>();
            list.add(payType.getPayType());
            list.add(StringUtils.isEmpty(product.getCouponType()) ? "" : product.getCouponType());
            map.put(proId, list);
        }
        if (map == null || map.isEmpty())
            throw new RuntimeException("暂无数据");
        else
            return map;
    }


    @Override
    public void updateProductByChannelName(String channelName, Integer killStatus) {
        QueryWrapper<ProChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("channel_name", channelName);
        Long channelId = channelMapper.selectOne(queryWrapper).getChannelId();
        if (channelId == null) {
            throw new RuntimeException("根据分类名称查询分类id失败");
        }
        List<ProDto> list = this.queryProDtosListByChannelId(channelId);
        if (list == null || list.isEmpty()) {
            //
            log.warn("=======查询没有秒杀信息!======");

        } else {
            log.warn("=======执行定时任务======");
            List<Long> ids = new ArrayList<>();
            for (ProDto proDto : list
            ) {
                long proId = proDto.getProId();
                ids.add(proId);
            }
            Product product = new Product();
            product.setKillStatus(killStatus);
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("pro_id", ids);
            int rows = productMapper.update(product, updateWrapper);
            if (rows == 0)
                throw new RuntimeException("修改秒杀状态失败");
        }

    }

    public List<ProDto> queryProDtosListByChannelId(Long channelId) {

        List<ProDto> list = new ArrayList<>();
        List<Product> products = this.findProductByChannelId(channelId);
        ProDto proDto=null;
        if (products != null && products.size() != 0) {
            for (Product product : products) {
                if (product == null)
                    continue;
                proDto = new ProDto();
                long proId = product.getProId();
                int proType = product.getProType();
                String imageUrl = fileService.findDefaultImageByProId(product.getProId());
                BigDecimal price = skuMapper.findDefaultPriceByProId(product.getProId());
                Integer allSeckillSellStock = skuMapper.findAllSeckillSellStockByProId(proId);
                Integer allSeckillStock = skuMapper.findAllSeckillStockByProId(proId);
                BigDecimal minPrice = skuMapper.findDefaultMinPriceByProId(proId);
                if (proType == 1) {
                    BigDecimal seckillPrice = skuMapper.findDefaultSeckillPriceByProId(proId);
                    log.info("====proType={},seckillPrice={}============", proType, seckillPrice);
                    proDto.setProId(product.getProId())
                            .setProName(product.getProName())
                            .setImageUrl(imageUrl)
                            .setPrice(price)
                            .setSeckillPrice(seckillPrice)
                            .setAllSeckillSellStock(allSeckillSellStock)
                            .setAllSeckillStock(allSeckillStock)
                            .setSource(product.getSource());
                } else if (proType == 2) {
                    proDto.setProId(product.getProId())
                            .setProName(product.getProName())
                            .setImageUrl(imageUrl)
                            .setPrice(price)
                            .setSource(product.getSource());
                } else {
                    proDto.setProId(product.getProId())
                            .setProName(product.getProName())
                            .setImageUrl(imageUrl)
                            .setPrice(price)
                            .setMinPrice(minPrice)
                            .setSource(product.getSource());
                }
                list.add(proDto);
            }
            return list;
        } else {
            throw new RuntimeException("暂无商品!");
        }
    }

    /**
     * 查询单个商品的详细信息
     *
     * @param proIdDTO : proId proType
     * @return
     */
    @Override
    public ProductDTO findProductDtoById(ProIdDTO proIdDTO) {
        long proId = proIdDTO.getProId();
        Product product = this.findProductById(proId);
        if (product == null)
            throw new RuntimeException("商品已下架");
        BigDecimal price = skuMapper.findDefaultPriceByProId(proId);

        ProDetails proDetails = proDetailsService.findProDetailsByProId(proId);
        List<ProImage> imageList = fileService.findImageByProId(product.getProId());
        BigDecimal postage = freightService.queryPostage(proId);
        ProductDTO productDTO = new ProductDTO();
        BigDecimal costPrice = skuMapper.findMinCostPriceByProId(proId);
        if (product.getSource() == 1) {

            ResponseInfo dockingResult = productDockingFeign.getProductDto(proId, price, costPrice, skuMapper.getSkuNo(proId), "75");
            if (!dockingResult.isSuccess()) {
                throw new RuntimeException("该商品已下架");
            }
            JDProductDTO dto = JSONObject.parseObject(JSONObject.toJSONString(dockingResult.getData()), JDProductDTO.class);
            price = dto.getPrice();
        }

        BigDecimal seckillPrice = skuMapper.findDefaultSeckillPriceByProId(proId);
        Integer allSellStock = skuMapper.findAllSellStockByProId(proId);
        BigDecimal minPrice = skuMapper.findDefaultMinPriceByProId(proId);
        Integer allSeckillStock = skuMapper.findAllSellStockByProId(proId);
        Integer allSeckillSellStock = skuMapper.findAllSeckillSellStockByProId(proId);
        if (product.getProType() == 0) {
            productDTO.setProduct(product)
                    .setProDetails(proDetails)
                    .setImageList(imageList)
                    .setPostage(postage)
                    .setAllSellStock(allSellStock)
                    .setPrice(price)
                    .setMinPrice(minPrice);
        }
        if (product.getProType() == 1) {
            productDTO.setProduct(product)
                    .setProDetails(proDetails)
                    .setImageList(imageList)
                    .setPostage(postage)
                    .setPrice(price)
                    .setSeckillPrice(seckillPrice)
                    .setAllSeckillStock(allSeckillStock)
                    .setAllSeckillSellStock(allSeckillSellStock);
        }
        if (product.getProType() == 2) {
            productDTO.setProduct(product)
                    .setProDetails(proDetails)
                    .setImageList(imageList)
                    .setPostage(postage)
                    .setAllSellStock(allSellStock)
                    .setPrice(price);
        }
        if (productDTO == null)
            throw new RuntimeException("商品已下架");
        else
            return productDTO;
    }

    @Override
    public Map<String, Product> queryJDProInstock() {
        Map<String, Product> map = new HashMap<>();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source", 1).eq("status", 0);
        List<Product> list = productMapper.selectList(queryWrapper);
        for (Product product : list
        ) {
            map.put(product.getProNo(), product);
        }
        return map;

    }


    @Override
    public Map<String, Long> queryProIdByProNo(String[] proNos) {
        Map<String, Long> map = new HashMap<>();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source", 1).in("pro_no", proNos);
        List<Product> list = productMapper.selectList(queryWrapper);
        for (Product product : list
        ) {
            map.put(product.getProNo(), product.getProId());
        }
        return map;
    }

    @Override
    public Map<Long, Product> getJDProInstock() {
        Map<Long, Product> map = new HashMap<>();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source", 1).eq("status", 0);
        List<Product> list = productMapper.selectList(queryWrapper);
        for (Product product : list
        ) {
            map.put(product.getProId(), product);
        }
        return map;
    }

    @Override
    public Product queryProductByProNo(String proNo) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_no", proNo);
        return productMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean checkProByProNo(String proNo) {

        return productMapper.checkProByProNo(proNo) > 0;

    }


    @Override
    public Long getProIdByProNo(String proNo) {

        return productMapper.getProIdByProNo(proNo);

    }


    @Override
    public Page<RProductDTO> getRProductDTO(RProductDTO rProductDTO, Integer page, Integer pageSize) {
        Page<RProductDTO> pages = productMapper.getRProductDTO(new Page(page, pageSize), rProductDTO);
        List<RProductDTO> records = pages.getRecords();
        List<RProductDTO> list = new ArrayList<>();
        for (RProductDTO r : records) {
            Long proId = r.getProId();

            String imgUrl = proImageMapper.findImgUrlByProId(proId);
            BigDecimal price = skuMapper.findDefaultPriceByProId(proId);
            BigDecimal costPrice = skuMapper.findMinCostPriceByProId(proId);
            String skuNo = null;
            if (r.getSource() == 1) {
                skuNo = skuMapper.getSkuNo(proId);
            }
            List<String> channelNames = productMapper.selectChannelNameByProId(proId);
            String channelName;
            if (channelNames == null || channelNames.isEmpty()) {
                channelName = null;
            } else {
                channelName = channelNames.get(0);
            }
//            String channelName = "";
//            for (String channelName1 : channelNames) {
//                channelName = channelName1 + ",";
//            }
//            if (StringUtils.isNotEmpty(channelName)) {
//                channelName = channelName.substring(0, channelName.length() - 1);
//            }
            r.setImgUrl(imgUrl).setImgUrl(imgUrl).setPrice(price).setCostPrice(costPrice).setSkuNo(skuNo).setChannelName(channelName);
            list.add(r);
        }
        pages.setRecords(list);
        return pages;
    }


//    @Override
//    public Page<ProDto> findProDtoPageByChannelId(ChannelPageDTO channelPageDTO) {
//        Page<ProDto> pages = productMapper.getProDto(new Page(channelPageDTO.getPage(), channelPageDTO.getPageSize()));
//        return pages;
//    }

    @Override
    public ProDto findProDtoByProId(Long proId) {
        ProDto proDto = new ProDto();
        Product product = this.findProductById(proId);
        int proType = product.getProType();
        String imageUrl = fileService.findDefaultImageByProId(product.getProId());
        BigDecimal price = skuMapper.findDefaultPriceByProId(product.getProId());
        Integer allSeckillSellStock = skuMapper.findAllSeckillSellStockByProId(proId);
        Integer allSeckillStock = skuMapper.findAllSeckillStockByProId(proId);
        BigDecimal minPrice = skuMapper.findDefaultMinPriceByProId(proId);
        if (proType == 1) {
            BigDecimal seckillPrice = skuMapper.findDefaultSeckillPriceByProId(proId);
            log.info("====proType={},seckillPrice={}============", proType, seckillPrice);
            proDto.setProId(product.getProId())
                    .setProName(product.getProName())
                    .setImageUrl(imageUrl)
                    .setPrice(price)
                    .setSeckillPrice(seckillPrice)
                    .setAllSeckillSellStock(allSeckillSellStock)
                    .setAllSeckillStock(allSeckillStock)
                    .setSource(product.getSource());
        } else if (proType == 2) {
            proDto.setProId(product.getProId())
                    .setProName(product.getProName())
                    .setImageUrl(imageUrl)
                    .setPrice(price)
                    .setSource(product.getSource());
        } else {
            proDto.setProId(product.getProId())
                    .setProName(product.getProName())
                    .setImageUrl(imageUrl)
                    .setPrice(price)
                    .setMinPrice(minPrice)
                    .setSource(product.getSource());
        }
        return proDto;
    }

    @Override
    public IPage<ProDto> getProDtoPageByChannelId(ChannelPageDTO channelPageDTO) {
//        Page<ProDto> proDtos = productMapper.getProDto(new Page(channelPageDTO.getPage(),
//                channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
//        List<ProDto> list = proDtos.getRecords();
//        List<ProDto> list1 = new ArrayList<>();
//        for (ProDto proDto : list) {
//            Product product = this.findProductById(proDto.getProId());
//            int proType = product.getProType();
//            String imageUrl = fileService.findDefaultImageByProId(proDto.getProId());
//            BigDecimal price = skuMapper.findDefaultPriceByProId(proDto.getProId());
//            Integer allSeckillSellStock = skuMapper.findAllSeckillSellStockByProId(proDto.getProId());
//            Integer allSeckillStock = skuMapper.findAllSeckillStockByProId(proDto.getProId());
//            BigDecimal minPrice = skuMapper.findDefaultMinPriceByProId(proDto.getProId());
//            if (proType == 1) {
//                BigDecimal seckillPrice = skuMapper.findDefaultSeckillPriceByProId(proDto.getProId());
//                log.info("====proType={},seckillPrice={}============", proType, seckillPrice);
//                proDto.setImageUrl(imageUrl)
//                        .setPrice(price)
//                        .setSeckillPrice(seckillPrice)
//                        .setAllSeckillSellStock(allSeckillSellStock)
//                        .setAllSeckillStock(allSeckillStock)
//                        .setSource(product.getSource());
//            } else if (proType == 2) {
//                proDto.setImageUrl(imageUrl)
//                        .setPrice(price)
//                        .setSource(product.getSource());
//            } else {
//                proDto.setImageUrl(imageUrl)
//                        .setPrice(price)
//                        .setMinPrice(minPrice)
//                        .setSource(product.getSource());
//            }
//            list.add(proDto);
//        }
//        return proDtos.setRecords(list);
//        return productMapper.getProDto(new Page((channelPageDTO.getPage()-1)*channelPageDTO.getPageSize(), channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
        return productMapper.getProDto(new Page(channelPageDTO.getPage(), channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
    }

    @Override
    public IPage<ProDto> getProDtoPageByChannelIdOrderByAllSellStockDesc(ChannelPageDTO channelPageDTO) {
        return productMapper.getProDtoPageByChannelIdOrderByAllSellStockDesc(new Page(channelPageDTO.getPage(), channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
    }

    @Override
    public IPage<ProDto> getProDtoPageByChannelIdOrderByPriceDesc(ChannelPageDTO channelPageDTO) {
        return productMapper.getProDtoPageByChannelIdOrderByPriceDesc(new Page(channelPageDTO.getPage(), channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
    }

    @Override
    public IPage<ProDto> getProDtoPageByChannelIdOrderByPriceAsc(ChannelPageDTO channelPageDTO) {
        return productMapper.getProDtoPageByChannelIdOrderByPriceAsc(new Page(channelPageDTO.getPage(), channelPageDTO.getPageSize()), channelPageDTO.getChannelId());
    }

    /**
     * 根据商品关键字或者分类id查询商品列表
     *
     * @param pageByProNameDTO
     * @return IPage<ProDto>
     */
    @Override
    public IPage<ProDto> findProDtoByProName(PageByProNameDTO pageByProNameDTO) {
        if (pageByProNameDTO.getChannelId() != null) {
            return productMapper.findProDtoByProName(new Page(pageByProNameDTO.getPage(), pageByProNameDTO.getPageSize()),
                    pageByProNameDTO.getQueryParam(), pageByProNameDTO.getChannelId(), pageByProNameDTO.getOrderByCloum(),
                    pageByProNameDTO.getIsAsc());
        }
        return productMapper.searchProDtoByProName(new Page(pageByProNameDTO.getPage(), pageByProNameDTO.getPageSize()),
                pageByProNameDTO.getQueryParam(), pageByProNameDTO.getOrderByCloum(),
                pageByProNameDTO.getIsAsc());
    }


    /**
     * edit by lhpu
     * ======================================================================
     */
    @Override
    public List<Long> getJDProductIds(int source, int status) {
        return productMapper.selectProductIds(source, status);
    }

    @Override
    public boolean offSaleByproNo(String proNo) {
        return productMapper.offSaleByproNo(proNo) > 0;
    }

    @Override
    public boolean offSaleByproId(Long proId) {
        return productMapper.offSaleByproId(proId) > 0;
    }

    @Override
    public Product getProductBySkuNo(String skuNo) {
        return productMapper.getProductBySkuNo(skuNo);
    }


}
