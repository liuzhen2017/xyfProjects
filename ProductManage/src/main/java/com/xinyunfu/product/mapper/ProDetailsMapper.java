package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * mapper接口
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface ProDetailsMapper extends BaseMapper<ProDetails>{

      @Select("select count(0) from pro_details where pro_id= #{proId}")
      int checkProDetailsByProId(long proId);

    List<ProDetails> selectProDetailsList(ProDetails proDetails);
}
