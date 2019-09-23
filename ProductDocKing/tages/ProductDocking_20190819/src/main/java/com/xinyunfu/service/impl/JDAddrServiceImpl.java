package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.model.JdAddr;
import com.xinyunfu.mapper.JDAddrMapper;
import com.xinyunfu.service.JDAddrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JDAddrServiceImpl extends ServiceImpl<JDAddrMapper, JdAddr>
          implements JDAddrService {

      @Autowired
      JDAddrMapper jdAddrMapper;

      //@Cacheable(cacheNames = {"getAllList"})
      //@Autowired
      public List<Integer> queryByCode(List<String> codes) {
            List<Integer> jdAddrList = new ArrayList<>();
            for (int i = 0; i < codes.size() - 1; i++) {
                  jdAddrMapper.queryByCode(Long.valueOf(codes.get(i)), (i + 1));
            }

            jdAddrMapper.queryByParmat(jdAddrList.get(1), codes.get(2));
            return jdAddrList;

      }

      @Override
      public JdAddr getProvinceByName(String provinceName) {
            return jdAddrMapper.getProvinceByName(provinceName);
      }

      @Override
      public JdAddr getAreaByNameAndParentId(Long parentAreaId, String areaName) {
            return jdAddrMapper.getAreaByNameAndParentId(parentAreaId, areaName);
      }


}