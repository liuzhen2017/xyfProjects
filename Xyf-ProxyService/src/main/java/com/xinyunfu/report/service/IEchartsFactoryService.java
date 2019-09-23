package com.xinyunfu.report.service;



import com.fasterxml.jackson.databind.ser.Serializers;
import com.xinyunfu.report.enums.BaseEnum;

import java.util.List;
import java.util.Map;

/**
 * User: liaoz
 * Date: 2019/4/22
 * Time: 11:02
 * Echar 生成图形工厂
 * <ul>
 * <ol>如果调用,请参照 See 案列</ol>
 * <ol>如果想了解封装,请看 link</ol>
 * </ul>
 */
public interface IEchartsFactoryService {
    /**
     * 封装折线图
     * @param sorce list集合,需要两个属性, name, num
     * @param xTitle 横坐标标题,不能为空
     * {@link=http://www.echartsjs.com/gallery/editor.html?c=doc-example/line-stack-tiled}
     * @return
     */
    public Map<String, Object> createdLine(List<Map<String, Object>> sorce[] , String[] xTitle)throws Exception;
    /**
     * 封装饼图
     * @param listSource list集合,需要两个属性, name, num
     * @param xTitle 横坐标标题,可以不传 ['product', '2012', '2013', '2014', '2015', '2016', '2017']
     * {@link=http://www.echartsjs.com/gallery/editor.html?c=dataset-default}
     * @return
     */
    public Map<String, Object> createdPie(List<Map<String, Object>> listSource,String xTitle[])throws Exception;
    /**
     * 封装基础图
     * @param listSource list集合,需要两个属性, name, num
     * @param xTitle 横坐标标题,可以不传 ['product', '2012', '2013', '2014', '2015', '2016', '2017']
     * {@link=http://www.echartsjs.com/gallery/editor.html?c=dataset-default}
     * @return
     */
    public Map<String, Object> creadBase(List<Map<String, Object>> []listSource, String[] xTitle, BaseEnum.shapeType type)throws Exception;

    /**
     * 封装柱状图
     * @param listSource list集合,需要两个属性, name, num
     * @param xTitle 横坐标标题,可以不传 ['product', '2012', '2013', '2014', '2015', '2016', '2017']
     * {@link=http://www.echartsjs.com/examples/editor.html?c=multiple-y-axis}
     * @return
     */
    public Map<String, Object> createdBar(List<Map<String, Object>> listSource[],String[] xTitle)throws Exception;
}
