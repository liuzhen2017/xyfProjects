package com.xinyunfu.customer.repository.address;

import com.xinyunfu.customer.domain.address.CustAreaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustAreaMapper {

    List<CustAreaDTO> findByAreaLevel(Integer areaLevel);
    List<Long> findIdByAreaLevel(Integer areaLevel);
    List<CustAreaDTO> findByParentId(Long parentId);
    List<Long> findIdByParentId(Long parentId);
    CustAreaDTO findById(Long id);
}
