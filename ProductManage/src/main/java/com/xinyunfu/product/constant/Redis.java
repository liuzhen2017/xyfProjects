package com.xinyunfu.product.constant;

/**
 * @author TYM
 * @date 2019/8/11
 * @Description : redis key
 */
public interface Redis {
    /**
     * 首页key
     */
    String HOMEPAGE_CACHE_KEY = "pm_home_page_cache";
    /**
     * 秒杀
     */
    String SECKILL_CACHE_KEY="pm_seckill_cache";
    /**
     * 一级分类
     */
    String FIR_CHANNEL_KEY="pm_fir_channel_cache";
    /**
     * 二三级分类 key前缀
     */
    String SECANDTHI_CHANNEL_KEY="pm_secAndThi_channel_";
    /**
     * 分类树前缀
     */
    String CHANNEL_TREE_KEY="channel_tree_";

    String ALL_CHANNEL_KEY="all_channel";
}
