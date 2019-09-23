package com.xinyunfu.sao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value="AppUser")
public interface ProducerSao {

    @GetMapping("/api/{id}")
	public String callProducerSao(@PathVariable("id")int id) ;
	
}
