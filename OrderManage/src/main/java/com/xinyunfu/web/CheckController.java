package com.xinyunfu.web;

import com.xinyunfu.dto.docking.JDAreaLimitParam;
import com.xinyunfu.dto.docking.JDPostParam;
import com.xinyunfu.dto.docking.JDStockParam;
import com.xinyunfu.dto.product.JDSaleSkusParam;
import com.xinyunfu.feign.ProductDocKingFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author XRZ
 * @date 2019/8/20
 * @Description :
 */
@RestController
public class CheckController {

    @Autowired
    private ProductDocKingFeign productDocKingFeign;

    /**
     * 查询邮费
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryFreight")
    public ResponseInfo queryFreight(@RequestBody JDPostParam param) {
        return productDocKingFeign.queryFreight(param);
    }

    /**
     * 查询商品是否可售
     *
     * @param skuIds
     * @return
     */
    @PostMapping("/preOrder/checkSale")
    public ResponseInfo checkSale(@RequestBody JDSaleSkusParam skuIds) {
        return productDocKingFeign.checkSale(skuIds);
    }


    /**
     * 查询区域购买是否被限制
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryAreaLimit")
    public ResponseInfo queryAreaLimit(@RequestBody JDAreaLimitParam param) {
        return productDocKingFeign.queryAreaLimit(param);
    }

    /**
     * 查询商品是否有货
     *
     * @param param
     * @return
     */
    @PostMapping("/preOrder/queryStock")
    public ResponseInfo queryStock(@RequestBody JDStockParam param) {
        return productDocKingFeign.queryStock(param);
    }

}
