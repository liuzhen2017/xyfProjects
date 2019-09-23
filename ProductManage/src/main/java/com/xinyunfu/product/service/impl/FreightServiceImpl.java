package com.xinyunfu.product.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.product.dto.FindFreightDTO;
import com.xinyunfu.product.dto.ModeDTO;
import com.xinyunfu.product.mapper.FreightMapper;
import com.xinyunfu.product.model.Freight;
import com.xinyunfu.product.service.IFreightService;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.service.ISkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
@Slf4j
public class FreightServiceImpl extends ServiceImpl<FreightMapper, Freight> implements IFreightService{

	@Autowired
	private IProductService productService;
	@Autowired
	private ISkuService skuService;
	@Autowired
	private FreightMapper freightMapper;
	@Override
	public BigDecimal queryPostage(Long proId) {
		Long freightId = productService.findProductById(proId).getFreightId();
		log.info("freightId="+freightId);
		Freight freight = freightMapper.selectFreightById(freightId);
		BigDecimal postage=freight.getDefaultPostage();
		if (postage==null)
		    throw new RuntimeException("查询默认邮费失败");
		else
		    return postage;
	}



    public BigDecimal queryPostageByIdAndCount(String province,String city,long proId,long skuId,int count) {
        BigDecimal postage=new BigDecimal("0");
        String proCity="";
        if (StringUtils.isNotEmpty(province)&&StringUtils.isNotEmpty(city)){
            proCity=province+"_"+city;
        }
        Long freightId = productService.findProductById(proId).getFreightId();
        Freight freight = freightMapper.selectFreightById(freightId);
        if (freight.getType()==0){ //包邮

            return postage;

        }else if (freight.getType()==1){  //不包邮
            postage=this.post(freight,proCity,province,count);
        }else if (freight.getType()==2){   //按地区包邮
            String condition = freight.getConditions();
            JSONArray areas=JSONArray.parseArray(condition);
            if (areas.contains(proCity)||areas.contains(province)){
                return postage;
            }else
               postage=this.post(freight,proCity,province,count);
        }else if (freight.getType()==3){   //按金额包邮
            BigDecimal price = skuService.getById(skuId).getPrice();
			//订单总金额小于包邮金额
			if ((new BigDecimal(freight.getConditions())).compareTo(price.multiply(new BigDecimal(Integer.valueOf(count))))==1) {
                postage = this.post(freight, proCity, province, count);
            }else{
			    return postage;
            }
        }else if  (freight.getType()==4){   //按件数包邮
            if (Integer.valueOf(freight.getConditions())>count) {
                postage=this.post(freight, proCity, province, count);
            }
            return postage;
        }
        return postage;
	}

public BigDecimal post( Freight freight,String proCity,String province, Integer value) {
    BigDecimal postage=new BigDecimal("0");
    JSONArray list = JSONArray.parseArray(freight.getModes());
    String defaultPost = "";
    for (Object object : list) {
        ModeDTO modeDTO = JSONObject.parseObject(object.toString(), ModeDTO.class);
        if (StringUtils.isNotEmpty(modeDTO.getArea()) && StringUtils.isNotEmpty(province) ) {
            if (proCity.equals(modeDTO.getArea()) || province.equals(modeDTO.getArea())) {
                String post = modeDTO.getPost();
                String[] split = post.split("_");
                int first = Integer.valueOf(split[0]);
                int firstPostage = Integer.valueOf(split[1]);
                int next = Integer.valueOf(split[2]);
                int nextPostage = Integer.valueOf(split[3]);
                postage = BigDecimal.valueOf(firstPostage + Math.ceil((value - first) / next) * nextPostage);
            }
        } else
            defaultPost = modeDTO.getPost();
    }
    if (StringUtils.isNotEmpty(defaultPost) && (postage.compareTo(BigDecimal.ZERO) == 0 || StringUtils.isEmpty(province))) {
        String[] split = defaultPost.split("_");
        int first = Integer.valueOf(split[0]);
        int firstPostage = Integer.valueOf(split[1]);
        int next = Integer.valueOf(split[2]);
        int nextPostage = Integer.valueOf(split[3]);
        postage = BigDecimal.valueOf(firstPostage + Math.ceil((value - first) / next) * nextPostage);
    }
    return postage;

}
















}
