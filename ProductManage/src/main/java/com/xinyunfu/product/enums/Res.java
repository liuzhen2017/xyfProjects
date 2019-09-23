package com.xinyunfu.product.enums;

public enum Res {
	 /**
     * 暂无数据!
     */
    NO_DATA("7000","暂无数据！"),

    /**
     * 参数有误!
     */
    ERROR_PARAM("7001","参数有误！"),

    /**
     * 权限不足!
     */
    ERROR_NOT_PERMISSION("7002","权限不足！"),

    /**
     * 下单失败！
     */
    ERROR_CARTATE_ORDER("7003","下单失败！"),


    /**
     * 操作失败！
     */
    ERROR_FAIL("7006","操作失败！");


    private Res(String code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }


    private String code;
    private String msg;
}
