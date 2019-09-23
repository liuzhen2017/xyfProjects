package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.PageVODTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProPackage;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IPackageProService;
import com.xinyunfu.product.service.IPackageService;
import com.xinyunfu.product.service.IProductService;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/9
 */
@RestController
@RequestMapping("/package")
@Slf4j
public class PackageController {

    @Autowired
    private IPackageService packageService;
    @Autowired
    private IPackageProService packageProService;
    @Autowired
    private IProductService productService;

    /*
     * 新增套餐
     */
    @PostMapping("/save")
    public ResponseInfo<String> add(@RequestBody ProPackage proPackage) {
        if (packageService.save(proPackage))
            return ResponseInfo.success("插入成功");
        else
            return ResponseInfo.error("插入失败");
    }

    /*
     * 修改套餐
     */
    @PostMapping("/update")
    public ResponseInfo<String> update(@RequestBody ProPackage proPackage) {
        if (packageService.saveOrUpdate(proPackage)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }

    /*
     * 启用套餐
     */
    @GetMapping("/enabled")
    public ResponseInfo<String> enabled(Integer[] ids) {
        int status = 0;   //启用
        return packageService.updateStatus(ids, status);
    }

    /*
     * 禁用套餐
     */
    @GetMapping("/disable")
    public ResponseInfo<String> disable(Integer[] ids) {
        int status = 1;   //禁用
        return packageService.updateStatus(ids, status);
    }

    /*
     * 删除套餐
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteById(Integer[] ids) {
        int status = 2;
        return packageService.updateStatus(ids, status);
    }

    /*
     * 根据id查询套餐
     */
    @GetMapping("/queryById/{packageId}")
    public ResponseInfo<ProPackage> queryById(@PathVariable("packageId") Integer packageId) {
        return packageService.findOneById(packageId);
    }

    /*
     * 分页查询所有套餐
     */
    @PostMapping("/findAllPackage")
    public ResponseInfo<IPage<ProDto>> findAllPackage(@RequestBody PageVODTO pageVODTO) {
        if (pageVODTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        }
        IPage<ProDto> proDtoIPage = packageService.findAllPackage(pageVODTO);

        if (proDtoIPage == null) {
            return new ResInfo(Res.NO_DATA);
        }
        return ResponseInfo.success(proDtoIPage);
    }



    /*
     *根据商品id查询套餐商品详情
     */
    @PostMapping("/findPackageProductDTOByProId")
    public ResponseInfo<ProductDTO> findPackageProductDTOByProId(@RequestBody ProIdDTO proIdDTO) {
        if (proIdDTO == null) {
            return new ResInfo(Res.ERROR_PARAM);
        } else {
            ProductDTO productDTO = productService.findProductDtoById(proIdDTO);
            if (productDTO == null)
                return new ResInfo(Res.NO_DATA);
            else
                return ResponseInfo.success(productDTO);
        }
    }


}
