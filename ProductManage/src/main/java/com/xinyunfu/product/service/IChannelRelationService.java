package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProChannelRelationDTO;
import com.xinyunfu.product.model.ProChannelRelation;
import com.xinyunfu.product.vo.PageVO;

import java.util.List;

public interface IChannelRelationService extends IService<ProChannelRelation> {


	ResponseInfo<String> savePCR(ProChannelRelation proChannelRelation);
//
//	ResponseInfo<String> updatePCR(ProChannelRelation proChannelRelation);

      ResponseInfo<String> updateStatus(Long[] ids, int status);

      ResponseInfo<PageVO<ProChannelRelation>> findChannelRelationByPage(ProChannelRelation proChannelRelation, Integer page,
                                                                         Integer pageSize);

      List<ProChannelRelation> queryJDProChannelRelationReshelf();

      ResponseInfo<String> deleteByIds(Long[] ids, int i);

      boolean checkByPidAndCid(Long proId, Integer channelId);

    boolean saveProChannelRelations(ProChannelRelationDTO proChannelRelationDTO);

    ProChannelRelation findChannelRelationByProId(Long proId);
}
