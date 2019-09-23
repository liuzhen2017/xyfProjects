package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.BannerDTO;
import com.xinyunfu.product.dto.BannerIdDTO;
import com.xinyunfu.product.dto.ChannelIdDTO;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.service.IBannerService;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.utils.ResInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IChannelService channelService;

    /**
     * 根据分类id查询广告图片地址
     *
     * @param channelIdDTO
     * @return
     */
    @PostMapping("/queryBannerUrlByChannelId")
    public ResponseInfo<String> queryBannerById(@RequestBody ChannelIdDTO channelIdDTO) {
        if (channelIdDTO != null) {
//            Long channelId = channelIdDTO.getChannelId();
//            Long bannerId = channelService.findChannelById(channelId).getData().getBanner();
//            ProBanner banner=bannerService.queryBannerById(bannerId);
            String bannerUrl = channelService.findChannelById(channelIdDTO.getChannelId()).getData().getBannerUrl();

            if (bannerUrl != null)
                return ResponseInfo.success(bannerUrl);
            else
                return new ResInfo(Res.NO_DATA);
        }
        return new ResInfo(Res.ERROR_PARAM);
    }


    @PostMapping("/redirectBanner")
    public String redirectBanner(@RequestBody BannerIdDTO bannerIdDTO) {
        long bannerId = bannerIdDTO.getBannerId();
        String linkUrl = bannerService.selectLinkUrlByBannerId(bannerId);
        return "redirect: linkUrl";
    }

    /**
     * 新增广告
     *
     * @param proBanner
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveBanner(@RequestBody BannerDTO bannerDTO) {
        ProBanner proBanner = new ProBanner();
        proBanner.setBannerName(bannerDTO.getBannerName())
                .setForwardType(Integer.valueOf(bannerDTO.getForwardType()))
                .setImgUrl(bannerDTO.getImgUrl())
                .setSortNumber(bannerDTO.getSortNumber())
                .setStartDate(bannerDTO.getStartDate())
                .setEndDate(bannerDTO.getEndDate())
                .setStatus(bannerDTO.getStatus());
        proBanner.setColor(bannerDTO.getColor());

        if (bannerDTO.getForwardType().equals("1")) {
            proBanner.setLinkUrl(bannerDTO.getLinkUrl());
        } else if (bannerDTO.getForwardType().equals("2") || bannerDTO.getForwardType().equals("3")) {
            proBanner.setLinkUrl(bannerDTO.getChannelId().toString());
        } else {
            proBanner.setLinkUrl(bannerDTO.getProId().toString());
        }
        if (bannerService.save(proBanner))
            return ResponseInfo.success("新增成功!");
        return ResponseInfo.error("新增失败!");
    }

    /**
     * 修改广告
     *
     * @param proBanner
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateBanner(@RequestBody ProBanner proBanner) {
        if (bannerService.saveOrUpdate(proBanner))
            return ResponseInfo.success("修改成功");
        else
            return ResponseInfo.error("修改失败");
    }

    /**
     * 分页查询
     *
     * @param proBanner
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProBanner>> findBannerByPage(@RequestBody ProBanner proBanner,
                                                           @RequestParam("page") Integer page,
                                                           @RequestParam("pageSize") Integer pageSize) {
        IPage<ProBanner> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProBanner> wrapper = new LambdaQueryWrapper<>();

        if (proBanner.getStatus() != null && proBanner.getStatus() != -1) {
            wrapper.eq(ProBanner::getStatus, proBanner.getStatus());
        }
        if (!StringUtils.isEmpty(proBanner.getBannerName())) {
            wrapper.eq(ProBanner::getBannerName, proBanner.getBannerName());
        }
        if (!StringUtils.isEmpty(proBanner.getStartDate())) {
            wrapper.eq(ProBanner::getStartDate, proBanner.getStartDate());
        }
        if (!StringUtils.isEmpty(proBanner.getEndDate())) {
            wrapper.eq(ProBanner::getEndDate, proBanner.getEndDate());
        }
        return ResponseInfo.success(bannerService.page(pages, wrapper));
    }

    /**
     * 根据广告id查询广告
     *
     * @param bannerId
     * @return
     */
    @GetMapping("/selectById")
    ResponseInfo<ProBanner> selectProBannerById(@RequestParam("bannerId") Long bannerId) {
        if (bannerId == null)
            return new ResInfo(Res.ERROR_PARAM);
        if (bannerService.getById(bannerId) == null)
            return new ResInfo(Res.NO_DATA);
        return ResponseInfo.success(bannerService.getById(bannerId));
    }


    /**
     * 根据id删除广告
     *
     * @param ids 多个使用逗号拼接
     * @return
     */
    @GetMapping("/deleteProBanner")
    ResponseInfo<String> deleteProBanner(@RequestParam("ids") String ids) {
        return bannerService.delete(ids);
    }


}
