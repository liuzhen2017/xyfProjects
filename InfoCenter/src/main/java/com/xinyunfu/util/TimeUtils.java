package com.xinyunfu.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XRZ
 * @date 2019/8/9
 * @Description :
 */
public class TimeUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    /**
     * 获取指定日期时间
     * @param timestamp
     * @return yyyy-MM-dd
     */
    public static String getStr(Timestamp timestamp){
        return sdf.format(new Date(timestamp.getTime()));
    }

}
