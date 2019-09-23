package com.ruoyi.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author TYM
 * @date 2019/8/2
 * @Description :用户微服务
 */
@FeignClient("CustomerManage")
public interface CustomerManageFeign {

//    @PostMapping("/customer/findUserByPage")
//    public IPage<>



}
