package com.xinyunfu.jace.entites;

import lombok.Data;

@Data
public class SystemLogStrategy {
	
	    
	
	    private String threadId;
	
	    private String location;
	
	    private String description;
	
	    private String className;
	
	    private String methodName;
	
	    private String arguments;
	
	    private String result;
	
	    private Long elapsedTime;
	
	
	    public Object[] args() {
	        return new Object[]{this.threadId, this.elapsedTime, this.className, this.methodName, this.arguments, this.result};
	    }
	    
	    
	    public String toString() {
	    	return "Response  cost: ["+this.elapsedTime+" ms], ClassName: ["+this.className+"], method name: {"+this.methodName+"}, return:"+this.result ;
	    }
	
}

