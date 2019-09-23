//package com.ruoyi.feign;
//
//import java.util.HashMap;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//
//public class AipOcrUtil {
//	//设置APPID/AK/SK  需自行去百度申请哦
//    private static final String APP_ID = "xxxx";
//    private static final String API_KEY = "xxxxxx";
//    private static final String SECRET_KEY = "xxxxxxx";
//
//    public static String UploadFile(String path) throws JSONException {
//        // 初始化一个AipOcr
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 调用接口
//        String resultStr = "";
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        JSONObject jsonObject = JSONObject.parseObject(res.toString());
//        JSONArray resultList = jsonObject.getJSONArray("words_result");
//        for(Object result:resultList){
//            JSONObject temp = (JSONObject) result;
//            resultStr += temp.getString("words")+"<br/>";
//        }
//        return resultStr;
//    }
//}
