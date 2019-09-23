package com.gateway.config;

import org.springframework.stereotype.Component;

import com.gateway.service.FallBackItem;

import feign.hystrix.FallbackFactory;

@Component
public class HytriFallBackFactory implements FallbackFactory<FallBackItem>{

	
	@Override
	public FallBackItem create(Throwable cause) {

		return new FallBackItem();
	}

	

}
