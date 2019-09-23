package com.ruoyi.VO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CusUserUpgradeVo {
      @NotNull(message = "标识不能为空")
      private Long userNo;
      @NotBlank(message = "验证码不能为空")
      private String  validCode;
}
