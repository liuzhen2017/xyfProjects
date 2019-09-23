package com.ruoyi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushMsgDto {
      private String custName;
      /** 用户ID */
      private Long userId;
      /** 部门ID */
      private Long deptId;
      /** 登录账号 */
      private String loginName;
      /** 用户昵称 */
      private String userName;
      /** 用户类型（00系统用户） */
      private String userType;
      /** 用户邮箱 */
      private String email;
      /** 手机号码 */
      private String phonenumber;
      /** 用户性别（0男 1女 2未知） */
      private String sex;
      /** 头像路径 */
      private String avatar;
      /** 密码 */
      private String password;
      /** 盐加密 */
      private String salt;
      /** 帐号状态（0正常 1停用） */
      private String status;
      /** 删除标志（0代表存在 2代表删除） */
      private String delFlag;
      /** 最后登陆IP */
      private String loginIp;
      /** 最后登陆时间 */
      private Date loginDate;
      /** 备注 */
      private String remarks;
      /** 企业编号 */
      private String custNo;
      /** 企业类型 */
      private String custType;
}
