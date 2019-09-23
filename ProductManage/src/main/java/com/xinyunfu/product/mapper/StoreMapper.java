package com.xinyunfu.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author tym
 * @since 2019/7/10
 */
public interface StoreMapper extends BaseMapper<Store> {

    @Select("select store_id from store where owner_id=#{ownerId} and status=0")
    Long findStoreIdByOwnerId(@Param("ownerId") String ownerId);
    @Select("select store_name from store where owner_id=#{ownerId} and status=0")
    String findStoreNameByOwnerId(String ownerId);
}
