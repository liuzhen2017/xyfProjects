package com.xinyunfu.product.mapper;





import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.Freight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * mapper接口
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface FreightMapper extends BaseMapper<Freight>{

    @Select("select * from pro_freight where freight_id=#{freightId}")
	Freight selectFreightById(@Param("freightId") Long freightId);


}
