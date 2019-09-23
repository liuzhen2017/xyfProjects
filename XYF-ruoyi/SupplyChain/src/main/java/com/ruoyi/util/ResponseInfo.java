package com.ruoyi.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author TYM
 * @date 2019/8/3
 * @Description :
 */
@Data
public class ResponseInfo<T> implements Serializable {
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATA = "data";
    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_ERROR = "9999";
    private static final ImmutableMap<String, String> codeMap = ImmutableMap.of("0000", "成功", "9999", "系统调用异常");
    private String code;
    private String message;
    private T data;

    public static <T> ResponseInfo<T> success(T data) {

        return new ResponseInfo("0000", (String)codeMap.get("0000"), data);
    }

    public static <T> ResponseInfo<T> error(T data) {
        return new ResponseInfo("9999", (String)codeMap.get("9999"), data);
    }

    public static ResponseInfo<String> errorReturn(String message) {
        return new ResponseInfo("9999", message, "{}");
    }

    @JsonIgnore
    public boolean isSuccess() {
        return "0000".equals(this.code);
    }

    public ResponseInfo(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseInfo() {
    }

}
