package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.constant.Redis;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.mapper.BannerMapper;
import com.xinyunfu.product.mapper.ProChannelMapper;
import com.xinyunfu.product.mapper.ProChannelRelationMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.model.*;
import com.xinyunfu.product.service.IChannelRelationService;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@Service
@Slf4j
public class ChannelServiceImpl extends ServiceImpl<ProChannelMapper, ProChannel> implements IChannelService {

    @Autowired
    private ProChannelMapper proChannelMapper;
    @Autowired
    private RedisCommonUtil redis;
    @Autowired
    private IProductService productService;
    @Autowired
    private IChannelRelationService channelRelationService;
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProChannelRelationMapper channelRelationMapper;


    /*
     * 修改分类
     */
    @Override
    public ResponseInfo<String> update(ProChannel proChannel) {
        if (this.updateById(proChannel)) {
            return ResponseInfo.success("修改成功!");
        } else {
            return ResponseInfo.error("修改失败!");
        }

    }

    /*
     * 根据id查询分类
     */
    @Override
    public ResponseInfo<ProChannel> findChannelById(Long channelId) {
        QueryWrapper<ProChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("channel_id", channelId);
        ProChannel proChannel = proChannelMapper.selectOne(wrapper);
        return ResponseInfo.success(proChannel);
    }

    /*
     * 新增分类
     */
    @Override
    public ResponseInfo<String> saveProChannel(ProChannel proChannel) {
        if (this.save(proChannel)) {
            return ResponseInfo.success("新增成功!");
        } else {
            return ResponseInfo.error("新增失败!");
        }

    }

    /*
     * 根据父id查询子分类
     */
    public List<ProChannel> findProChannelList(Long fatherId) {
        QueryWrapper<ProChannel> queryWrapper = new QueryWrapper<ProChannel>();
        queryWrapper.eq("father_id", fatherId).eq("status", 0).orderBy(true, true, "sort_number");
        List<ProChannel> list = proChannelMapper.selectList(queryWrapper);
//		if (null==list||list.isEmpty())
//            throw new RuntimeException("暂无数据");
//		else
        return list;
    }

    /*
     * 修改分类状态,0启用,1禁用,2删除
     */
    //	@Override
    //	public ResponseInfo<String> updateStatus(Integer channelId, Integer status) {
    //		try {
    //			ProChannel proChannel=this.getById(channelId);
    //			if (proChannel.getIsParent()) {
    //				List<ProChannel> list=this.findProChannelList(channelId);
    //				for (ProChannel proChannel2 : list) {
    //					if (proChannel2.getIsParent()) {
    //						List<ProChannel> channelLists = findProChannelList(proChannel2.getChannelId());
    //					}
    //				}
    //			}
    //		} catch (Exception e) {
    //			// TODO: handle exception
    //		}
    //	}
    /*
     * 查询一级分类
     */
    @Override
    public ResponseInfo<List<ProChannel>> queryFirstChannels(Long fatherId) {
        Object obj = redis.getCache(Redis.FIR_CHANNEL_KEY);
        if (obj == null) {
            List<ProChannel> list = this.findProChannelList(fatherId);
            if (null == list || list.isEmpty()) {
                throw new RuntimeException("暂无数据");
            } else {
                redis.setCache(Redis.FIR_CHANNEL_KEY, list, 3600);
                return ResponseInfo.success(list);
            }
        } else {
            List<ProChannel> firChannel = (List<ProChannel>) obj;
            return ResponseInfo.success(firChannel);
        }


    }

    /*
     * 查询一级分类下所有二级分类及其三级分类
     */
    @Override
    public ResponseInfo<List<ChannelDto>> querySecAndThiChannels(Long channelId) {
        List<ChannelDto> list = new ArrayList<>();
        String key = Redis.SECANDTHI_CHANNEL_KEY + channelId;
        Object obj = redis.getCache(key);
        if (obj == null) {
            List<ProChannel> secList = this.findProChannelList(channelId);
            if (secList != null && !secList.isEmpty()) {
                for (ProChannel proChannel : secList) {
                    ChannelDto channelDto = new ChannelDto();
                    List<ProChannel> thiList = this.findProChannelList(proChannel.getChannelId());
                    channelDto.setSeconedChannel(proChannel).setThirdChannel(thiList);
                    list.add(channelDto);
                }
                redis.setCache(key, list, 3600);
                return ResponseInfo.success(list);
            } else {
                throw new RuntimeException("暂无二级分类");
            }
        } else {
            list = (List<ChannelDto>) redis.getCache(key);
            return ResponseInfo.success(list);
        }

//        if (redis.exists(key)) {
//             list= (List<ChannelDto>) redis.getCache(key);
//            return ResponseInfo.success(list);
//        } else {
//            List<ProChannel> secList = this.findProChannelList(channelId);
//            if (secList != null && !secList.isEmpty()) {
//                for (ProChannel proChannel : secList) {
//                    ChannelDto channelDto = new ChannelDto();
//                    List<ProChannel> thiList = this.findProChannelList(proChannel.getChannelId());
//                    channelDto.setSeconedChannel(proChannel).setThirdChannel(thiList);
//                    list.add(channelDto);
//                }
//                redis.setCache(key, list, 3600);
//                return ResponseInfo.success(list);
//            } else {
//                throw new RuntimeException("暂无二级分类");
//            }
//        }
    }

    /*
     * 分页查询
     */
    @Override
    public ResponseInfo<IPage<ProChannel>> findProductByPage(ProChannel proChannel, Integer page, Integer pageSize) {
        Page<ProChannel> pages = new Page<ProChannel>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
        QueryWrapper<ProChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_time");
        IPage<ProChannel> page2 = this.page(pages, queryWrapper);
        return ResponseInfo.success(page2);
    }

    @Override
    public List<Ztree> selectProChannelTree(ProChannel proChannel) {
//        String key=Redis.CHANNEL_TREE_KEY+proChannel.getChannelId();
//        if (redis.exists(key)){
//            List<Ztree> ztrees = (List<Ztree>)redis.getCache(key);
//            return ztrees;
//        }
        LambdaQueryWrapper<ProChannel> wrapper = new LambdaQueryWrapper<>();
        if (proChannel.getChannelId() != null) {
            wrapper.eq(ProChannel::getChannelId, proChannel.getChannelId());
        }
        if (proChannel.getChannelName() != null && StringUtils.isNotEmpty(proChannel.getChannelName())) {
            wrapper.eq(ProChannel::getChannelName, proChannel.getChannelName());
        }
        if (proChannel.getFatherId() != null) {
            wrapper.eq(ProChannel::getFatherId, proChannel.getFatherId());
        }
        wrapper.eq(ProChannel::getStatus, 0);


//        String key = Redis.ALL_CHANNEL_KEY;
        List<ProChannel> channelList ;
        //todo 暂时不使用缓存
//        if (redis.exists(key)){
//            channelList=(List<ProChannel>)redis.getCache(key);
//            log.info("==========从缓存获取所有proChannel,Size ={}==========",channelList.size());
//        }else {
        channelList = proChannelMapper.selectList(wrapper);
//            if(!channelList.isEmpty() && channelList.size() >0) {
//                redis.setCache(key, channelList, 24 * 3600);
//            }
        log.info("==========从DB获取所有proChannel,Size ={}==========", channelList.size());
//        }
        List<Ztree> ztrees = initZtree(channelList);

        return ztrees;
    }

    @Override
    public ProChannel selectByChannelId(Long channelId) {
        return proChannelMapper.selectByChannelId(channelId);
    }

    @Override
    public Long findMaxChannelId() {
        return proChannelMapper.findMaxChannelId();
    }

    /**
     * 根据channelId查寻ProChannelDTO
     *
     * @param channelPageDTO
     * @return
     */
    @Override
    public ResponseInfo<ProChannelDTO> findProChannelDTOById(ChannelPageDTO channelPageDTO) {
        ProChannel proChannel = this.selectByChannelId(channelPageDTO.getChannelId());
        IPage<ProChannelRelation> pages = new Page<>(channelPageDTO.getPage() == null ? 1 : channelPageDTO.getPage(),
                channelPageDTO.getPageSize() == null ? 15 : channelPageDTO.getPageSize());
        LambdaQueryWrapper<ProChannelRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProChannelRelation::getChannelId, channelPageDTO.getChannelId());
        IPage<ProChannelRelation> PCRpage = channelRelationService.page(pages, wrapper);
        List<ProChannelRelation> records = PCRpage.getRecords();
        List<ProDto> list = new ArrayList<>();
        for (ProChannelRelation record : records) {
            ProDto proDto = productService.findProDtoByProId(record.getProId());
            if (proDto == null) {
                continue;
            }
            list.add(proDto);
        }
        IPage<ProDto> proDtoPage = new Page<>();
        proDtoPage.setSize(PCRpage.getSize())
                .setRecords(list)
                .setTotal(PCRpage.getTotal())
                .setCurrent(PCRpage.getCurrent())
                .setPages(PCRpage.getPages());
        return ResponseInfo.success(new ProChannelDTO(proChannel, proDtoPage));
    }

    @Override
    public List<ChannelImgDTO> findBannerImgByChannelId(Long channelId) {
        ProChannel proChannel = this.findChannelById(channelId).getData();
        if (proChannel == null) {
            log.warn("=============id为channelId={}分类不可用或不存在===========", channelId);
            return null;
        } else {
            String bannerImgs = proChannel.getBannerImg();
            String linkUrls = proChannel.getLinkUrl();
            String forwardTypes = proChannel.getForwardType();
            if (StringUtils.isNotEmpty(bannerImgs) && StringUtils.isNotEmpty(linkUrls) && StringUtils.isNotEmpty(forwardTypes)) {
                String[] split2 = bannerImgs.split(";");
                String[] split1 = linkUrls.split(";");
                String[] split = forwardTypes.split(";");
                List<ChannelImgDTO> list = new ArrayList<>();
                for (int i = 0; i < split2.length; i++) {
                    list.add(new ChannelImgDTO(split2[i], split[i], split1[i]));
                }
                return list;
            } else {
                log.warn("=============id为channelId={}分类没有跳转图片===========", channelId);
                return null;
            }
        }
    }

    @Override
    public ResponseInfo<String> updateStatus(String ids, int status) {
        String[] split = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : split) {
            list.add(Long.valueOf(s));
        }
        ProChannel proChannel = new ProChannel();
        proChannel.setStatus(status);
        UpdateWrapper<ProChannel> wrapper = new UpdateWrapper<>();
        wrapper.in("id", list);
        if (proChannelMapper.update(proChannel, wrapper) > 0) {
            return ResponseInfo.success("删除成功");
        } else {
            return ResponseInfo.error("删除失败");
        }

    }

    public List<Ztree> initZtree(List<ProChannel> proChannelList) {
        return initZtree(proChannelList, null);
    }

    public List<Ztree> initZtree(List<ProChannel> proChannelList, List<String> roleProChannelList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = roleProChannelList != null && !proChannelList.isEmpty();
        for (ProChannel proChannel : proChannelList) {
            if (proChannel.getStatus() == 0) {
                Ztree ztree = new Ztree();
                ztree.setId(proChannel.getChannelId());
                ztree.setpId(proChannel.getFatherId());
                ztree.setName(proChannel.getChannelName());
                ztree.setTitle(proChannel.getChannelName());
                if (isCheck) {
                    ztree.setChecked(roleProChannelList.contains(proChannel.getChannelId() + proChannel.getChannelName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

//========================================排序=======================================
    @Override
    public Integer selectAmount(Long fatherId) {
        return proChannelMapper.selectAmount(fatherId);
    }

    @Override
    public ProChannel selectMinSortNumber(Long fatherId) {
        return proChannelMapper.selectMinSortNumber(fatherId);
    }

    @Override
    public ProChannel selectLast(Long fatherId,Integer sortNumber) {
        return proChannelMapper.selectLast(fatherId,sortNumber);
    }

    @Override
    public ProChannel selectNext(Long fatherId,Integer sortNumber) {
        return proChannelMapper.selectNext(fatherId,sortNumber);
    }

    @Override
    public ProChannel selectMax(Long fatherId) {
        return proChannelMapper.selectMax(fatherId);
    }

    @Override
    public boolean sort(ProChannel proChannel, Long channelId, Long fatherId, Integer sortNumber) {
        Long channelId1 = proChannel.getChannelId();
        Integer sortNumber1 = proChannel.getSortNumber();
        ProChannel proChannel1 = new ProChannel();
        proChannel1.setSortNumber(sortNumber1);
        UpdateWrapper<ProChannel> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("channel_id", channelId);
        int i=proChannelMapper.update(proChannel1, wrapper1);
        ProChannel proChannel2 = new ProChannel();
        proChannel2.setSortNumber(sortNumber);
        UpdateWrapper<ProChannel> wrapper2 = new UpdateWrapper<>();
        wrapper2.eq("channel_id", channelId1);
        int j=proChannelMapper.update(proChannel2, wrapper2);
        if (i>0 && j>0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updateSortNumber(Long channelId, int sortNumber) {
        ProChannel proChannel=new ProChannel();
        proChannel.setSortNumber(sortNumber);
        UpdateWrapper<ProChannel> wrapper=new UpdateWrapper<>();
        wrapper.eq("channel_id",channelId);
        int i=proChannelMapper.update(proChannel,wrapper);
        if (i==0){
            return false;
        }
        return true;
    }

    @Override
    public Integer findMaxSortNumber(Long fatherId) {
        return proChannelMapper.findMaxSortNumber(fatherId);
    }

    @Override
    public HomePageChannelDto findHomePageChannelDtoByChannelId(Long channelId) {
        HomePageChannelDto homePageChannelDto=proChannelMapper.findHomePageChannelDtoByChannelId(channelId);
        List<HomePageChannelDto> childs=proChannelMapper.findHomePageChannelDtoByFatherId(channelId);
        if (childs==null || childs.isEmpty()){
            log.warn("============channelId={}的分类下没有子分类或子分类已被禁用================",channelId);
            return homePageChannelDto;
        }
        List<HomePageChannelDto> homePageChannelDtos=new ArrayList<>();
        for (HomePageChannelDto child : childs) {
            List<HomePageChannelDto> childs1=proChannelMapper.findHomePageChannelDtoByFatherId(child.getChannelId());
            child.setChilds(childs1);
            if (child.getChannelTypeId()!=10 && child.getChannelTypeId()!=2 ){
                List<ProDto> proDtos=productMapper.findProDtosByChannelId(child.getChannelId());
                for (ProDto proDto : proDtos) {
                    ProChannelRelation channelRelation=channelRelationMapper.getByProIdAndChannelId(child.getChannelId(),proDto.getProId());
                    if (channelRelation==null || StringUtils.isEmpty(channelRelation.getImgUrl())){
                        continue;
                    }else {
                        proDto.setImageUrl(channelRelation.getImgUrl()).setColor(channelRelation.getColor());
                    }
                }
                child.setProDtos(proDtos);
            }
            homePageChannelDtos.add(child);
        }
        homePageChannelDto.setChilds(homePageChannelDtos);
        return homePageChannelDto;
    }

    /**
     * edit by lhpu
     * ============================================================================
     */


    @Override
    public boolean batchSaveProChannel(List<ProChannel> proChannels) {

        return this.saveBatch(proChannels);
    }

    @Override
    public ResponseInfo<String> getChannelNameByChannelId(Long channelId) {
        String result = proChannelMapper.getChannelNameByChannelId(channelId);
        if (result != null) {
            return ResponseInfo.success(result);
        }
        return new ResponseInfo("1111", "查询分类名称失败", null);
    }



}
