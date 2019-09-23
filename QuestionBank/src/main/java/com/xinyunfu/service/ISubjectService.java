package com.xinyunfu.service;

import com.xinyunfu.model.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface ISubjectService extends IService<Subject> {

    /**
     * 获取随机的六道题 的ID
     * @return
     */
    int[]  getSubject();

    /**
     * 导入题库
     * @param currentUserId 当前用户ID
     * @param type          导入的类型（生活常识=0,基本法律=1）
     * @param file 文件
     * @param fileName 文件名字
     * @return
     */
    boolean inputQuestionBank(String currentUserId, Integer type, MultipartFile file,String fileName) throws IOException;

}
