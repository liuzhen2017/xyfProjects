package com.xinyunfu.jace.entites;

import lombok.Data;

@Data
public class RequestBean {

	
	
	private String threadId;
	
	
	private String url ;
	
	
	private Object[] obj;
	
	
	public String getRequestString() {
		if(this!=null) {
			StringBuilder sb =	new StringBuilder();
			for(Object o : obj) {
				sb.append(o+",");
			}
			String str = sb.toString().toString();
			return str.substring(0, str.length()-1);
		}
		return null;
	}
	
	
	public String toString() {
		return "Request  url: ["+this.url+" ],request:["+getRequestString()+"]";
	}
}
