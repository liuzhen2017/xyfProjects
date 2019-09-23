package com.xinyunfu.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 时间工具类
 */
public class TimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    /**
     * 获取七天后的日期
     * @return
     */
    public static LocalDateTime getNextTime(){
        return LocalDateTime.now().minusDays(-7);
    }

}
