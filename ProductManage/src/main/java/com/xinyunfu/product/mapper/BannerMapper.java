package com.xinyunfu.product.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.ProBanner;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * mapper接口
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface BannerMapper extends BaseMapper<ProBanner>{

	@Select("select * from pro_banner where start_date<curdate() and end_date>curdate() and status=0")
	List<ProBanner> queryBanners();

	@Select("select link_url from pro_banner where banner_id=#{bannerId}")
	String selectLinkUrlByBannerId(@Param("bannerId") Long bannerId);

    @Update("update pro_banner set status=2 where banner_id=#{bannerId}")
    int deleteByBannerId(@Param("bannerId") String bannerId);
//	@Select("select * from product where prod_id in(select pro_id from pro_channel_relation where channel_id = #{channelId})and status=0")
//	List<Product> findProductByChannelId(Integer channelId);

//	@Select("select * from product order by updated_time desc limit #{start},#{rows}")
//	List<Product> findProductByPage(int start, Integer rows);


}
