package com.xinyunfu.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.mapper.ProPropertyMapper;
import com.xinyunfu.product.mapper.ProPropertyValueMapper;
import com.xinyunfu.product.mapper.ProSkuMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.ISkuService;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
public class SkuServiceImpl extends ServiceImpl<ProSkuMapper, ProSku> implements ISkuService {

    @Autowired
    private ProSkuMapper proSkuMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProPropertyMapper propertyMapper;
    @Autowired
    private ProPropertyValueMapper valueMapper;
    @Autowired
    private ProSkuMapper skuMapper;

    /*
     * 新增sku
     */
    @Override
    public ResponseInfo<String> saveProsku(ProSku proSku) {
        if (this.save(proSku)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    /*
     * 修改sku
     */
    @Override
    public ResponseInfo<String> update(ProSku proSku) {
        if (updateById(proSku)) {
            return ResponseInfo.success("修改成功!");
        } else {
            return ResponseInfo.error("修改失败!");
        }
    }

    /*
     * 根据id查询sku
     */
    @Override
    public ResponseInfo<ProSku> findOneById(Long skuId) {
        ProSku proSku = this.getById(skuId);
        return ResponseInfo.success(proSku);
    }

    /*
     * 分页查询
     */
    @Override
    public ResponseInfo<PageVO<ProSku>> queryByPage(ProSku proSku, Integer page, Integer pageSize) {
//        page = (page == null ? 1 : page);
//        pageSize = (pageSize == null ? 15 : pageSize);
//        PageVO<ProSku> pages = new PageVO<>();
//        List<ProSku> list = proSkuMapper.selectProSkuList(proSku);
//        pages.setCurrent_page(page);
//        pages.setInstanceList(list);
//        pages.setSize(pageSize);
//        return ResponseInfo.success(pages);
        return null;
    }


    /*
     * 修改sku状态,0启用,1禁用,2删除
     */
    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, Integer status) {
        ProSku proSku = new ProSku();
        proSku.setStatus(status);
        List<Long> idList = Arrays.asList(ids);
        UpdateWrapper<ProSku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("sku_id", idList);
        if (this.update(proSku, updateWrapper)) {
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
     * 根据商品id列表查询sku
     */
    @Override
    public ResponseInfo<List<List<ProSku>>> findSkuByProIds(List<Long> ids) {
        List<List<ProSku>> list = new ArrayList<>();
        for (long id : ids) {
            ProIdDTO proIdDTO = new ProIdDTO();
            proIdDTO.setProId(id).setProType(0);
            List<ProSku> skuList = this.findSkuByProId(proIdDTO);
            list.add(skuList);
        }
        return ResponseInfo.success(list);
    }


    /*
     * 根据商品id查询普通sku
     */
    @Override
    public List<ProSku> findSkuByProId(ProIdDTO proIdDTO) {
        long proId = proIdDTO.getProId();
        Product product = productMapper.selectById(proId);
        if (product == null) {
            throw new RuntimeException("暂无商品");
        }
        QueryWrapper<ProSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("status", 0);
        List<ProSku> list = proSkuMapper.selectList(queryWrapper);
        if (list == null || list.size() == 0)
            throw new RuntimeException("查询sku失败");
        else
            return list;
    }


    /*
     * 根据商品id查询普通sku中的最低价格
     */
    @Override
    public BigDecimal findDefaultPriceByProId(ProIdDTO proIdDTO) {

        long proId = proIdDTO.getProId();
        int proType = proIdDTO.getProType();
        BigDecimal price = proSkuMapper.findDefaultPriceByProId(proId);
        if (price == null)
            throw new RuntimeException("查询默认价格失败");
        else
            return price;
    }


    /*
     * 根据商品id查询商品总销量
     */
    public Integer findAllSellStockByProId(ProIdDTO proIdDTO) {
        long proId = proIdDTO.getProId();
        int proType = proIdDTO.getProType();
        Integer allSellStock = proSkuMapper.findAllSellStockByProId(proId);
        if (allSellStock == null)
            throw new RuntimeException("查询总销量失败");
        else
            return allSellStock;
    }


    /*
     * 根据商品id查询商品总库存
     */
    public Integer findAllStockByProId(ProIdDTO proIdDTO) {

        long proId = proIdDTO.getProId();
        int proType = proIdDTO.getProType();
        Integer allStock = proSkuMapper.findAllStockByProId(proId);
        if (allStock == null)
            throw new RuntimeException("查询总库存失败");
        else
            return allStock;
    }

    @Override
    public Map<Long, Integer> findProTypeBySkuId(String ids) {
        String[] split = ids.split(",");
        Map<Long, Integer> map = new HashMap<>();
        for (String string : split) {
            long skuId = Long.valueOf(string);
            int proType = skuMapper.findProTypeBySkuId(skuId);
            map.put(skuId, proType);
        }
        return map;
    }

    /*
     *减库存
     */
    @Override
    public Boolean subtractStock(Map<Long, Integer> map) {
        log.info(map.toString());
//        Map<Long,Integer> map=new HashMap<>();
//        map = JsonUtils.parseMap(str, Long.class, Integer.class);
        boolean flag = true;
        for (Map.Entry<Long, Integer> entry : map.entrySet()
        ) {
            Long skuId = entry.getKey();
            Integer count = entry.getValue();
            if (proSkuMapper.subtractStock(skuId, count) != 1) {
                flag = false;
            }
        }
        return flag;

    }

    /*
     *加库存
     */
    @Override
    public Boolean addStock(Map<Long, Integer> map) {
//        Map<Long, Integer> map = JsonUtils.parseMap(str, Long.class, Integer.class);
        boolean flag = true;
        for (Map.Entry<Long, Integer> entry : map.entrySet()
        ) {
            Long skuId = entry.getKey();
            Integer count = entry.getValue();
            if (proSkuMapper.addStock(skuId, count) != 1) {
                flag = false;
            }
            ;
        }
        return flag;
    }

    @Override
    public List<ProSku> queryJDSkuReshelf() {
        List<Long> list = productMapper.selectProIdList();
        QueryWrapper<ProSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("proId", list);
        return proSkuMapper.selectList(queryWrapper);
    }

    @Override
    public String getSkuDesc(Long skuId) {
        String skuDesc = "";
        String groupNo = this.findOneById(skuId).getData().getGroupNo();
        String[] s = groupNo.split(",");
        for (String str : s
        ) {
            String[] ss = str.split(":");
            long propertyId = Long.valueOf(ss[0]);
            long valueId = Long.valueOf(ss[1]);
            String propertyName = propertyMapper.getProPertyName(propertyId);
            String valueName = valueMapper.getValueName(valueId);
            skuDesc = skuDesc + propertyName + ":" + valueName + ",";
        }
        skuDesc = skuDesc.substring(0, skuDesc.length() - 1);
        return skuDesc;
    }

    @Override
    public ResponseInfo<String> deleteProSku(Long[] ids, int i) {
        ProSku proSku = new ProSku();
        proSku.setStatus(i);
        UpdateWrapper<ProSku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("sku_id", ids);
        if (this.update(proSku, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }

    @Override
    public List<String> findSkusByProId(Long proId) {
        QueryWrapper<ProSku> wrapper = new QueryWrapper<>();
        wrapper.eq("pro_id", proId).eq("status",0);
        List<ProSku> proSkus = skuMapper.selectList(wrapper);
        if (proSkus==null || proSkus.size()==0){
            log.info("==========id为proId={}的商品没有sku或已被禁用===========",proId);
            return null;
        }
        List<String> proSkuJsons=new ArrayList<>();
        for (ProSku proSku : proSkus) {
            String json= JSON.toJSONString(proSku);
            proSkuJsons.add(json);
        }
        return proSkuJsons;
    }

    /**
     * edit by lhpu
     * ==========================================================================
     * /
     * <p>
     * /**
     * 新增sku
     */
    @Override
    public boolean addProsku(ProSku proSku) {
        return proSkuMapper.insert(proSku) > 0;
    }


    @Override
    public ProSku getSkuByProId(long proId) {
        QueryWrapper<ProSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId);
        ProSku proSku = proSkuMapper.selectOne(queryWrapper);
        return proSku;
    }


    @Override
    public boolean checkSkuByProId(Long proId) {

        return proSkuMapper.checkSkuByProId(proId) > 0;
    }

    @Override
    public String getSkuNoBySkuId(Long skuId) {
        return proSkuMapper.getSkuNoBySkuId(skuId);
    }




    @Override
    public boolean updateSkuByProNo(String proNo) {
        return proSkuMapper.updateSkuByProNo(proNo) > 0;
    }

    @Override
    public Long getProIdBySkuNo(String skuNo) {

        return proSkuMapper.getProIdBySkuNo(skuNo);

    }

    @Override
    public Long getSkuIdByProIdAndGroupNo(Long proId, String groupNo) {
        return skuMapper.getSkuIdByProIdAndGroupNo(proId,groupNo);
    }
}



























