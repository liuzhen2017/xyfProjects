package com.xinyunfu.customer.dto.user;

import lombok.Data;

/**
 * 修改用户信息
 */
@Data
public class UserChangeDTO {

    private String nickName;
    private String wechat;
    private Integer sex;
    private String photoUrl;
}
