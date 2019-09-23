package com.ruoyi.dto;

import com.ruoyi.domain.ShelfGoodsParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
public class UpperShelfDto implements Serializable {

    /**
     * 上架的商品的信息
     */
     private List<ShelfGoodsParam> list;
}
