package com.ruoyi.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/6
 * @Description :
 */
public class ResUitls {

    public static <T> Res<T> getRes(Object obj,Class<T> tClass){
        Res<T> res = new Res<>();
        LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) obj;
        res.setPages((Integer) map.get("pages"));
        res.setCurrent((Integer) map.get("current"));
        res.setSize((Integer) map.get("size"));
        res.setTotal((Integer) map.get("total"));
        res.setRecords((List<T>) map.get("records"));
        return res;
    }

}
