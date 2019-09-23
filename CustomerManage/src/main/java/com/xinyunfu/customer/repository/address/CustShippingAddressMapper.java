package com.xinyunfu.customer.repository.address;

import com.xinyunfu.customer.domain.address.CustShippingAddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustShippingAddressMapper {
    List<CustShippingAddressDTO> findByUserNo(Long userNo);
    List<Long> findIdByUerNo(Long userNo);
    CustShippingAddressDTO findById(Long id);
    void delete(Long id);
    Long add(CustShippingAddressDTO address);
    void update(CustShippingAddressDTO address);
}
