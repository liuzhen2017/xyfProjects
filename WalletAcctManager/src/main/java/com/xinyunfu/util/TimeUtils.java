package com.xinyunfu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 时间工具类
 */
@Component
public class TimeUtils {



    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm:ss");


    /**
     * 获取指定日期时间
     * @param timestamp
     * @return yyyy-MM-dd
     */
    public static String getStr(Timestamp timestamp){
        return sdf.format(new Date(timestamp.getTime()));
    }



}
