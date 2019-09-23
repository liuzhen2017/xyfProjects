package com.xinyunfu.report.service;


import com.xinyunfu.report.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: liaoz
 * Date: 2019/4/22
 * Time: 11:04
 */
@Service
public class EchartsFactoryServiceImpl implements IEchartsFactoryService {
    @Override
    public Map<String, Object> createdLine(List<Map<String, Object>>[] sorce, String[] xTitle) throws Exception {
        return creadBase(sorce,xTitle, BaseEnum.shapeType.LINE);
    }

    @Override
    public Map<String, Object> createdPie(List<Map<String, Object>> listSource, String[] xTitle) throws Exception {
        Map<String, Object> map =null;

        if(listSource ==null || listSource.isEmpty()) {
            throw new RuntimeException("数据不能为空!");
        }

        int size =listSource.size() +1;
        String name;
        Map<String,String[]> nameStr =new HashMap<>();

        String[] tempStr =null;
        List<String[]> sourceList =new LinkedList<>();
        StringBuffer sb =new StringBuffer("data;");
        List<Map<String, Object>> seriesList =new LinkedList<Map<String,Object>>();
        Map<String, Object> series =null;
        Map<String, Object> encodeMap;
        //封装第一行,如果有横坐标的话
        if(xTitle !=null && xTitle.length >0) {
            for(String s: xTitle) {
                sb.append(s+";");
                //封装横坐标
                series =new HashMap<>();
                series.put("type", BaseEnum.shapeType.PIE.name());
                encodeMap=new LinkedHashMap<>();
                encodeMap.put("itemName", "data");
                encodeMap.put("value",s);
                series.put("encode", encodeMap);
                seriesList.add(series);
            }
            //['product', '2012', '2013', '2014', '2015', '2016', '2017'],
            sourceList.add(sb.toString().split(";"));

        }
        //循环取值
        for(int i=0;i < listSource.size(); i++) {
            map =listSource.get(i);
            //获取行坐标
            name =(String) map.getOrDefault("name","");
            // 查询是否有这个集合
            tempStr = nameStr.get(name);
            if (tempStr == null) {
                tempStr = new String[size];
                tempStr[getIndex(tempStr)] =name;
                tempStr[getIndex(tempStr)] =  map.getOrDefault("num","").toString();;
            }else {
                tempStr[getIndex(tempStr)] = map.getOrDefault("num","").toString();;
            }
            sourceList.add(tempStr);
        }
        //横坐标定义
        Map<String, Object> xAxis =new HashMap<>();
        List<Map<String, Object>> listxAxis =new LinkedList<>();
        //横坐标属性

        //取到标题
        Map<String, Object> tempMap =new HashMap<>();
        if(xTitle !=null) {
            for (int i=0;i< xTitle.length; i++) {
                xAxis =new LinkedHashMap<>();
                tempMap.put("itemName",xTitle[0]);
                tempMap.put("value",xTitle[i]);
                xAxis.put("encode", tempMap);
                listxAxis.add(xAxis);
            }
        }
        listSource =null;
        // 返回值
        Map<String, Object> lastData = new HashMap<>();
        // 数据
        lastData.put("source", sourceList);
        // 横坐标
        lastData.put("xAxis", xAxis);
        // 纵坐标值
        lastData.put("series", seriesList);
        return lastData;
    }

    public int getIndex(String[] str) {
        for(int i=0;i<str.length;i++) {
            if(StringUtils.isEmpty(str[i])) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public Map<String, Object> creadBase(List<Map<String, Object>>[] listSource, String[] xTitle, BaseEnum.shapeType type) throws Exception {
        if(listSource ==null) {
            throw new RuntimeException("数据不能为空!");
        }
        if(  xTitle ==null) {
            throw new RuntimeException("标题不能为空!");
        }
//        //横坐标,横坐标从 listSource获取 name,
//        Set<String> xAxis =new TreeSet<>(
//                new Comparator<String>() {
//                    @Override
//                    public int compare(String o1, String o2) {
//                        return o1.compareTo(o2);
//                    }
//                });
        //横坐标,横坐标从 listSource获取 name,
        Set<String> xAxis =new TreeSet<>();
        //纵坐标,从 listSource 里面取,name 和 num
        //循环N个取
        List<Map<String, Object>> tempList =null;
        //取出集合列表横坐标刻度,比如: 2016,2017...
        for(int i=0;i < listSource.length; i++) {
            //获取单个
            tempList =listSource[i];
            if(tempList ==null) continue;
            //得到xAxis
            for(int j=0;j<tempList.size(); j++) {
                //取到key
                xAxis.add(tempList.get(j).get("name")==null? "" : tempList.get(j).get("name").toString());
            }
        }

        //将data值取出,封装为
        List<Map<String, Object>> data =new LinkedList<>();
        Map<String, Object> dataMap;
        String[] str =null;
        List<Map<String, Object>> litTemp;
        String value =null;
        String key ="name";
        //将横坐标转为 集合
        List<Object> listAxis =Arrays.asList(xAxis.toArray());

        for (int i=0; i<listSource.length; i++) {
            str =new String[xAxis.size()]; //一个集合,创建一个一个对应的 数组
            //取到当前集合
            litTemp = listSource[i];

            //循环一个集合,如果有值,则填充进入 数组里面, 数据需要返还给页面
            for(Map<String, Object> m : litTemp) {
                value =(String) m.get(key);
                //判断 占在那个位置
                str[listAxis.indexOf(value)] = m.get("num").toString();
            }
            dataMap=new LinkedHashMap<>();
            dataMap.put("name",xTitle[i]);
            dataMap.put("data",str);
            dataMap.put("type",type.name());
            data.add(dataMap);
        }

        //返回值
        Map<String, Object> lastData =new HashMap<>();
        //标题
        lastData.put("legend", xTitle);
        //横坐标
        lastData.put("xAxis", xAxis);
        //纵坐标值
        lastData.put("series", data);

        listSource = null;
        listAxis = null;

        return lastData;
    }

    @Override
    public Map<String, Object> createdBar(List<Map<String, Object>>[] listSource, String[] xTitle) throws Exception {
        return creadBase(listSource,xTitle, BaseEnum.shapeType.BAR);
    }
}
