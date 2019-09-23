package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.model.ProPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * mapper接口
 * </p>
 * 
 *@author tym
 *@since  2019/7/9
 */
public interface ProPackageMapper extends BaseMapper<ProPackage>{

//    @Select("select pro_id and package_price from pro_package where status=0")
//    List<Long> findAllProId();
    @Select("select package_price from pro_package where pro_id=#{proId}")
    BigDecimal getPriceByProId(Long proId);
    @Select("select * from pro_package where status=0")
    List<ProPackage> findAll(@Param("page") Page page);

    @Select("select package_price from pro_package where pro_id=#{proId}")
    BigDecimal findPriceByProId(Long proId);

    /**
     * 分页查询所有套餐商品
     * @param page
     * @return
     */
    IPage<ProDto> findAllPackage(@Param("page") Page page, @Param("proName") String proName);

    /**
     * edit by lhpu
     *
     * ===================================================================================
     *
     */
    @Select("select * from pro_package where package_name like concat(#{packageName},'%') and status=0")
    List<ProPackage> findAllByName(@Param("page") Page page,@Param("proName") String proName);



}
