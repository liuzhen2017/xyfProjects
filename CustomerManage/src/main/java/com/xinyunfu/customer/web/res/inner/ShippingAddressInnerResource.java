package com.xinyunfu.customer.web.res.inner;

import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressInfoDTO;
import com.xinyunfu.customer.service.address.AreaManagerService;
import com.xinyunfu.customer.service.address.ShippingAddressManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer/inner/address")
public class ShippingAddressInnerResource {

    @Autowired private ShippingAddressManagerService shippingAddressManagerService;
    @Autowired private AreaManagerService areaManagerService;

    /**
     * 根据收货地址id查询出收货地址详细信息
     */
    @GetMapping
    public ShippingAddressInfoDTO getAddress(@RequestParam Long id){
        log.info("REST request to get address. id: {}", id);
        ShippingAddressInfoDTO result = shippingAddressManagerService.getAddress(id);
        log.info("REST request to get address. success!");
        return result;
    }

    /**
     * 根据行政级别，查询出该级别下所有的行政区域
     */
    @GetMapping("/level")
    public List<CustAreaDTO> getLevelArea(@RequestParam Integer areaLevel){
        log.info("REST request to get level area. areaLevel: {}", areaLevel);
        List<CustAreaDTO> result = areaManagerService.getLevelArea(areaLevel);
        log.info("REST request to get level area. success! result number: {}", result.size());
        return result;
    }

    /**
     * 根据行政区域id，查询出该区域下所有的行政区域
     */
    @GetMapping("/lower")
    public List<CustAreaDTO> getLowerArea(@RequestParam Long parentId){
        log.info("REST request to get lower area. parentId: {}", parentId);

        List<CustAreaDTO> result = areaManagerService.getLowerArea(parentId);
        log.info("REST request to get lower area. success! result number: {}", result.size());
        return result;
    }
}
