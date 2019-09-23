package com.xinyunfu.service.impl;

import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.model.Subject;
import com.xinyunfu.mapper.SubjectMapper;
import com.xinyunfu.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 获取随机的六道题 的ID
     *
     * @return
     */
    @Override
    public int[] getSubject() {
        //获取总题数
        int count = subjectMapper.count();
        int[] array = new int[6];

        for(int i = 0; i < 6; i++){
            array[i] = (int)(Math.random() * (count - 1) + 1);
            for(int j = 0; j < i;j++){//检测是否重复
                if(array[i] == array[j]){
                    i -= 1;//重新生成
                    break;
                }
            }
        }
        return array;
    }




    /**
     * 导入题库
     *
     * @param currentUserId 当前用户ID
     * @param type          导入的类型（生活常识=0,基本法律=1）
     * @param file 文件
     * @param fileName      文件名字
     * @return
     */
    @Override
    public boolean inputQuestionBank(String currentUserId, Integer type, MultipartFile file, String fileName) throws IOException {
        List<Subject> list = new ArrayList<>();
        //创建输入流
        InputStream is = file.getInputStream();
        //判断该excel版本是否最新版
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            wb = new XSSFWorkbook(is);
        }else{
            wb = new HSSFWorkbook(is);
        }
        //获取第一页
        Sheet sheet = wb.getSheetAt(0);
        //遍历每行
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            Subject sub = new Subject();
            for (int c = 1; c < 7; c++) {
                //获取每列
                Cell cell = row.getCell(c);
                if(null != cell){
                    //设置格式
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String v = cell.getStringCellValue();
                    if(StringUtils.isNotEmpty(v)){
                        if(c == 1){ //标题
                            sub.setSubjectTitle(v);
                        }else if( c == 2){
                            sub.setSubjectOption(v);
                        }else if( c == 3){
                            sub.setSubjectOption(sub.getSubjectOption()+"#"+v);
                        }else if( c == 4){
                            sub.setSubjectOption(sub.getSubjectOption()+"#"+v);
                        }else if( c == 5){
                            sub.setSubjectOption(sub.getSubjectOption()+"#"+v);
                        }else if( c == 6){
                            sub.setSubjectAnswer(v);
                        }
                    }
                }

            }
            sub.setSubjectType(type+"");
            sub.setCreatedBy(currentUserId);
            sub.setUpdatedBy(currentUserId);
            list.add(sub);
        }
        //入库
        if(!super.saveBatch(list))
            throw new CustomException(ExecptionEnum.ERROR_SAVE_FAIL);
        return true;
    }


}
