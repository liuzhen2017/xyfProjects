package com.xinyunfu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.JdAddr;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JDAddrService extends IService<JdAddr> {

      /**\
       * 根据行政编码查询
       * @return
       */

      List<Integer> queryByCode(List<String> codes);

      // 根据省名字查询 jd area_id
      JdAddr getProvinceByName(String provinceName);

      //根据当前区域名称和父类id查询
      JdAddr getAreaByNameAndParentId(Long parentAreaId,String areaName);
}