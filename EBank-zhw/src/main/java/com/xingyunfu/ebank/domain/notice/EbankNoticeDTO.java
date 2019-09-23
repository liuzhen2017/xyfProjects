package com.xingyunfu.ebank.domain.notice;

import lombok.Data;

@Data
public class EbankNoticeDTO {
    private Long id;
    private String userName;
    private String phone;
    private String email;
    private Boolean phoneEnable;
    private Boolean emailEnable;
    private Boolean enable;
    private String remark;
}
