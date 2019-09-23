package com.xinyunfu.product.controller;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.Redis;
import com.xinyunfu.product.dto.ActDTO;
import com.xinyunfu.product.dto.HomePageDTO;
import com.xinyunfu.product.dto.SecKillDTO;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.service.ActService;
import com.xinyunfu.product.service.HomePageChannelsService;
import com.xinyunfu.product.service.IBannerService;
import com.xinyunfu.product.service.SeckillService;
import com.xinyunfu.product.utils.RedisCommonUtil;
import com.xinyunfu.product.utils.ResInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/11
 */
@RestController
@RequestMapping("/homePage")
@Slf4j
public class HomePageController {
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private HomePageChannelsService homePageChannelsService;
    @Autowired
    private ActService actService;
    @Autowired
    private SeckillService secKillService;
    @Autowired
    private RedisCommonUtil redis;


    @PostMapping("/show")
    public ResponseInfo<HomePageDTO> queryHomePage() {
        HomePageDTO homePageDTO = new HomePageDTO();
        if (redis.exists(Redis.HOMEPAGE_CACHE_KEY))
            redis.del(Redis.HOMEPAGE_CACHE_KEY);
        Object obj = redis.getCache(Redis.HOMEPAGE_CACHE_KEY);
        log.info("=======开始请求首页=========,cache ={}",obj);
        if(obj ==null){
            log.info("从数据库获取");
            List<ProBanner> list1 = bannerService.queryBanners();
            List<ProChannel> list2 = homePageChannelsService.queryProChannel();
            ActDTO actDTO = actService.queryAct().getData();
            homePageDTO.setBanners(list1)
                    .setChannels(list2)
                    .setActDTO(actDTO);
            redis.setCache(Redis.HOMEPAGE_CACHE_KEY, homePageDTO, 24 * 3600);

        }else {
            log.info("==redis有缓存数据====");
            homePageDTO = (HomePageDTO)obj;

        }

        Object obj2 = redis.getCache(Redis.SECKILL_CACHE_KEY);
        redis.del(Redis.SECKILL_CACHE_KEY);
        SecKillDTO secKillDTO = new SecKillDTO();
        if(obj2 == null )
        {
            //如果为空,择查询数据库
            log.info("=====开始查询商品数据===");
            secKillDTO = secKillService.querySecKill().getData();
            redis.setCache(Redis.SECKILL_CACHE_KEY,secKillDTO,30*60);
        }else {
            log.info("=====取缓存数据===");
            secKillDTO = (SecKillDTO) obj2;
        }
        homePageDTO.setSecKillDTO(secKillDTO);
        if (homePageDTO != null) {
            return ResponseInfo.success(homePageDTO);
        } else {
            return new ResInfo(Res.NO_DATA);
        }






    }


}
