package com.xinyunfu.customer.web.res.inner;

import com.xinyunfu.customer.dto.PageQueryInnerDTO;
import com.xinyunfu.customer.dto.user.CustUserWithExtensionDTO;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.customer.dto.user.UserPageQueryDTO;
import com.xinyunfu.customer.dto.user.UserPhoneListDTO;
import com.xinyunfu.customer.service.user.UserManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer/inner/user")
public class UserManagerInnerResource {

    @Autowired private UserManagerService userManagerService;

    /**
     * 根据用户编号获得上级10个推荐人，升序
     */
    @GetMapping("/referral")
    public List<String> getReferral_10_Users(@RequestParam Long userNo){
        log.info("REST request to getReferral 10 Users. uerNo: {}", userNo);
        List<String> referralList = new ArrayList<>(10);

        Long referrerNo = userManagerService.findExByUserNo(userNo).getReferrerNo();
        for(int i=0; i< 10; i++){
            if(Objects.nonNull(referrerNo)){
                referralList.add(i, referrerNo.toString());
                referrerNo = userManagerService.findExByUserNo(referrerNo).getReferrerNo();
            }else{
                referralList.add(i, "-1");
            }
        }

        log.info("REST request to get referral 10 users. success! result: {}", referralList);
        return referralList;
    }

    /**
     * 获取完整用户信息
     */
    @GetMapping("/info")
    public UserInfoDTO userInfo(@RequestParam Long userNo){
        log.info("REST request to get user info. userNo: {}", userNo);
        CustUserWithExtensionDTO user = userManagerService.findExByUserNo(userNo);
        UserInfoDTO result;
        if(Objects.nonNull(user)) {
            result = new UserInfoDTO(user, false);
        } else {
            result = new UserInfoDTO();
        }
        log.info("REST request to get user info. success!");
        return result;
    }

    /**
     * 判断用户是否有两个子推荐人
     */
    @GetMapping("/relation")
    public Boolean userRelation(@RequestParam Long userNo){
        log.info("REST request to get user relation. userNo: {}", userNo);
        Boolean result = userManagerService.relation(userNo);
        log.info("REST request to get user relation. success! result: {}", result);
        return result;
    }

    @GetMapping("/level")
    public void changeLevel(@RequestParam Long userNo, @RequestParam Integer level){
        log.info("REST request to change user level. userNo: {}, level: {}", userNo, level);
        userManagerService.changeUserLevel(userNo, level);
        log.info("REST request to change user level. success!");
    }

    /**
     * 分页查询用户
     */
    @PostMapping("/page")
    public PageQueryInnerDTO<UserInfoDTO> pageQuery(@RequestBody@Validated UserPageQueryDTO pageQuery){
        log.info("REST request to page query user info. pageQuery: {}", pageQuery);
        if(Objects.isNull(pageQuery.getStartTime())) pageQuery.setStartTime(0L);
        if(Objects.isNull(pageQuery.getEndTime())) pageQuery.setEndTime(Instant.now().getEpochSecond());
        PageQueryInnerDTO<CustUserWithExtensionDTO> pageCustUser = userManagerService.pageQuery(pageQuery);
        PageQueryInnerDTO<UserInfoDTO> pageUserInfo = new PageQueryInnerDTO<>(pageCustUser);
        pageUserInfo.setList(pageCustUser.getList().stream().map(var -> new UserInfoDTO(var, false))
                .collect(Collectors.toList()));
        log.info("REST request to page query user info. success! totalCount: {}", pageUserInfo.getTotalCount());
        return pageUserInfo;
    }

    @PostMapping("/phone")
    public List<UserInfoDTO> findByPhoneList(@RequestBody UserPhoneListDTO phone){
        log.info("REST request to find user info by phone. phone: {}", phone);
        List<UserInfoDTO> userInfoList = userManagerService.findByPhoneList(phone).stream()
                .map(var -> new UserInfoDTO(var, false)).collect(Collectors.toList());
        log.info("REST request to find user info by phone. success!");
        return userInfoList;
    }
}
