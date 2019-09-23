package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.model.Ztree;

import java.awt.color.ICC_Profile;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface IChannelService extends IService<ProChannel> {


    ResponseInfo<String> update(ProChannel proChannel);


    ResponseInfo<ProChannel> findChannelById(Long channelId);

    ResponseInfo<String> saveProChannel(ProChannel proChannel);

//	ResponseInfo<String> updateStatus(Integer channelId, Integer status);

    ResponseInfo<List<ProChannel>> queryFirstChannels(Long parentId);

    ResponseInfo<List<ChannelDto>> querySecAndThiChannels(Long channelId);


    List<ProChannel> findProChannelList(Long parentId);


    ResponseInfo<IPage<ProChannel>> findProductByPage(ProChannel proChannel, Integer page, Integer pageSize);

    boolean batchSaveProChannel(List<ProChannel> proChannels);

    List<Ztree> selectProChannelTree(ProChannel proChannel);

    ProChannel selectByChannelId(Long channelId);

    Long findMaxChannelId();

    ResponseInfo<ProChannelDTO> findProChannelDTOById(ChannelPageDTO channelPageDTO);

    List<ChannelImgDTO> findBannerImgByChannelId(Long channelId);

    ResponseInfo<String> updateStatus(String ids, int status);

    ResponseInfo<String> getChannelNameByChannelId(Long channelId);

    Integer selectAmount(Long fatherId);

    ProChannel selectMinSortNumber(Long fatherId);

    ProChannel selectLast(Long fatherId,Integer sortNumber);

    ProChannel selectNext(Long fatherId,Integer sortNumber);

    ProChannel selectMax(Long fatherId);

    boolean sort(ProChannel proChannel, Long channelId, Long fatherId, Integer sortNumber);

    boolean updateSortNumber(Long channelId, int sortNumber);

    Integer findMaxSortNumber(Long fatherId);

    HomePageChannelDto findHomePageChannelDtoByChannelId(Long channelId);
}
