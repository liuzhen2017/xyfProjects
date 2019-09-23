package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.ProImageMapper;
import com.xinyunfu.product.model.ProImage;
import com.xinyunfu.product.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@Service
@PropertySource("classpath:/properties/image.properties")
@Slf4j
public class FileServiceImpl extends ServiceImpl<ProImageMapper, ProImage> implements IFileService {

    @Autowired
    private ProImageMapper proImageMapper;
    /**
     * 单个文件大小
     */
    @Value("${uploadMaxSize}")
    private int uploadMaxSize;
    //定义服务器路径
    @Value("${img}")
    private String localDirPath;
    //定义虚拟路径名称
    @Value("${image.urlPath}")
    private String urlPath;
//      @Autowired
//      private SftpUtil sftpUtil;

    /**
     * 根据商品id查询图片
     *
     * @param proId
     * @return
     */
    public List<ProImage> findImageByProId(Long proId) {
        QueryWrapper<ProImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("status", 0);
        List<ProImage> list = proImageMapper.selectList(queryWrapper);
        if (list == null || list.isEmpty()) {
            log.info("==============id为proId={}的商品图片不存在或已被禁用============", proId);
        }
        return list;
    }

    /*
     *根据proId查询默认图片
     */
    public String findDefaultImageByProId(Long proId) {
        QueryWrapper<ProImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pro_id", proId).eq("is_default", 1).eq("status", 0);
        String imgUrl = proImageMapper.selectOne(queryWrapper).getImgUrl();
        if (StringUtils.isNotEmpty(imgUrl))
            return imgUrl;
        else
            log.info("=================id为id={}的商品暂无暂无默认图片", proId);
        return null;
//        return imgUrl;
    }


    /*
     *  图片上传
     */
    @Override
    public ResponseInfo<String> uploadProductImage(MultipartFile file, Long proId) {
        ProImage proImage = new ProImage();
        //如果文件不为空,写入上传路径
        if (!file.isEmpty()) {
            if (file.getSize() > uploadMaxSize * 1024 * 1024) {
                return ResponseInfo.error("上传的文件不能超过" + uploadMaxSize + "M");
            }
        }
        try {
            InputStream inputStream = file.getInputStream();
            //获取图片名称  a.jpg  A.JPG
            String fileName = file.getOriginalFilename();
            //将字符统一转化为小写
            fileName = fileName.toLowerCase();
            //校验图片类型  使用正则表达式判断字符串.
            if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
                return ResponseInfo.error("图片格式不正确!");
            }
            //判断是否为恶意程序
            BufferedImage bufferedImage =
                    ImageIO.read(inputStream);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width == 0 || height == 0) {
                return ResponseInfo.error("请上传图片!");
            }
            //时间转化为字符串 2019/5/31
            String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            //准备文件夹  /image/yyyy/MM/dd
            String filePath = localDirPath + dateDir;
            //使用UUID定义文件名称 uuid.jpg
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //图片类型 a.jpg 动态获取 ".jpg"
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            fileName = filePath + "/" + uuid + fileType;
            // 将上传文件保存到一个目标文件当中
//                  sftpUtil.connect();
//                  sftpUtil.uploadFile(filePath, fileName, inputStream);
//                  sftpUtil.disconnect();
            //拼接url路径   http://image.xinyunfu.com/yyyy/MM/dd/uuid.jpg
            String urlParh = urlPath + dateDir + "/" + uuid + fileType;
            //图片入库
            proImage.setImgUrl(urlParh).setProId(proId);
            this.save(proImage);
            return ResponseInfo.success(urlParh);
        } catch (Exception e) {
            return ResponseInfo.error("服务器异常,图片上传失败!");
        }

    }

    @Override
    public ResponseInfo<IPage<ProImage>> findImageByPage(ProImage proImage, Integer page, Integer pageSize) {
        Page<ProImage> pages = new Page<ProImage>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
        QueryWrapper<ProImage> queryWrapper = new QueryWrapper<>();
        IPage<ProImage> page2 = this.page(pages, queryWrapper);
        return ResponseInfo.success(page2);
    }

    @Override
    public ResponseInfo<String> updateStatus(Long[] ids, int status) {
        ProImage proImage = new ProImage();
        proImage.setStatus(status);
        List<Long> idList = Arrays.asList(ids);
        UpdateWrapper<ProImage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", idList);
        if (this.update(proImage, updateWrapper)) {
            if (status == 0) {
                return ResponseInfo.success("启用成功!");
            } else if (status == 1) {
                return ResponseInfo.success("禁用成功!");
            } else {
                return ResponseInfo.success("删除成功!");
            }
        } else {
            if (status == 0) {
                return ResponseInfo.success("启用失败!");
            } else if (status == 1) {
                return ResponseInfo.success("禁用失败!");
            } else {
                return ResponseInfo.success("删除失败!");
            }
        }
    }

    @Override
    public ResponseInfo<List<List<ProImage>>> findImageByProIds(List<Long> proIds) {
        List<List<ProImage>> list = new ArrayList<>();
        for (Long proId : proIds) {
            List<ProImage> imageList = this.findImageByProId(proId);
            if (imageList == null || imageList.isEmpty())
                throw new RuntimeException("商品id为:" + proId + "的商品暂无图片");
            else
                list.add(imageList);
        }
        return ResponseInfo.success(list);
    }

//    @Override
//    public String findDefaultImageByProId(Long proId) {
//        return null;
//    }

    /*
     * 分类图片上传
     */
    @Override
    public ResponseInfo<String> uploadChannelImage(MultipartFile file, Integer channleId) {
        //如果文件不为空,写入上传路径
        if (!file.isEmpty()) {
            if (file.getSize() > uploadMaxSize * 1024 * 1024) {
                return ResponseInfo.error("上传的文件不能超过" + uploadMaxSize + "M");
            }
        }
        try {
            InputStream inputStream = file.getInputStream();
            //获取图片名称  a.jpg  A.JPG
            String fileName = file.getOriginalFilename();
            //将字符统一转化为小写
            fileName = fileName.toLowerCase();
            //校验图片类型  使用正则表达式判断字符串.
            if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
                return ResponseInfo.error("图片格式不正确!");
            }
            //判断是否为恶意程序
            BufferedImage bufferedImage =
                    ImageIO.read(inputStream);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width == 0 || height == 0) {
                return ResponseInfo.error("请上传图片!");
            }
            //时间转化为字符串 2019/7/11
            String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            //准备文件夹  /image/yyyy/MM/dd
            String filePath = localDirPath + dateDir;
            //使用UUID定义文件名称 uuid.jpg
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //图片类型 a.jpg 动态获取 ".jpg"
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            fileName = filePath + "/" + uuid + fileType;
            // 将上传文件保存到一个目标文件当中
//                  sftpUtil.connect();
//                  sftpUtil.uploadFile(filePath, fileName, inputStream);
//                  sftpUtil.disconnect();
            //拼接url路径   http://image.xinyunfu.com/yyyy/MM/dd/uuid.jpg
            String urlParh = urlPath + dateDir + "/" + uuid + fileType;
            return ResponseInfo.success(urlParh);
        } catch (Exception e) {
            return ResponseInfo.error("服务器异常,图片上传失败!");
        }
    }

    @Override
    public Map<Long, List<ProImage>> queryJDProImageReshelf() {
        QueryWrapper<ProImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source", 1).eq("status", 0);
        proImageMapper.selectList(queryWrapper);
        return null;
    }

    @Override
    public String saveProImages(List<ProImage> list) {

        if (this.saveBatch(list, 1)) {
            return "图片批量入库成功";

        } else {
            return "图片入库失败";
        }

    }

    @Override
    public boolean checkProImageByProId(long proId) {
        return proImageMapper.checkProImageByProId(proId) > 0;
    }

    @Override
    public ResponseInfo<String> deleteProImage(Long[] ids, int i) {
        return null;
    }


}
