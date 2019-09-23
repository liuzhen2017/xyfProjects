package com.xinyunfu.web;


import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 题库表 前端控制器
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private ISubjectService iSubjectService;


    /**
     * 导入题库
     * @param currentUserId 当前用户ID
     * @param type          导入的类型（生活常识=0,基本法律=1）
     * @param multipartFile 文件
     * @return
     */
    @PostMapping("/inputQuestionBank")
    public ResponseInfo<Object> inputQuestionBank(@RequestHeader(Common.UID) String currentUserId,
                                                  @RequestParam("type") Integer type,
                                                  @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if(null == type || null == multipartFile)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //验证格式
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new CustomException(ExecptionEnum.ERROR_FILE_TYPE);
        }
        return ResponseInfo.success(iSubjectService.inputQuestionBank(currentUserId, type, multipartFile,fileName));
    }

}
