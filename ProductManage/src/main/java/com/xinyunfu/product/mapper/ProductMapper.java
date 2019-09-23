package com.xinyunfu.product.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.RProductDTO;
import org.apache.ibatis.annotations.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.model.Product;

/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface ProductMapper extends BaseMapper<Product> {

    @Insert("insert into product (pro_no,pro_name,pro_title,freight_id,store_id,unit,weight,source,coupon_type,remarks) values(#{proNo},#{proName},#{proTitle},#{freightId},#{storeId},#{unit},#{weight},#{source},#{couponType},#{remarks})")
    @Options(useGeneratedKeys = true, keyProperty = "proId")
    int insert(Product product);

    @Select("select pro_id from product where source=1 and status=0")
    List<Long> selectProIdList();

    @Select("select count(0) from product where pro_no = #{proNo}")
    int checkProByProNo(String proNo);

    @Select("select pro_id from product where pro_no = #{proNo}")
    Long getProIdByProNo(String proNo);

    @Select("select cn.channel_name from pro_channel cn left join pro_channel_relation pcr on cn.channel_id=pcr.channel_id LEFT JOIN product p on pcr.pro_id=p.pro_id where p.pro_id=#{proId} and cn.channel_type_id=1")
    List<String> selectChannelNameByProId(Long proId);

    /**
     * 根据商品关键字和分类id查询商品列表
     *
     * @param page
     * @param queryParam
     * @param channelId
     * @return
     */
    IPage<ProDto> findProDtoByProName(@Param("page") Page page,
                                      @Param("queryParam") String queryParam,
                                      @Param("channelId") Long channelId,
                                      @Param("orderByCloum") String orderByCloum,
                                      @Param("isAsc") Integer isAsc);


    IPage<ProDto> searchProDtoByProName(@Param("page") Page page, @Param("queryParam") String queryParam, @Param("orderByCloum") String orderByCloum, @Param("isAsc") Integer isAsc);

    /**
     * edit by lhpu
     * =================================================================================
     */

    @Select("select pro_id from product where source=#{source} and status=#{status}")
    List<Long> selectProductIds(@Param("source") int source, @Param("status") int status);

    Page<RProductDTO> getRProductDTO(@Param("page") Page page, @Param("rProductDTO") RProductDTO rProductDTO);


    Page<ProDto> getProDto(@Param("page") Page page, @Param("channelId") Long channelId);

    IPage<ProDto> getProDtoPageByChannelIdOrderByAllSellStockDesc(@Param("page") Page page, @Param("channelId") Long channelId);

    IPage<ProDto> getProDtoPageByChannelIdOrderByPriceDesc(@Param("page") Page page, @Param("channelId") Long channelId);

    IPage<ProDto> getProDtoPageByChannelIdOrderByPriceAsc(@Param("page") Page page, @Param("channelId") Long channelId);

    @Update("update product set status = 1 where pro_no = #{proNo}")
    int offSaleByproNo(String proNo);

    @Update("update product set status = 1 where pro_id = #{proId}")
    int offSaleByproId(Long proId);

    @Select("select * from product  where pro_id=(select s.pro_id from pro_sku s where s.sku_no =#{skuNo})")
    Product getProductBySkuNo(String skuNo);

    List<ProDto> findProDtosByChannelId(@Param("channelId") Long channelId);
}
