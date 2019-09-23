package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ChannelPageDTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.utils.ResInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

	@Autowired
	private IProductService productService;
	@PostMapping("/findRecommendPro")
	public ResponseInfo<IPage<ProDto>> findRecommendPro(@RequestBody ChannelPageDTO channelPageDTO){
	    if (channelPageDTO==null){
	        return new ResInfo(Res.ERROR_PARAM);
        }else{
            IPage<ProDto> pages=productService.getProDtoPageByChannelId(channelPageDTO);
	        if (pages==null)
	            return new ResInfo(Res.NO_DATA);
	        else
                return ResponseInfo.success(pages);
        }
	}














}
