package com.xinyunfu.product.dto;

import com.xinyunfu.product.model.ProSku;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PropertyAndSkuDTO {
    private List<PropertyDTO> propertyDTOS;
    private List<ProSku> proSkus;
    private String imgUrl;
}
