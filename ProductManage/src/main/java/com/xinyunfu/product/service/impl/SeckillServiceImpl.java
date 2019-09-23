package com.xinyunfu.product.service.impl;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.ChannelId;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.mapper.ProChannelMapper;
import com.xinyunfu.product.mapper.ProSkuMapper;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private IProductService productService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private ProChannelMapper channelMapper;
    @Autowired
    private IProDetailsService proDetailsService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISkuService skuService;
    @Autowired
    private IFreightService freightService;
    @Autowired
    private ProSkuMapper skuMapper;

    @Autowired
    private Environment env;



    /*
     *查询秒杀商品
     */
    @Override
    public ResponseInfo<SecKillDTO> querySecKill() {
        long channelId = ChannelId.SECKILL;
        int times = env.getProperty("product.application.times",Integer.class);
        SecKillDTO secKillDTO = new SecKillDTO();
        ProChannel proChannel = channelMapper.findChannelByChannelId(channelId);
        List<ProChannel> proChannels = channelMapper.findProChannelNameList(channelId);
        if (proChannels==null || proChannels.isEmpty()){
            log.warn("============channelId={}的分类没有子分类或者子分类已被禁用============",channelId);
             return ResponseInfo.success(secKillDTO.setProChannel(proChannel));

        }
        long channelId1=0;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        List<KillChannelDTO> killChannelDTOS = new ArrayList<>();
        for (ProChannel channel : proChannels
        ) {
            KillChannelDTO killChannelDTO = new KillChannelDTO();
            int i = Integer.valueOf((channel.getChannelName().split(":"))[0]);
//            if (hour != 0) {
            if (i < hour-(times-1) && i != 0) {
                killChannelDTO.setChannelId(channel.getChannelId())
                        .setChannelName(channel.getChannelName())
                        .setKillStatus(-1);
                killChannelDTOS.add(killChannelDTO);
            } else if (i == hour || i==hour-(times-1)) {
                killChannelDTO.setChannelId(channel.getChannelId())
                        .setChannelName(channel.getChannelName())
                        .setKillStatus(0);
                killChannelDTOS.add(killChannelDTO);
                channelId1=channel.getChannelId();

            } else {
                killChannelDTO.setChannelId(channel.getChannelId())
                        .setChannelName(channel.getChannelName())
                        .setKillStatus(1);
                killChannelDTOS.add(killChannelDTO);
            }
//            log.info("============================"+killChannelDTOS.toString());
        }
            ProChannel currentSecKill = channelMapper.findChannelByChannelId(channelId1);
            List<ProDto> proDtos = productService.queryProDtosListByChannelId(channelId1);
            secKillDTO.setProChannel(proChannel).setKillChannelDTOS(killChannelDTOS).setCurrentSecKill(currentSecKill).setProDTOs(proDtos);
            if (secKillDTO == null)
                throw new RuntimeException("查询秒杀商品失败");
            else
                return ResponseInfo.success(secKillDTO);
        }

    @Override
    public void updateKillStatus() {

    }


//    @Override
//    public List<ProDto> findProDtoByChannelId(Long channelId) {
//        List<ProDto> proDtos = productService.queryProDtosByChannelId(channelId,1);
//        if (proDtos==null || proDtos.isEmpty()){
//            throw new RuntimeException("暂无商品");
//        }
//        for (ProDto proDto : proDtos
//             ) {
//            BigDecimal price=skuMapper.findDefaultPriceByProId(proDto.getProId());
//            BigDecimal seckillPrice=skuMapper.findDefaultSeckillPriceByProId(proIdDTO.getProId())
//            Integer allStock=skuService.findAllStockByProId(proIdDTO);
//            Integer allSellStock=skuService.findAllSellStockByProId(proIdDTO);
//            proDto.setPrice(price).setSeckillPrice(allStock).setAllSellStock(allSellStock);
//        }
//        return proDtos;
//    }

//    @Override
//    public void updateKillStatus() {
//
//    }
    /*
     *根据商品id查询秒杀商品详情
     */
//    @Override
//    public ProductDTO findProductDTOByProId(ProIdDTO proIdDTO) {
//        Long proId=proIdDTO.getProId();
//        Product product=productService.findProductById(proId);
//        ProDetails proDetails=proDetailsService.findProDetailsById(proId).getData();
//        List<ProSku> skuList=skuService.findSkuByProId(proIdDTO);
//        List<ProImage> imageList=fileService.findImageByProId(proId).getData();
//        BigDecimal postage=freightService.queryPostage(proId);
//        ProductDTO productDTO=new ProductDTO();
//        int allSellStock=skuService.findAllSellStockByProId(proIdDTO);
//        BigDecimal price=skuService.findDefaultPriceByProId(proIdDTO);
//        productDTO.setProduct(product)
//                .setProDetails(proDetails)
//                .setImageList(imageList)
//                .setPostage(postage)
//                .setAllSellStock(allSellStock)
//                .setPrice(price);
//        return productDTO;
//    }


}