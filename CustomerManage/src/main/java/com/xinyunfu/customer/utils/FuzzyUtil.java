package com.xinyunfu.customer.utils;

import java.util.Objects;

public class FuzzyUtil {

    /**
     * 身份证做模糊化处理
     */
    public static String cardFuzzy(String str){
        if(Objects.isNull(str)) return str;

        StringBuilder sb = new StringBuilder();
        sb.append(str, 0, 4);
        sb.append("*************");
//        sb.append(str.substring(str.length()-4));

        return sb.toString();
    }
    /**
     * 银行卡做模糊化处理
     */
    public static String bankCardFuzzy(String str){
        if(Objects.isNull(str)) return str;

        StringBuilder sb = new StringBuilder();
        sb.append(str, 0, 4);
        sb.append("*************");
        sb.append(str.substring(str.length()-4));

        return sb.toString();
    }

    /**
     * 对姓名做模糊化处理
     */
    public static String nameFuzzy(String str){
        if(Objects.isNull(str)) return str;
        StringBuilder sb = new StringBuilder();
        sb.append(str, 0, 1);
        for(int i=0; i<str.length()-1; i++){
            sb.append("*");
        }

        return sb.toString();
    }

    public static String fuzzy(String str){
        if(Objects.isNull(str) || str.length()<3) return str;

        Integer firstIndex = str.length()/3;
        Integer secondIndex = str.length()/3 * 2;

        StringBuilder sb = new StringBuilder();
        sb.append(str, 0, firstIndex);
        sb.append("******");
        sb.append(str.substring(secondIndex));

        return sb.toString();
    }
}
