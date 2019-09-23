package com.xinyunfu.product.controller;

import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private IProductService productService;

//    /*
//    * 根据分类id查询秒杀商品
//     */
//    @PostMapping("/findProDtoByChannelId")
//    public ResponseInfo<PageVO<ProDto>> findProDtoByChannelId(@RequestBody ChannelPageDTO channelPageDTO) {
//        if (channelPageDTO == null) {
//            return new ResInfo(Res.ERROR_PARAM);
//        } else {
//            long channelId = channelPageDTO.getChannelId();
//            List<ProDto> proDtos = productService.(channelId);
//            PageVO<ProDto> page = new PageVO<>(proDtos, channelPageDTO.getPageSize());
//            page.setCurrent_page(channelPageDTO.getPage());
//            page.setSize(channelPageDTO.getPageSize());
//            List<ProDto> currentPageData = page.currentPageData();
//            page.setCurrentPageData(currentPageData);
//            if (page==null)
//                return new ResInfo(Res.NO_DATA);
//            else
//                return ResponseInfo.success(page);
//        }
//
//    }

}
