package com.xingyunfu.ebank.util;

import java.util.UUID;

public class CommonUtil {

    /**
     * ebank order no
     */
    public static String ebankOrder(){ return UUID.randomUUID().toString().replaceAll("-", ""); }
}
