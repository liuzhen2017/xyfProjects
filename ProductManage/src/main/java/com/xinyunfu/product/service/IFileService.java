package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface IFileService extends IService<ProImage>{

	List<ProImage> findImageByProId(Long proId);

	ResponseInfo<String> uploadProductImage( MultipartFile file,Long proId);

	ResponseInfo<IPage<ProImage>> findImageByPage(ProImage proImage, Integer page, Integer pageSize);

	ResponseInfo<String> updateStatus(Long[] ids, int status);

	ResponseInfo<List<List<ProImage>>> findImageByProIds(List<Long> proIds);

	String findDefaultImageByProId(Long proId);

	ResponseInfo<String> uploadChannelImage(MultipartFile file, Integer channleId);

//    Map<Long,List<ProImage>> queryJDProImageReshelf();

    String saveProImages(List<ProImage> list);

    boolean checkProImageByProId(long proId);

    ResponseInfo<String> deleteProImage(Long[] ids, int i);

    Map<Long, List<ProImage>> queryJDProImageReshelf();

}
