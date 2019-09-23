package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.ProDetailsMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IProDetailsService;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProDetailsServiceImpl extends ServiceImpl<ProDetailsMapper, ProDetails> implements IProDetailsService {

    @Autowired
    private ProDetailsMapper proDetailsMapper;
    @Autowired
    private ProductMapper productMapper;

    /*
     * 根据商品id查询商品详情
     */
    @Override
    public ProDetails findProDetailsByProId(Long proId) {
        QueryWrapper<ProDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("status",0);
        ProDetails proDetails = proDetailsMapper.selectOne(queryWrapper);
        if (proDetails==null){
            log.info("================商品id为proId={}的商品详情不存在=========", proId);
        }
        return proDetails;
    }

    /*
     * 根据三级分类id查询商品详情
     */
    @Override
    public ResponseInfo<List<ProDetails>> findProDetailsByProIds(List<Long> proIds) {
        QueryWrapper<ProDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("pro_id", proIds).eq("status", 0);
        List<ProDetails> list = proDetailsMapper.selectList(queryWrapper);
        if (list == null || list.isEmpty())
            throw new RuntimeException("暂无商品详情数据");
        else
            return ResponseInfo.success(list);
    }

    @Override
    public ResponseInfo<String> saveProDetails(ProDetails proDetails) {
        if (this.save(proDetails)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }
    }

    @Override
    public List<ProDetails> queryJDProDetaislsReshelf() {
        List<Long> list = productMapper.selectProIdList();
        QueryWrapper<ProDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("proId", list);
        return proDetailsMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean checkProDetailsByProId(long proId) {

        return proDetailsMapper.checkProDetailsByProId(proId) > 0;
    }

    @Override
    public ResponseInfo<String> updateProDetails(ProDetails proDetails) {
        if (this.updateById(proDetails))

            return ResponseInfo.success("修改成功!");
        else
            return ResponseInfo.error("修改失败!");
    }

    @Override
    public ResponseInfo<PageVO<ProDetails>> findProDetailsByPage(ProDetails proDetails, Integer page, Integer pageSize) {
        page =( page == null ? 1 : page);
        pageSize= (pageSize == null ? 15 : pageSize);
        PageVO<ProDetails> pages=new PageVO<>();
        List<ProDetails> list=proDetailsMapper.selectProDetailsList(proDetails);
        pages.setCurrent_page(page);
        pages.setInstanceList(list);
        pages.setSize(pageSize);
        return ResponseInfo.success(pages);
    }



    @Override
    public ResponseInfo<String> deleteProDetails(Long[] ids, int i) {
        ProDetails proDetails = new ProDetails();
        proDetails.setStatus(i);
        UpdateWrapper<ProDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("pro_id", ids);
        if (this.update(proDetails, updateWrapper))
            return ResponseInfo.success("删除成功!");
        else
            return ResponseInfo.error("删除失败!");
    }

    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, int i) {
        ProDetails proDetails = new ProDetails();
        proDetails.setStatus(i);
        UpdateWrapper<ProDetails> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("proId", ids);
        if (this.update(proDetails, updateWrapper)) {
            if (i == 1) {
                return ResponseInfo.success("下架成功!");
            } else {
                return ResponseInfo.success("上架成功!");
            }
        } else {
            if (i == 1) {
                return ResponseInfo.error("下架失败");
            } else {
                return ResponseInfo.error("上架失败");
            }
        }
    }


    /**
     * edit by lhpu
     * =================================================================================
     */

    @Override
    public Boolean addProDetails(ProDetails proDetail) {
        return proDetailsMapper.insert(proDetail) > 0;
    }





}
