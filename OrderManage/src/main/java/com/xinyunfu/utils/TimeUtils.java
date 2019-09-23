package com.xinyunfu.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 时间工具类
 */
@Component
public class TimeUtils {

    /**
     * 未付款订单的限时 （12小时）
     */
    public static long ORDER_UNPAID;

    /**
     * 已取消订单自动删除的限时 （7天）
     */
    public static long ORDER_AUTO_DELETE;

    /**
     * 自动确认收货的限时 （7天）
     */
    public static long ORDER_AUTO_COMFIRM_GOODS;

    /**
     * 延迟释放券的时长 （15分钟）
     */
    public static long ORDER_RELEASE_COUPON;


    @Value("${order.unpaid}")
    public void setOrderUnpaid(long orderUnpaid) {
        ORDER_UNPAID = orderUnpaid;
    }
    @Value("${order.auto.delete}")
    public void setOrderAutoDelete(long orderAutoDelete) {
        ORDER_AUTO_DELETE = orderAutoDelete;
    }
    @Value("${order.auto.comfirmGoods}")
    public void setOrderAutoComfirmGoods(long orderAutoComfirmGoods) { ORDER_AUTO_COMFIRM_GOODS = orderAutoComfirmGoods; }
//    @Value("${order.releaseCoupon}")
//    public void setOrderReleaseCoupon(long orderReleaseCoupon) {
//        ORDER_RELEASE_COUPON = orderReleaseCoupon;
//    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    /**
     * 获取1年后的日期
     * @return
     */
    public static String getNextTime(){
        return LocalDateTime.now().minusYears(-1).format(formatter);
    }
    /**
     * 获取指定 毫秒数 后的时间
     * @return  yyyy-MM-dd HH:mm:ss
     */
    public static String payTimeOut(long s){
        return LocalDateTime.now().minusSeconds(-(s/1000)).format(formatter2);
    }


    /**
     * 获取当前时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDate(){
        return sdf.format(new Date());
    }

    /**
     * 获取指定日期时间
     * @param timestamp
     * @return yyyy-MM-dd
     */
    public static String getStr(Timestamp timestamp){
        return sdf2.format(new Date(timestamp.getTime()));
    }

    /**
     * 获取指定日期时间
     * @param timestamp
     * @return yyyy-MM-dd HH:mm:sss
     */
    public static String getStr2(Timestamp timestamp){
        return sdf.format(new Date(timestamp.getTime()));
    }

    /**
     * 获取剩余时间
     *
     *          未来时间 = 下单时间 + 限时时间
     *          剩余时间 = 未来时间 - 当前时间
     *
     * @param timeLimit 限时时间
     * @param startTime 下单时间/发货时间
     * @return 剩余时间 （XX天XX分XX秒）
     */
    public static String getStrTime(long timeLimit,long startTime){
        //  未来时间 = 当前时间 + 限时时间
        long time = startTime + timeLimit;
        return getStrDateTime(time,System.currentTimeMillis());
    }

    /**
     * 0天0小时15分钟30秒
     * @param after  未来时间
     * @param before 过去时间
     * @return 0天0小时15分钟30秒
     */
    private  static String getStrDateTime(long after,long before) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = after - before;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
         long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }

    /**
     *  判断当前时间是否在指定时间范围内
     *
     * @param startDate   开始时间  08:00
     * @param endDate     结束时间  23:30
     * @return   true在时间段内，false不在时间段内
     * @throws Exception
     */
    public static boolean hourMinuteBetween(String startDate, String endDate) throws Exception {
        Date now = format.parse(format.format(new Date()));
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);
        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();
        return nowTime >= startTime && nowTime <= endTime;
    }
//
//    public static void main(String[] args) throws Exception {
//
//        System.out.println(TimeUtils.hourMinuteBetween("08:00","23:30"));
//    }





}
