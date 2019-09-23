package com.xinyunfu.product.utils;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.enums.Res;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
@Data
@Accessors(chain=true)
public class ResInfo extends ResponseInfo {

    public static final String CODE_EXCEPTION="1111";
    public ResInfo(){}
    public ResInfo(Res resultCode) {
        this.setCode(resultCode.code()+"");
        super.setMessage(resultCode.msg());
        Map<String,Object> map = new HashMap<>();
        map.put("records",new int[]{});
        super.setData(map);
    }
    public ResInfo erroReturn(String message){
        ResInfo resInfo=new ResInfo();
        resInfo.setCode("9999");
        resInfo.setMessage(message);
//        Map<String,Object> map = new HashMap<>();
//        map.put("records",new int[]{});
        int[] data=new int[]{};
        resInfo.setData(data);
        return resInfo;
    }
    
    public ResInfo disposeReturn(String message) {
    	 ResInfo resInfo=new ResInfo();
    	 resInfo.setCode(this.CODE_EXCEPTION);
    	 resInfo.setMessage(message);
         int[] data=new int[]{};
         resInfo.setData(data);
         return resInfo;
    }
}
