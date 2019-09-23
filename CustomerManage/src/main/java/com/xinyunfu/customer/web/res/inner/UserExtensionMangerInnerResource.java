package com.xinyunfu.customer.web.res.inner;

import com.xinyunfu.customer.domain.user.CustUserExtensionDTO;
import com.xinyunfu.customer.dto.extension.UserExtensionSetSuperUserDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.user.UserExtensionMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer/inner/user/extension/")
public class UserExtensionMangerInnerResource {

    @Autowired private UserExtensionMangerService userExtensionMangerService;

    /**
     * 修改套餐购买数量
     */
    @GetMapping("/meal")
    public void meal(@RequestParam Long userNo, @RequestParam Integer mealNu){
        log.info("REST request to add meal. userNo: {}, mealNu: {}", userNo, mealNu);
        CustUserExtensionDTO extension = new CustUserExtensionDTO();
        extension.setUserNo(userNo);
        extension.setMealNu(mealNu);
        userExtensionMangerService.update(extension);
        log.info("REST request to add meal. success!");
    }

    /**
     * 根据手机号设置用户的上级用户
     */
    @PostMapping("/super")
    public void setSuperUser(@RequestBody@Validated UserExtensionSetSuperUserDTO superUser) throws CustomerException {
        log.info("REST request to set super user. superUser: {}", superUser);
        userExtensionMangerService.update(superUser);
        log.info("REST request to set super user. success!");
    }
}
