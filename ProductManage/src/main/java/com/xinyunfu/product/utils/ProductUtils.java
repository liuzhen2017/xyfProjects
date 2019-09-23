package com.xinyunfu.product.utils;

import com.xinyunfu.product.dto.ProDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductUtils {

    /**
     * @param  int i 0 升序, 1降序
     * @param list
     * @return
     */
    public static List<ProDto> descByPrice(List<ProDto> list, int i){
        Collections.sort(list, new Comparator<ProDto>() {
            @Override
            public int compare(ProDto p1, ProDto p2){
                if(i==1) {
                    return p2.getPrice().compareTo(p1.getPrice());
                }else{
                    return p1.getPrice().compareTo(p2.getPrice());
                }//降序
            }
        });
        return list;
    }

    public static List<ProDto> descByStock(List<ProDto> list, int i){
        Collections.sort(list, new Comparator<ProDto>() {
            @Override
            public int compare(ProDto p1, ProDto p2){
                if(i==1) {
                    return p2.getAllSellStock().compareTo(p1.getAllSellStock());
                }else{
                    return p1.getAllSellStock().compareTo(p2.getAllSellStock());
                }//降序
            }
        });
        return list;
    }



}
