package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.mapper.CategoryMappingMapper;
import com.xinyunfu.model.CategoryMapping;
import com.xinyunfu.service.CategoryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMappingServiceImpl extends ServiceImpl<CategoryMappingMapper, CategoryMapping>
          implements CategoryMappingService {

      @Autowired
      private CategoryMappingMapper categoryMappingMapper;

      /**
       * 新增类型关系映射
       */
      @Override
      public Boolean saveMapping(CategoryMapping categoryMapping) {
            return this.save(categoryMapping);
      }

      /**
       * 更新类型关系映射
       */
      @Override
      public Boolean updateMappingById(CategoryMapping categoryMapping) {
            return this.updateById(categoryMapping);
      }

      /**
       * 删除类型关系映射
       */
      @Override
      public Boolean deleteMapping(long id) {
            return this.removeById(id);
      }

      /**
       * 根据id查询类型关系映射
       */

      @Override
      public CategoryMapping getMappingById(long id) {
            CategoryMapping categoryMapping = this.getById(id);
            return categoryMapping;
      }

      /**
       * 分页查询类型关系映射
       */
      @Override
      public IPage<CategoryMapping> getMappingByPage(
                CategoryMapping categoryMapping, Integer page, Integer pageSize) {
            Page<CategoryMapping> pages = new Page<>(page == null ? 1 : page, pageSize == null ? 10 : pageSize);
            QueryWrapper<CategoryMapping> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("createdTime");
            IPage<CategoryMapping> page2 = this.page(pages, queryWrapper);
            return page2;
      }

      @Override
      public CategoryMapping getChannelByIdAndSource(int categoryId, int source) {
            return categoryMappingMapper.getChannelByIdAndSource(categoryId, source);
      }
}
