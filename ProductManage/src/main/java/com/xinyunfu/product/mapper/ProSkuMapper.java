package com.xinyunfu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.model.ProSku;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * mapper接口
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface ProSkuMapper extends BaseMapper<ProSku> {
    /**
     * 根据proId查询sku的总库存
     *
     * @param proId
     * @param proType
     * @return
     */
    @Select("select sum(sell_stock) from pro_sku where pro_id=#{proId} ")
    Integer findAllSellStockByProId(@Param("proId") Long proId);

    /**
     * 根据proId查询sku的最低price
     *
     * @param proId
     * @return
     */
    @Select("select min(price) from pro_sku where pro_id=#{proId} ")
    BigDecimal findDefaultPriceByProId(@Param("proId") Long proId);

    /**
     * 根据proId查询sku的最低min_sell_price
     *
     * @param proId
     * @return
     */
    @Select("select min(min_sell_price) from pro_sku where pro_id=#{proId} ")
    BigDecimal findDefaultMinPriceByProId(@Param("proId") Long proId);

    /**
     * 根据proId查询sku的最低seckill_price
     *
     * @param proId
     * @return
     */
    @Select("select min(seckill_price) from pro_sku where pro_id=#{proId} ")
    BigDecimal findDefaultSeckillPriceByProId(@Param("proId") Long proId);

    /**
     * 根据proId查询sku的总库存
     *
     * @param proId
     * @param proType
     * @return
     */
    @Select("select sum(stock) from pro_sku where pro_id=#{proId} ")
    Integer findAllStockByProId(@Param("proId") Long proId);

    @Update("update pro_sku set stock=stock-#{count} , sell_stock=sell_stock+#{count} where sku_id=#{skuId} and stock-#{count}>=0")
    Integer subtractStock(@Param("skuId") Long skuId, @Param("count") Integer count);

    @Update("update pro_sku set stock=stock+#{count} where sku_id=#{skuId}")
    Integer addStock(@Param("skuId") Long skuId, @Param("count") Integer count);

    /**
     * 根据proId查询商品秒杀总销量
     *
     * @param proId
     * @return
     */
    @Select("select sum(seckill_sell_stock) from pro_sku where pro_id=#{proId}")
    Integer findAllSeckillSellStockByProId(long proId);

    /**
     * 根据proId查询商品秒杀总库存
     *
     * @param proId
     * @return
     */
    @Select("select sum(seckill_stock) from pro_sku where pro_id=#{proId}")
    Integer findAllSeckillStockByProId(long proId);

    @Select("select p.pro_type from product p left join pro_sku s on p.pro_id=s.pro_id where s.sku_id=#{skuId}")
    Integer findProTypeBySkuId(long skuId);

    /**
     * 根据proId查询skuNo
     *
     * @param proId
     * @return
     */
    @Select("select sku_no from pro_sku where pro_id=#{proId}")
    String getSkuNo(Long proId);

    /**
     * 根据proId查询最低成本价
     *
     * @param proId
     * @return
     */
    @Select("select min(cost_price) from pro_sku where pro_id=#{proId}")
    BigDecimal findMinCostPriceByProId(Long proId);

    /**
     * 根据proId和groupNo查询skuId
     * @param proId
     * @param groupNo
     * @return
     */
    @Select("select sku_id from pro_sku where pro_id=#{proId} and group_no=#{groupNo}")
    Long getSkuIdByProIdAndGroupNo(@Param("proId") Long proId,@Param("groupNo") String groupNo);

    /**
     * edit by lupu
     * ========================================================================================
     */

    @Select("select count(0) from pro_sku where pro_id = #{proId}")
    int checkSkuByProId(Long proId);

    @Insert("insert into pro_sku (sku_no,group_no,pro_id,stock,sell_stock,img_url,price,integral_price,market_price,min_sell_price,cost_price) values(#{skuNo},#{groupNo},#{proId},#{stock},#{sellStock},#{imgUrl},#{price},#{integralPrice},#{marketPrice},#{minSellPrice},#{costPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "sku_id")
    int insert(ProSku prosku);

    @Select("select sku_no from pro_sku where sku_id = #{skuId}")
    String getSkuNoBySkuId(Long skuId);

    @Update("update pro_sku set status = 1 where pro_id = (select pro_id from product where pro_no = #{proNo})")
    int updateSkuByProNo(String proNo);

    @Select("select pro_id from pro_sku where sku_no = #{skuNo}")
    Long getProIdBySkuNo(String skuNo);


}
