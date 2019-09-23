package com.xinyunfu.product.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProImage;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IFileService;
import com.xinyunfu.product.utils.ResInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 * 
 *@author tym
 *@since  2019/7/9
 */
@RestController
@RequestMapping("/proImage")
public class FileController {

	@Autowired
	private IFileService fileService;
	/*
	 *商品 图片上传
	 */
    @GetMapping("/uploadProductImage")
    public ResponseInfo<String> uploadProductImage(@RequestPart("file") MultipartFile file, @RequestParam("proId")Long proId) {
       return fileService.uploadProductImage(file,proId);
    }


    /*
     * 分类图片上传
     */
    @PostMapping("/upload/uploadChannelImage")
    public ResponseInfo<String> uploadChannelImage(@RequestPart("file") MultipartFile file,Integer channleId){
    	return fileService.uploadChannelImage(file, channleId);
    }
    /*
	 * 分页查询商品图片信息
	 */
//	@GetMapping("/queryByPage")
//	public ResponseInfo<IPage<ProImage>> findProductByPage(ProImage proImage,Integer page,Integer pageSize) {
//		return fileService.findImageByPage(proImage,page,pageSize);
//	}
	/*
	 * 启用
	 */
	@GetMapping("/enabled")
	public ResponseInfo<String> enabled(Long[] ids){
		int status=0;   //启用
		return fileService.updateStatus(ids,status);
	}
	/*
	 * 禁用
	 */
	@GetMapping("/disable")
	public ResponseInfo<String> disable(Long[] ids){
		int status=1;   //禁用
		return fileService.updateStatus(ids,status);
	}
	/*
	 * 删除图片
	 */
	@GetMapping("/delete")
	public ResponseInfo<String> delete(Long[] skuIds){
		int status=2;   //删除
		return fileService.updateStatus(skuIds,status);
	}
    /**
     * 查询所有来源为京东,状态为0的商品的图片
     * @return
     */
    @GetMapping("/queryJDProImageReshelf")
    public Map<Long,List<ProImage>> queryJDProImageReshelf(){

        return fileService.queryJDProImageReshelf();
    }

    /**
     * 新增商品图片
     *
     * @param proImage
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProImage(@RequestBody ProImage proImage){

        boolean flag=fileService.save(proImage);
        if (flag)
            return ResponseInfo.success("新增商品图片成功!");
        else
            return ResponseInfo.error("新增商品图片失败!");
    }

    /**
     * 修改商品图片
     *
     * @param proImage
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProImage(@RequestBody ProImage proImage){
        boolean flag=fileService.saveOrUpdate(proImage);
        if (flag)
            return ResponseInfo.success("修改商品图片成功!");
        else
            return ResponseInfo.error("修改商品图片失败!");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProImage>> findProductByPage(@RequestBody ProImage proImage,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize){
        IPage<ProImage> pages=new Page<>(page,pageSize);
        LambdaQueryWrapper<ProImage> wrapper=new LambdaQueryWrapper<>();
        if (proImage.getStatus()!=null){
            wrapper.eq(ProImage::getStatus,proImage.getStatus());
        }
        if (proImage.getProId()!=null){
            wrapper.eq(ProImage::getProId,proImage.getProId());
        }
        return ResponseInfo.success(fileService.page(pages,wrapper));
    }

    /**
     * 删除商品图片
     *
     * @param ids
     * @return
     */
    @GetMapping("/deleteProImage")
    public ResponseInfo<String> deleteProImage(@RequestParam("ids") Long[] ids){
        return fileService.updateStatus(ids,2);
    }

    /**
     * 下架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProImage(@RequestParam("ids") Long[] ids){
        return fileService.updateStatus(ids,1);
    }

    /**
     * 上架商品
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProImage(@RequestParam("ids") Long[] ids){
        return fileService.updateStatus(ids,0);
    }

    /**
     * 根据商品id查询图片
     * @param proId
     * @return
     */
	@GetMapping("/queryProImageByProId")
    public ResponseInfo<List<ProImage>> queryProImageByProId(@RequestParam("proId") Long proId){
	    if (proId==null){
	        return new ResInfo(Res.ERROR_PARAM);
        }
	    return ResponseInfo.success(fileService.findImageByProId(proId));
    }
	@PostMapping("/saveProImages")
    public ResponseInfo<String> saveProImages(@RequestBody List<ProImage> list){
        return ResponseInfo.success(fileService.saveProImages(list));
    }


	/**
	 * 根据商品id查询图片是否存在
	 * @param proId
	 * @return
	 */
	@GetMapping("/checkProImageByProId")
	public ResponseInfo<Boolean> checkProImageByProId(@RequestParam("proId") Long proId){
		return ResponseInfo.success(fileService.checkProImageByProId(proId));
	}














}
