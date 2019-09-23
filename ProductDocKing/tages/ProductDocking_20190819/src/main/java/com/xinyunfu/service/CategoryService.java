package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.Category;

public interface CategoryService extends IService<Category> {
      boolean saveOrUpdateCategory(Category category);
}
