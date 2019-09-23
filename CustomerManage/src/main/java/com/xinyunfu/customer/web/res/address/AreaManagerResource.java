package com.xinyunfu.customer.web.res.address;

import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.dto.SingleIdDTO;
import com.xinyunfu.customer.service.address.AreaManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行政区域管理
 */
@Slf4j
@RestController
@RequestMapping("/customer/area")
public class AreaManagerResource {

    @Autowired private AreaManagerService areaManagerService;
    /**
     * 根据行政级别，查询出该级别下所有的行政区域
     */
    @PostMapping("/level")
    public List<CustAreaDTO> getLevelArea(@RequestBody SingleIdDTO singleId){
        Integer areaLevel = singleId.getId().intValue();
        log.info("REST request to get level area. areaLevel: {}", areaLevel);
        List<CustAreaDTO> result = areaManagerService.getLevelArea(areaLevel);
        log.info("REST request to get level area. area number: {}", result.size());
        return result;
    }

    /**
     * 根据行政区域id，查询出该区域下所有的行政区域
     */
    @PostMapping("/lower")
    public List<CustAreaDTO> getLowerArea(@RequestBody SingleIdDTO singleId){

        Long parentId = singleId.getId();
        log.info("REST request to get lower area. parentId: {}", parentId);
        List<CustAreaDTO> result = areaManagerService.getLowerArea(parentId);
        log.info("REST request to get lower area. area number: {}", result.size());
        return result;
    }
}
