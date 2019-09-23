package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.service.IProDetailsService;
import com.xinyunfu.product.utils.ResInfo;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tym
 * @date 2019/7/29
 * @Description :
 */
@RestController
@RequestMapping("/proDetails")
public class ProDetailsController {

    @Autowired
    private IProDetailsService proDetailsService;


    /**
     * 查询所有来源为京东,状态为0的商品的详情
     *
     * @return
     */
    @GetMapping("/queryJDProDetaislsReshelf")
    public ResponseInfo<List<ProDetails>> queryJDProDetaislsReshelf() {
        return ResponseInfo.success(proDetailsService.queryJDProDetaislsReshelf());
    }

//      /**
//       * 根据商品id查询商品详情
//       *
//       * @param proId
//       * @return
//       */
//      @GetMapping("/queryProDetailsByProId")
//      public ResponseInfo<ProDetails> queryProDetailsByProId(@RequestParam("proId") Long proId) {
//            return proDetailsService.findProDetailsByProId(proId);
//      }


    /**
     * 根据商品id查询商品详情
     *
     * @param proId
     * @return
     */
    @GetMapping("/queryProDetailsByProId")
    public ResponseInfo<ProDetails> queryProDetailsByProId(@RequestParam("proId") Long proId) {
        if (proId == null) {
            return new ResInfo(Res.ERROR_PARAM);
        }
        ProDetails proDetails = proDetailsService.findProDetailsByProId(proId);

        if (proDetails == null) {
            return new ResInfo(Res.NO_DATA);
        }
        return ResponseInfo.success(proDetails);

    }

    /**
     * 新增商品详情
     *
     * @param proDetails
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProDetails(@RequestBody ProDetails proDetails) {
        return proDetailsService.saveProDetails(proDetails);
    }

    /**
     * 修改商品详情
     *
     * @param proDetails
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProDetails(@RequestBody ProDetails proDetails) {
        if (proDetailsService.saveOrUpdate(proDetails)){
            return ResponseInfo.success("修改成功");
        }else{
            return ResponseInfo.error("修改失败");
        }
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/queryByPage")
    public ResponseInfo<IPage<ProDetails>> findProDetailsByPage(@RequestBody ProDetails proDetails,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("pageSize") Integer pageSize) {
        IPage<ProDetails> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProDetails> wrapper = new LambdaQueryWrapper<>();
        if (proDetails.getStatus() != null) {
            wrapper.eq(ProDetails::getStatus, proDetails.getStatus());
        }
        return ResponseInfo.success(proDetailsService.page(pages, wrapper));
    }

    /**
     * 下架商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProDetails(@RequestParam("ids") Long[] ids) {
        return proDetailsService.updateStatus(ids, 1);
    }

    /**
     * 上架商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProdetails(@RequestParam("ids") Long[] ids) {
        return proDetailsService.updateStatus(ids, 0);
    }

    /**
     * 删除商品详情
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteProDetails(@RequestParam("ids") Long[] ids) {
        return proDetailsService.deleteProDetails(ids, 2);
    }


    /**
     * edit by lhpu
     * ========================================================
     *
     */


    /**
     * 根据商品id查询商品详情是否已存在
     *
     * @param proId
     * @return
     */
    @GetMapping("/checkProDetailsByProId")
    public ResponseInfo<Boolean> checkProDetailsByProId(@RequestParam("proId") Long proId) {
        return ResponseInfo.success(proDetailsService.checkProDetailsByProId(proId));
    }


    /**
     * 新增商品详情
     *
     * @param proDetails
     * @return
     */
    @PostMapping("/add")
    public ResponseInfo<String> addProdetails(@RequestBody ProDetails proDetails) {
        if (proDetailsService.addProDetails(proDetails)) {
            return ResponseInfo.success("添加商品详情成功");
        }else {
            return ResponseInfo.error("添加商品详情失败");
        }

    }







}


