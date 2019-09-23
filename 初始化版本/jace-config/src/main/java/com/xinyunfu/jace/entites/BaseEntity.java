package com.xinyunfu.jace.entites;

import java.sql.Date;

import lombok.Data;

@Data
public class BaseEntity {

	
	
	private String updatedBy;
	
	private String createdBy;
	
	private Date createdDate;
	
	private Date updatedDate;
}
