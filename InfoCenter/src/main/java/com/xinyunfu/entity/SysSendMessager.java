package com.xinyunfu.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysSendMessager {
	private long id;
	private String recvObject;
	private String sendContent;
	private String sendType;
	private Integer sendStatus;
	private String clientVersion;
	private String clientSystem;
	private String clientSource;
	private String deviceId;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String templateNo;
	private String errMsg;
	private String userNo;
}
