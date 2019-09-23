package com.ruoyi.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.dto.CustUserDto;
import com.ruoyi.dto.PageQueryInnerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author TYM
 * @date 2019/8/2
 * @Description :用户微服务
 */
@FeignClient("CustomerManage")
public interface CustomerManageFeign {

//    @PostMapping("/customer/findUserByPage")
//    public IPage<>

    /**
     * 用户升级0
     * @param userNo
     * @param level
     */
    @GetMapping("/customer/inner/user/level")
    public com.ruoyi.utils.ResponseInfo<Object> changeLevel(@RequestParam("userNo") Long userNo, @RequestParam("level") Integer level);

    /*
    用户信息
     */
    @PostMapping("/customer/inner/user/page")
    public com.ruoyi.utils.ResponseInfo<PageQueryInnerDTO<CustUserDto>> listcs(@RequestBody PageQueryInnerDTO dto);


}
