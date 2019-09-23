package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.mapper.CategoryMapper;
import com.xinyunfu.model.Category;
import com.xinyunfu.service.CategoryService;
import com.xinyunfu.service.JDGateWayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
          implements CategoryService {

      @Autowired
      private CategoryMapper categoryMapper;

      @Autowired
      private JDGateWayService jdGateWayService;

      /**
       * 新增类型
       */
      @Override
      public boolean saveOrUpdateCategory(Category category) {

            return this.saveOrUpdate(category);

      }

      /**
       * 获取JD分类信息并处理
       */

      public void handleCategory(){

            //获取JD商品列表

            //获取上架商品列表
            //List<JDShlefGoodsDto> shlefGoods = jdGateWayService.getShelfGoodsList(skus);
            //
            //JDCategoryDto categoryDto = jdGateWayService.getCategory(cid);

      }


}
