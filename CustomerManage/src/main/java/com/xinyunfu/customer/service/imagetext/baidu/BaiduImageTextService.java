package com.xinyunfu.customer.service.imagetext.baidu;

import com.xinyunfu.customer.dto.imagetext.BankCardRespDTO;
import com.xinyunfu.customer.dto.imagetext.IdCardRespDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class BaiduImageTextService {

    @Autowired private BaiduAuthService baiduAuthService;
    @Autowired private RestTemplate restTemplate;

    private static String address = "住址";
    private static String cardNo = "公民身份号码";
    private static String birth = "出生";
    private static String name = "姓名";
    private static String sex = "性别";
    private static String nation = "民族";

    private static String bankCardNo = "bank_card_number";
    private static String validDate = "valid_date";
    private static String cardType = "bank_card_type";
    private static String bankName = "bank_name";

    // 身份证识别url
    private static String baidu_url = "/rest/2.0/ocr/v1/idcard?access_token=%s";
    // 银行卡识别url
    private static String baidu_bank_url = "/rest/2.0/ocr/v1/bankcard?access_token=%s";

    public IdCardRespDTO idCard(String base64Image) throws CustomerException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("image", base64Image);
        map.add("id_card_side", "front");


        String url = baiduAuthService.getHost() + baidu_url;
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(String.format(url, baiduAuthService.getAuth()), entity, String.class);
        String result = response.getBody();

        String words_result = "words_result";
        if(!result.contains(words_result)) throw new CustomerException(CustomerExceptionEnum.COMMON_IAMGE_CARD_ERROR);

        JSONObject json = new JSONObject(result).getJSONObject(words_result);

        IdCardRespDTO idCardResp = new IdCardRespDTO();
        idCardResp.setAddress(this.getJsonRes(json, address));
        idCardResp.setNumber(this.getJsonRes(json, cardNo));
        idCardResp.setBirthday(this.getJsonRes(json, birth));
        idCardResp.setName(this.getJsonRes(json, name));
        idCardResp.setSex(this.getJsonRes(json, sex));
        idCardResp.setNation(this.getJsonRes(json, nation));

        return idCardResp;
    }

    public BankCardRespDTO bankCard(String base64Image) throws CustomerException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("image", base64Image);

        String url = baiduAuthService.getHost() + baidu_bank_url;
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(String.format(url, baiduAuthService.getAuth()), entity, String.class);
        String result = response.getBody();

        String resultKey = "result";
        if(!result.contains(resultKey)) throw new CustomerException(CustomerExceptionEnum.COMMON_IAMGE_BANK_ERROR);

        JSONObject json = new JSONObject(result).getJSONObject(resultKey);

        BankCardRespDTO bankCardResp = new BankCardRespDTO();
        bankCardResp.setCardNo((String) this.getJsonRes_bank(json, bankCardNo));
        bankCardResp.setValidDate((String) this.getJsonRes_bank(json, validDate));
        bankCardResp.setCardType((Integer) this.getJsonRes_bank(json, cardType));
        bankCardResp.setBankName((String) this.getJsonRes_bank(json, bankName));

        return bankCardResp;
    }

    private String getJsonRes(JSONObject json, String key) throws JSONException {
        String result = null;
        if(!json.isNull(key)){
            result = json.getJSONObject(key).getString("words");
        }
        return result;
    }

    private  Object getJsonRes_bank(JSONObject json, String key) throws JSONException {
        Object result = null;
        if(!json.isNull(key)){
            result = json.get(key);
        }
        return result;
    }
}
