package com.xinyunfu.customer.repository.user;

import com.xinyunfu.customer.domain.user.CustCardInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;

@Mapper
public interface CustCardInfoMapper {

    CustCardInfoDTO findByCardNo(String cardNo);
    CustCardInfoDTO findByUserNo(Long userNo);
    void insert(CustCardInfoDTO cardInfo);
    void update(CustCardInfoDTO cardInfo);

    default void addOrUpdate(CustCardInfoDTO cardInfo){
        if(Objects.isNull(cardInfo.getId())){
            this.insert(cardInfo);
        }else{
            this.update(cardInfo);
        }
    }
}
