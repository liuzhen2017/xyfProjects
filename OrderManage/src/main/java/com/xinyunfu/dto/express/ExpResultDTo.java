//package com.xinyunfu.dto.express;
//
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author XRZ
// * @date 2019/8/15
// * @Description : 快递100返回的DTO
// */
//@Data
//public class ExpResultDTo implements Serializable {
//
//    /**
//     * 快递公司编码,一律用小写字母
//     */
//    private String com;
//
//    /**
//     * 是否签收标记，请忽略，明细状态请参考state字段
//     */
//    private String ischeck;
//
//    /**
//     * 快递单明细状态标记，暂未实现，请忽略
//     */
//    private String condition;
//
//    /**
//     * 单号
//     */
//    private String nu;
//
//    /**
//     *快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
//     */
//    private String state;
//
//    /**
//     * 消息体，请忽略
//     */
//    private String message;
//
//    /**
//     *通讯状态，请忽略
//     */
//    private String status;
//
//    /**
//     * 最新查询结果，数组，包含多项，全量，倒序（即时间最新的在最前），每项都是对象，对象包含字段请展开
//     */
//    private ExpInfoDto[] data;
//
//}
