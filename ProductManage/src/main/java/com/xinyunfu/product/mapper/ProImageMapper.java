package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface ProImageMapper extends BaseMapper<ProImage> {

    @Select("select count(0) from pro_image where pro_id= #{proId}")
    int checkProImageByProId(long proId);

    @Select("select img_url from pro_image where pro_id=#{proId} and is_default=1")
    String findImgUrlByProId(Long proId);
}
