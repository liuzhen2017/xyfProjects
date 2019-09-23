package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.CategoryMapping;

public interface CategoryMappingService extends IService<CategoryMapping> {

      Boolean saveMapping(CategoryMapping categoryMapping);

      Boolean updateMappingById(CategoryMapping categoryMapping);

      Boolean deleteMapping(long id);

      CategoryMapping getMappingById(long id);

      IPage<CategoryMapping> getMappingByPage(CategoryMapping categoryMapping, Integer page, Integer pageSize);

      CategoryMapping getChannelByIdAndSource(int categoryId, int source);

}
