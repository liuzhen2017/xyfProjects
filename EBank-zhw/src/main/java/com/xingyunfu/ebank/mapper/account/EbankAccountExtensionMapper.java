package com.xingyunfu.ebank.mapper.account;

import com.xingyunfu.ebank.domain.account.EbankAccountExtensionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;

@Mapper
public interface EbankAccountExtensionMapper {
    EbankAccountExtensionDTO findByAccountNo(String accountNo);
    void update(EbankAccountExtensionDTO extension);
    void insert(EbankAccountExtensionDTO extension);

    default void addOrUpdate(EbankAccountExtensionDTO extension){
        if(Objects.isNull(extension.getId())){
            this.insert(extension);
        }else{
            this.update(extension);
        }
    }
}
