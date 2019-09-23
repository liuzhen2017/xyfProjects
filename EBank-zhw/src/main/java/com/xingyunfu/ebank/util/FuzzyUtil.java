package com.xingyunfu.ebank.util;

import java.util.Objects;

public class FuzzyUtil {

    /**
     * 对银行卡/身份证做模糊化处理
     */
    public static String cardFuzzy(String str){
        if(Objects.isNull(str)) return str;

        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 5));
        sb.append("*********");
        sb.append(str.substring(str.length()-4));

        return sb.toString();
    }

    public static String fuzzy(String str){
        if(Objects.isNull(str) || str.length()<3) return str;

        Integer firstIndex = str.length()/3;
        Integer secondIndex = str.length()/3 * 2;

        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, firstIndex));
        sb.append("******");
        sb.append(str.substring(secondIndex));

        return sb.toString();
    }
}
