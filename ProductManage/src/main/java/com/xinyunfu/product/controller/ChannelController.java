package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.ChannelId;
import com.xinyunfu.product.constant.Mode;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.Ztree;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.utils.ResInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@RestController
@RequestMapping("/channel")

public class ChannelController {

    @Autowired
    private IChannelService channelService;
    @Autowired
    private IProductService productService;

    /**
     * 新增分类
     *
     * @param proChannel
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProChannel(@RequestBody ProChannel proChannel) {
        Long channelId = channelService.findMaxChannelId() + 1;
        Integer sortNumber;
        if (channelService.findMaxSortNumber(proChannel.getFatherId()) != null) {
            sortNumber = channelService.findMaxSortNumber(proChannel.getFatherId()) + 1;
        } else {
            sortNumber = 0;
        }
        proChannel.setChannelId(channelId);
        proChannel.setSortNumber(sortNumber);
        if (channelService.save(proChannel))
            return ResponseInfo.success("新增成功");
        return ResponseInfo.error("新增失败");
    }

    /**
     * 修改分类
     *
     * @param proChannel
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProChannel(@RequestBody ProChannel proChannel) {
//        if (channelService.update(proChannel)) {
//            return ResponseInfo.success("修改成功");
//        } else {
//            return ResponseInfo.error("修改失败");
//        }
        if (proChannel == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            if (channelService.update(proChannel).isSuccess())
                return ResponseInfo.success("修改成功");
            else
                return new ResInfo(Res.NO_DATA);
        }

    }

    /**
     * 分页查询
     *
     * @param proChannel
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProChannel>> findProChannleByPage(@RequestBody ProChannel proChannel,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize) {
        IPage<ProChannel> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(ProChannel::getStatus, 2);
        if (proChannel.getChannelId() != null) {
            wrapper.eq(ProChannel::getChannelId, proChannel.getChannelId());
        }
        if (proChannel.getStatus() != null) {
            wrapper.eq(ProChannel::getStatus, proChannel.getStatus());
        }
        if (StringUtils.isNotEmpty(proChannel.getChannelName())) {
            wrapper.like(ProChannel::getChannelName, proChannel.getChannelName());
        }
        if (proChannel.getFatherId() != null) {
            wrapper.eq(ProChannel::getFatherId, proChannel.getFatherId());
        }
        if (StringUtils.isNotEmpty(proChannel.getFatherName())) {
            wrapper.like(ProChannel::getFatherName, proChannel.getFatherName());
        }
        wrapper.orderByAsc(ProChannel::getSortNumber);

        List<ProChannel> channels = new ArrayList<>();
        IPage<ProChannel> page1 = channelService.page(pages, wrapper);
        List<ProChannel> list = page1.getRecords();
        for (ProChannel channel : list) {
            Integer amount = channelService.selectAmount(channel.getChannelId());
            channel.setAmount(amount);
            channels.add(channel);
        }
        page1.setRecords(channels);
        return ResponseInfo.success(page1);
    }

    /*
     * 根据id查询目录信息
     */
    @GetMapping("queryChannel")
    public ResponseInfo<ProChannel> findChannelByParentId(Long channelId) {
        return channelService.findChannelById(channelId);
    }

    /*
     * 查询商品一级分类
     */
    @PostMapping("/queryFirChannels")
    public ResponseInfo<List<ProChannel>> queryFirstChannels() {
        long fatherId = 0;

        return channelService.queryFirstChannels(fatherId);
    }

    /*
     *根据一级分类id 查询二级分类及其三级分类
     */
    @PostMapping("/querySecAndThiChannels")
    public ResponseInfo<List<ChannelDto>> querySecAndThiChannels(@RequestBody ChannelIdDTO channelIdDTO) {
        if (channelIdDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            if (channelService.querySecAndThiChannels(channelIdDTO.getChannelId()) != null)
                return channelService.querySecAndThiChannels(channelIdDTO.getChannelId());
            else
                return new ResInfo(Res.NO_DATA);
        }
    }

    /*
     * 分页查询分类信息
     */
    @GetMapping("/queryByPage")
    public ResponseInfo<IPage<ProChannel>> findProChannelByPage(ProChannel proChannel, Integer page, Integer pageSize) {

        return channelService.findProductByPage(proChannel, page, pageSize);
    }


    @GetMapping("/selectByChannelId")
    public ResponseInfo<ProChannel> selectProChannelByChannelId(@RequestParam("channelId") Long channelId) {
        if (channelId == -1) {
            channelId = 1L;
        }
        return channelService.findChannelById(channelId);
    }

    @GetMapping("/selectProChannelById")
    ResponseInfo<ProChannel> selectProChannelById(@RequestParam("id") Long id) {
        return ResponseInfo.success(channelService.getById(id));
    }

    @PostMapping("/treeData")
    ResponseInfo<List<Ztree>> selectProChannelTree(@RequestBody ProChannel proChannel) {
        return ResponseInfo.success(channelService.selectProChannelTree(proChannel));
    }

    /**
     * 根据channelId查询prochannelDto
     *
     * @param channelPageDTO
     * @return
     */
    @PostMapping("/findProDtoByChannelId")
    public ResponseInfo<IPage<ProDto>> findProDtoByChannelId(@RequestBody ChannelPageDTO channelPageDTO) {
        if (channelPageDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            if (productService.getProDtoPageByChannelId(channelPageDTO) == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(productService.getProDtoPageByChannelId(channelPageDTO));
        }

    }

    @PostMapping("/findChannelImgDTO")
    public ResponseInfo<List<ChannelImgDTO>> findChannelImgDTOByChannelId(@RequestBody ChannelIdDTO channelIdDTO) {
        if (channelIdDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            List<ChannelImgDTO> list = channelService.findBannerImgByChannelId(channelIdDTO.getChannelId());
            if (list == null || list.size() == 0) {
                return ResponseInfo.success(null);
            } else {
                return ResponseInfo.success(list);
            }
        }
    }


    /**
     * 删除分类
     *
     * @param ids
     * @return
     */
    @GetMapping("/deleteProChannel")
    public ResponseInfo<String> deleteProChannel(@RequestParam("ids") String ids) {
        if (ids == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            return channelService.updateStatus(ids, 2);
        }
    }

    @GetMapping("/sort")
    public ResponseInfo<String> sort(@RequestParam("channelId") Long channelId,
                                     @RequestParam("fatherId") Long fatherId,
                                     @RequestParam("sortNumber") Integer sortNumber,
                                     @RequestParam("mode") Integer mode) {
        if (channelId == null || mode == null) {
            return new ResInfo(Res.ERROR_PARAM);
        }
        ProChannel proChannel;
        if (mode == Mode.TOP) {
            proChannel = channelService.selectMinSortNumber(fatherId);
            if (proChannel.getSortNumber() == sortNumber) {
                return ResponseInfo.success("已在第一位");
            }
            boolean flag = channelService.updateSortNumber(channelId, proChannel.getSortNumber() - 1);
            if (!flag) {
                return ResponseInfo.error("修改排序失败");
            }
            return ResponseInfo.success("修改排序成功");
        } else if (mode == Mode.LAST) {
            proChannel = channelService.selectLast(fatherId, sortNumber);
            if (proChannel == null) {
                return ResponseInfo.success("已在第一位");
            }
            boolean flag = channelService.sort(proChannel, channelId, fatherId, sortNumber);
            if (!flag) {
                return ResponseInfo.error("修改排序失败");
            }
            return ResponseInfo.success("修改排序成功");
        } else if (mode == Mode.NEXT) {
            proChannel = channelService.selectNext(fatherId, sortNumber);
            if (proChannel == null) {
                return ResponseInfo.success("已在最后一位");
            }
            boolean flag = channelService.sort(proChannel, channelId, fatherId, sortNumber);
            if (!flag) {
                return ResponseInfo.error("修改排序失败");
            }
            return ResponseInfo.success("修改排序成功");
        } else if (mode == Mode.BOTTOM) {
            proChannel = channelService.selectMax(fatherId);

            if (proChannel.getSortNumber() == sortNumber) {
                return ResponseInfo.success("已在最后一位");
            }
            boolean flag = channelService.updateSortNumber(channelId, proChannel.getSortNumber() + 1);
            if (!flag) {
                return ResponseInfo.error("修改排序失败");
            }
            return ResponseInfo.success("修改排序成功");
        } else {
            return new ResInfo(Res.ERROR_PARAM);
        }
    }

    /**
     * 根据channelId查询首页或者二级内页
     *
     * @return
     */
    @PostMapping("/findHomePageChannelDtoByChannelId")
    public ResponseInfo<HomePageChannelDto> findHomePageChannelDtoByChannelId(@RequestBody ChannelIdDTO channelIdDTO) {
        if (channelIdDTO==null){
            return new ResInfo(Res.ERROR_PARAM);
        }
        Long channelId;
        if (channelIdDTO.getChannelId() == null) {
            channelId = ChannelId.HOME_PAGE;
        } else {
            channelId = channelIdDTO.getChannelId();
        }
        HomePageChannelDto homePageChannelDto = channelService.findHomePageChannelDtoByChannelId(channelId);
        if (homePageChannelDto == null) {
            return new ResInfo(Res.NO_DATA);
        } else {
            return ResponseInfo.success(homePageChannelDto);
        }
    }


/**
 * edit by lhpu
 * ======================================================================
 */

    /**
     * 新增分类
     *
     * @param
     * @return
     */
    @PostMapping("/batchSave")
    public ResponseInfo<String> batchSaveProChannel(@RequestBody List<ProChannel> proChannels) {
        if (channelService.batchSaveProChannel(proChannels)) {
            return ResponseInfo.success("批量添加分类成功");
        }
        return ResponseInfo.error("批量添加分类成功");
    }

    /**
     * 根据分类 id查询分类名称
     *
     * @param channelId
     * @return
     */
    @GetMapping("/getChannelName")
    public ResponseInfo<String> getChannelNameByChannelId(@RequestParam("channelId") Long channelId) {
        return channelService.getChannelNameByChannelId(channelId);
    }

}
