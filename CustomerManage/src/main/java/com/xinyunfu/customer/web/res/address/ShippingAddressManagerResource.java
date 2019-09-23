package com.xinyunfu.customer.web.res.address;

import com.xinyunfu.customer.dto.SingleIdDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressAddDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressInfoDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressUpdateDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.address.ShippingAddressManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;

/**
 * 收货地址管理
 */
@Slf4j
@RestController
@RequestMapping("/customer/address")
public class ShippingAddressManagerResource {

    @Autowired private ShippingAddressManagerService shippingAddressManagerService;

    /**
     * 查询单个收货地址
     */
    @PostMapping("/info")
    public ShippingAddressInfoDTO getAddress(@RequestBody SingleIdDTO singleId){
        Long id = singleId.getId();
        log.info("REST request to get address. id: {}", id);
        ShippingAddressInfoDTO addressInfo = shippingAddressManagerService.getAddress(id);
        log.info("REST request to get address. success! address: {}", addressInfo.getAddress());
        return addressInfo;
    }

    /**
     * 增加收货地址
     */
    @PostMapping("/add")
    public void addAddress(@RequestHeader(header_uid)Long userNo, @Validated@RequestBody ShippingAddressAddDTO addressAdd) throws CustomerException {
        log.info("REST request to add address. userNo: {}, addressAdd: {}", userNo, addressAdd);
        shippingAddressManagerService.addAddress(userNo, addressAdd);
        log.info("REST request to add address. success!");
    }

    /**
     * 修改收货地址
     */
    @PostMapping("/change")
    public void updateAddress(@RequestHeader(header_uid)Long userNo, @Validated@RequestBody ShippingAddressUpdateDTO addressUpdate){
        log.info("REST request to update address. userNo: {}, addressUpdate: {}", userNo, addressUpdate);
        shippingAddressManagerService.updateAddress(userNo, addressUpdate);
        log.info("REST request to update address. success!");
    }

    /**
     * 删除收货地址
     */
    @PostMapping("/delete")
    public void deleteAddress(@RequestHeader(header_uid)Long userNo, @RequestBody SingleIdDTO singleId){
        Long id = singleId.getId();
        log.info("REST request to delete address. userNo: {}, id: {}", userNo, id);
        shippingAddressManagerService.deleteAddress(userNo, id);
        log.info("REST request to delete address. success!");
    }

    /**
     * 查询所有收货地址
     */
    @PostMapping("/all")
    public List<ShippingAddressInfoDTO> getAllAddress(@RequestHeader(header_uid)Long userNo){
        log.info("REST request to get all address. userNo: {}", userNo);
        List<ShippingAddressInfoDTO> addressList = shippingAddressManagerService.getAllAddress(userNo);
        if(Objects.isNull(addressList)) addressList = new ArrayList<>();
        log.info("REST request to get all address. success! address number: {}", addressList.size());
        return addressList;
    }
}
