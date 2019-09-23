package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.domain.user.CustReferralCodeSpecialDTO;
import com.xinyunfu.customer.repository.user.CustUserMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisLock;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 生成随机邀请码
 */
@Slf4j
@Service
public class ReferralCodeService {

    @Autowired private RedisTemplate redisTemplate;
    @Autowired private RefferralCodeSpecialService refferralCodeSpecialService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisStringService redisStringService;
    @Autowired private CustUserMapper custUserMapper;

    private static String[] code_2 = {"a", "b", "c", "d", "e", "f", "g", "h",
                                        "j", "k", "l", "m", "n", "p", "q", "r",
                                        "s", "t", "u", "v", "w", "x", "y", "z",
                                        "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] code = {
            "x", "e", "8", "q", "u", "2", "l", "m", "6", "j",
            "p", "a", "f", "9", "k", "g", "b", "4", "s", "n",
            "v", "3", "r", "y", "c", "d", "w", "t", "z", "5", "h", "7"
    };

    private String inner_code = "(!@#$)";//特殊符号，用于标识下标走向
    private String defaultIndex = "0_0_0_0_0_0";
    private Integer indexLength = defaultIndex.split("_").length;

    @PostConstruct
    public void init(){
        if(Objects.isNull(refferralCodeSpecialService.findByReferralCode(inner_code))){
            refferralCodeSpecialService.add(inner_code, defaultIndex);
        }
    }

    /**
     * 为新创建用户生成邀请码，长度6，最多生成（26+10-4）的六次方个，不考虑超出的情况
     */
    public String createCodeWithCheck() {
        RedisLock lock = new RedisLock(redisTemplate, RedisKeyRules.userReferral(), 1000, 5000);

        String result = null;
        try {
            if(lock.lock()){
                Integer[] index = this.initIndex();

                this.indexAdd(index);
                while(!this.checkIndex(index)) {
                    this.indexAdd(index);
                }

                result = this.createCode(index);
                while(!checkCode(result)) {
                    this.indexAdd(index);
                    while(!this.checkIndex(index)) {
                        this.indexAdd(index);
                    }
                    result = this.createCode(index);
                }
                this.saveIndex(index);
                log.info("user code: {}", result);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    private Integer[] initIndex(){

        String str;
        if(redisCommonService.exists(RedisKeyRules.referralKey())){
            str = (String) redisStringService.get(RedisKeyRules.referralKey());
        }else{
            CustReferralCodeSpecialDTO code = refferralCodeSpecialService.findByReferralCode(inner_code);
            if(Objects.isNull(code)){
                str = defaultIndex;
                refferralCodeSpecialService.add(inner_code, str);
            }else{
                str = code.getRemark();
            }
        }

        Integer[] index = new Integer[indexLength];
        for(int i=0; i < index.length; i++){
            index[i] = Integer.valueOf(str.split("_")[i]);
        }
        return index;
    }

    private void saveIndex(Integer[] index){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<index.length; i++){
            sb.append(index[i]);
            if(i != index.length-1) sb.append("_");
        }
        redisStringService.add(RedisKeyRules.referralKey(), sb.toString());
        refferralCodeSpecialService.update(inner_code, sb.toString());
    }

    /**
     * 为新创建用户生成邀请码，长度6，最多生成（26+10）的六次方个，不考虑超出的情况
     */
    private String createCode(Integer[] index){

        StringBuilder sb = new StringBuilder();
        for (Integer i : index) {
            sb.append(code[i]);
        }
        return sb.toString();
    }

    /**
     * 重置index, index加一
     */
    private void indexAdd(Integer[] index) {
        index[index.length-1] += 1;
        for(int i=index.length-1; i >= 0; i--) {
            if(index[i] == code.length) {
                index[i] = 0;
                if(i != 0) index[i-1] += 1;
            }
        }
    }

    /**
     * 查询数据库判断当前验证码是否可用
     */
    private Boolean checkCode(String code){
        return !refferralCodeSpecialService.findAllReferralCode().contains(code)
                && Objects.isNull(custUserMapper.findByUserCode(code));
    }

    /**
     * 判断index格式是否符合要求
     */
    private Boolean checkIndex(Integer[] index) {
        //有三个连续相同的则不符合要求
        for(int i=0; i<index.length-2; i++){
            if(index[i] == index[i+1] && index[i] == index[i+2]) return false;
        }
        return true;
    }
}
