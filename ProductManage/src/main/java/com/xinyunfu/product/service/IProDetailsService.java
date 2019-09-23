package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tym
 * @since 2019/7/9
 */
public interface IProDetailsService extends IService<ProDetails> {

    ProDetails findProDetailsByProId(Long proId);

    ResponseInfo<List<ProDetails>> findProDetailsByProIds(List<Long> proIds);

    ResponseInfo<String> saveProDetails(ProDetails proDetails);

    List<ProDetails> queryJDProDetaislsReshelf();

    Boolean checkProDetailsByProId(long proId);

    ResponseInfo<String> updateProDetails(ProDetails proDetails);

    ResponseInfo<PageVO<ProDetails>> findProDetailsByPage(ProDetails proDetails, Integer page, Integer pageSize);

    ResponseInfo<String> deleteProDetails(Long[] ids, int i);

    ResponseInfo<String> updateStatus(Long[] ids, int i);


    /**
     * edit by lhpu
     * ============================================================================
     */

    Boolean addProDetails(ProDetails proDetail);}
