package com.ruoyi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  DescartesUtil{
	
	/**
	 * 
	 * 
	 * @param ds
	 * @param result
	 * @param layer
	 * @param curMap
	 */
	public static void toDescartes(List<List<Map<String, String>>> ds, List<Map<String, String>> result, int layer,Map<String, String> curMap) {
		if (layer < ds.size() - 1) {
            if (ds.get(layer).size() == 0) {
            	toDescartes(ds, result, layer + 1,curMap);
            } else {
                for (int i = 0; i < ds.get(layer).size(); i++) {
                	//声明个装完整数据的map且为前面的map
                	 Map<String,String> mapKing = new HashMap<String, String>(curMap);
                	 //声明循环下的map数据容器
                	 Map<String,String> mapCurI = new HashMap<String, String>(ds.get(layer).get(i));
                	//循环map数据 压进新map 通过key    Set<T> key = map.keySet()
                     for(String key:mapCurI.keySet()){
                         //System.out.println("key="+key+" value="+mapCurI.get(key));
                         mapKing.put(key, mapCurI.get(key));
                     }
                    toDescartes(ds, result, layer + 1, mapKing);
                }
            }
        } else if (layer == ds.size() - 1) {
            if (ds.get(layer).size() == 0) {
                result.add(curMap);
            } else {
                for (int i = 0; i < ds.get(layer).size(); i++) {
                	 Map<String,String> mapKing = new HashMap<String, String>(curMap);
                	 
                	//声明循环下的map数据容器
                	 Map<String,String> mapCurI = new HashMap<String, String>(ds.get(layer).get(i));
                	 
                	//循环map数据 压进新map 通过key    Set<T> key = map.keySet()
                     for(String key:mapCurI.keySet()){
                         mapKing.put(key, mapCurI.get(key));
                     }
                    result.add(mapKing);
                }
            }
        }
	}
	
	
	
	
	/**
	 * 
	 * @param listA
	 * @param listB
	 * @return
	 */
	public static List<String> createSkuList(List<String> listA,List<String> listB){
		List<String> newList = new ArrayList<String>();
		for(int i=0;i<listA.size();i++) {
			for(int j=0;j<listB.size();j++) {
				newList.add(listA.get(i).concat("_").concat(listB.get(j)));
			}
		}
		return newList;
	}

	
	public static void main(String[] args) {
		List<String> listA =new ArrayList<String>();
		List<String> listB =new ArrayList<String>();
		List<String> listC =new ArrayList<String>();
		listC.add("红色");
		listC.add("蓝色");
		listC.add("绿色");
		listA.add("red");
		listA.add("blue");
		listA.add("white");
		listB.add("5.0");
		listB.add("4.0");
		System.out.println(createSkuList(createSkuList(listA, listB),listC));
	}
}
