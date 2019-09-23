package com.xinyunfu.constant;

import java.util.Random;

/**
 * @author XRZ
 * @date 2019/7/8 0008
 * @Description : 通用常量类
 */
public interface Common {

    String UID = "currentUserId";

    /**
     * 万能券答题的键前缀
     */
    String KEY_SPECIAL = "Answer_";

    /**
     * redis 普通答题的键前缀
     */
    String KEY_BASIC = "AnswerRecord_";

    /**
     * 七天
     * 单位：秒
     */
    long EXC_REDIS = 3600 * 24 * 7;

    /**
     * 随机数对象
     */
    Random random = new Random();
    /**
     * 每页展示条数
     */
    Integer PAGE_SIEZ = 20;

    /**
     * 启用 表数据
     */
     int ENABLE = 1;

    /**
     * 禁用 表数据
     */
     int DISABLE = 0;

    /**
     * 万能券的初始化数量
     */
    int PACKAGE_COUNT = 6;

    /**
     * 生活常识
     */
    int LIFE = 0;

    /**
     * 法律
     */
    int LAW = 1;


 //===========================答题状态

    /**
     * 正确
     */
    Integer CORRECT = 1;

    /**
     * 为答
     */
    Integer UNDEFINED = 0;


}
