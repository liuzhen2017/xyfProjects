package com.xinyunfu.customer.service.address;

import com.xinyunfu.customer.constant.AreaConstant;
import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.domain.address.CustShippingAddressDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressAddDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressInfoDTO;
import com.xinyunfu.customer.dto.address.ShippingAddressUpdateDTO;
import com.xinyunfu.customer.dto.address.ShippingAreaInfoDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.repository.address.CustShippingAddressMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisSetService;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.SHIPPING_ADDRESS_ADD_ERROR;

@Slf4j
@Service
public class ShippingAddressManagerService {
    @Autowired private CustShippingAddressMapper custShippingAddressMapper;
    @Autowired private AreaManagerService areaManagerService;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisSetService redisSetService;

    /**
     * 获得当前用户的所有的收货地址
     */
    public List<ShippingAddressInfoDTO> getAllAddress(Long userNo){

        return this.getAllAddressId(userNo).stream().map(id -> this.getAddress(id))
                .sorted((var0, var1) -> var1.getDefaultAddress().compareTo(var0.getDefaultAddress()))
                .collect(Collectors.toList());
    }

    public List<Long> getAllAddressId(Long userNo){
        String redisKeys = RedisKeyRules.allShippingAddress(userNo);
        if(redisCommonService.exists(redisKeys)) {
            return redisSetService.get(redisKeys).stream()
                    .map(var -> Long.valueOf(var.toString())).collect(Collectors.toList());
        }
        List<Long> keys = custShippingAddressMapper.findIdByUerNo(userNo);
        if(Objects.isNull(keys) || keys.isEmpty()) return new ArrayList<>();

        redisSetService.addList(redisKeys, keys);
        return keys;
    }

    /**
     * 获得单个收货地址信息
     */
    public ShippingAddressInfoDTO getAddress(Long id){

        String redisKey = RedisKeyRules.shippingAddress(id);
        if(redisCommonService.exists(redisKey)){
            return this.getAddress((CustShippingAddressDTO) redisStringService.get(redisKey));
        }

        CustShippingAddressDTO address = custShippingAddressMapper.findById(id);
        if(Objects.isNull(address)) return null;

        redisStringService.add(redisKey, address);
        return this.getAddress(address);
    }

    /**
     * 删除单个收货地址
     */
    public void deleteAddress(Long userNo, Long id){
        custShippingAddressMapper.delete(id);
        redisCommonService.delete(RedisKeyRules.shippingAddress(id));
        redisSetService.remove(RedisKeyRules.allShippingAddress(userNo), id);
    }

    /**
     * 添加收货地址
     */
    public void addAddress(Long userNo, ShippingAddressAddDTO addressAdd) throws CustomerException {

        //用户收货地址上限10个
        if(this.getAllAddressId(userNo).size()>=10)
            throw new CustomerException(SHIPPING_ADDRESS_ADD_ERROR);

        this.deleteDefaultAddress(userNo, addressAdd.getDefaultAddress());
        CustShippingAddressDTO address = new CustShippingAddressDTO(userNo, addressAdd);
        custShippingAddressMapper.add(address);
        redisSetService.add(RedisKeyRules.allShippingAddress(userNo), address.getId());
    }

    /**
     * 修改收货地址
     */
    public void updateAddress(Long userNo, ShippingAddressUpdateDTO addressUpdate){
        this.deleteDefaultAddress(userNo, addressUpdate.getDefaultAddress());
        CustShippingAddressDTO address = new CustShippingAddressDTO(userNo, addressUpdate);
        custShippingAddressMapper.update(address);
        redisCommonService.delete(RedisKeyRules.shippingAddress(addressUpdate.getId()));
    }

    /**
     * 获取行政区域详细信息，包括其父地址信息
     */
    private ShippingAddressInfoDTO getAddress(CustShippingAddressDTO address){
        ShippingAddressInfoDTO shippingAddressInfo = new ShippingAddressInfoDTO(address);

        ShippingAreaInfoDTO areaInfo = new ShippingAreaInfoDTO();
        Long areaId = address.getRegionId();
        while(true){
            CustAreaDTO area = areaManagerService.findById(areaId);
            if(Objects.isNull(area)) break;
            if(area.getAreaLevel().equals(AreaConstant.area_level_1)){
                areaInfo.setProvince(area);
            }else if(area.getAreaLevel().equals(AreaConstant.area_level_2)){
                areaInfo.setCity(area);
            }else if(area.getAreaLevel().equals(AreaConstant.area_level_3)){
                areaInfo.setRegion(area);
            }else if(area.getAreaLevel().equals(AreaConstant.area_level_4)){
                areaInfo.setTown(area);
            }

            areaId = area.getParentId();
        }

        shippingAddressInfo.setAreaInfo(areaInfo);
        return shippingAddressInfo;
    }

    /**
     * 添加/修改收货地址时，如果修改后的地址为默认地址，则删除在redis中已存在的默认地址，重新获取
     */
    private void deleteDefaultAddress(Long userNo, Boolean defaultAddress){
        if(Objects.isNull(defaultAddress) || !defaultAddress) return;
        this.getAllAddress(userNo).forEach(address -> {
            if(address.getDefaultAddress()){
                redisCommonService.delete(RedisKeyRules.shippingAddress(address.getId()));
            }
        });
    }
}
