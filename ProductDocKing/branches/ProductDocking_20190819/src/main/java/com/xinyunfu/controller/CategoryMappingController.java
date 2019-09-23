package com.xinyunfu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.CategoryMapping;
import com.xinyunfu.service.CategoryMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoryMapping")
@Slf4j
public class CategoryMappingController {
      @Autowired
      private CategoryMappingService categoryMappingService;

      @RequestMapping("save.do")
      public ResponseInfo<String> saveMapping(CategoryMapping categoryMapping){
            if(categoryMappingService.saveMapping(categoryMapping)){
                  return ResponseInfo.success("新增成功!");
            }
            return ResponseInfo.error("新增失败!");
      }

      @RequestMapping("update.do")
      public ResponseInfo<String> updateMappingById(CategoryMapping categoryMapping){
            if(categoryMappingService.updateMappingById(categoryMapping)){
                  return ResponseInfo.success("更新成功!");
            }
            return ResponseInfo.error("更新失败!");
      }

      @RequestMapping("delete.do/{id}")
      public ResponseInfo<String> deleteMappingById(@RequestParam("id") long id){
            if(categoryMappingService.deleteMapping(id)){
                  return ResponseInfo.success("删除成功!");
            }
            return ResponseInfo.error("删除失败!");
      }

      @RequestMapping("get.do/{id}")
      public ResponseInfo<CategoryMapping> getMappingById(@RequestParam("id") long id){

            CategoryMapping categoryMapping = categoryMappingService.getMappingById(id);
            if(categoryMapping != null){
                  return ResponseInfo.success(categoryMapping);
            }
            return ResponseInfo.error(categoryMapping);
      }

      @RequestMapping("page.do")
      public ResponseInfo<IPage<CategoryMapping>> getMappingByPage(
                CategoryMapping categoryMapping, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){

            IPage<CategoryMapping> pageResult = categoryMappingService.getMappingByPage(categoryMapping, page, pageSize);
            if(pageResult != null){
                  return ResponseInfo.success(pageResult);
            }
            return ResponseInfo.error(pageResult);
      }
}
