package com.xinyunfu.product.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.product.model.ProChannel;
import com.xinyunfu.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author TYM
 * @date 2019/8/13
 * @Description :
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProChannelDTO {
    private ProChannel proChannel;       //当前分类
    private IPage<ProDto> page;         //分类下的商品分页列表



}
