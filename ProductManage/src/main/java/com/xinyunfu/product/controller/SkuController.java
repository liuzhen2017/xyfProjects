package com.xinyunfu.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.model.ProSku;
import com.xinyunfu.product.service.ISkuService;
import com.xinyunfu.product.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private ISkuService skuService;

    /**
     * 新增商品sku
     *
     * @param json
     * @return
     */
    @PostMapping("/save")
    public ResponseInfo<String> saveProSku(@RequestBody String json) {
        ProSku proSku = JSONObject.parseObject(json, ProSku.class);
        if (skuService.save(proSku) == true)
            return ResponseInfo.success("新增成功!");
        else
            return ResponseInfo.error("新增失败!");
    }

    /**
     * 修改商品sku
     *
     * @param json
     * @return
     */
    @PostMapping("/update")
    public ResponseInfo<String> updateProSku(@RequestBody String json) {
        ProSku proSku = JSONObject.parseObject(json, ProSku.class);
        if (skuService.saveOrUpdate(proSku)){
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
    public ResponseInfo<IPage<ProSku>> findProPropertyByPage(@RequestBody ProSku proSku,
                                                             @RequestParam("page") Integer page,
                                                             @RequestParam("pageSize") Integer pageSize) {
        IPage<ProSku> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<ProSku> wrapper = new LambdaQueryWrapper<>();
        if (proSku.getProId() != null) {
            wrapper.eq(ProSku::getProId, proSku.getProId());
        }
        if (proSku.getGroupNo() != null) {
            wrapper.eq(ProSku::getGroupNo, proSku.getGroupNo());
        }
        if (proSku.getSkuNo() != null) {
            wrapper.eq(ProSku::getSkuNo, proSku.getSkuNo());
        }
        if (proSku.getStatus() != null) {
            wrapper.eq(ProSku::getStatus, proSku.getStatus());
        }
        return ResponseInfo.success(skuService.page(pages, wrapper));
    }

    /**
     * 下架商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/instock")
    public ResponseInfo<String> instockProSku(@RequestParam("ids") Long[] ids) {
        return skuService.updateStatus(ids, 1);
    }

    /**
     * 上架商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/reshelf")
    public ResponseInfo<String> reshelfProSku(@RequestParam("ids") Long[] ids) {
        return skuService.updateStatus(ids, 0);
    }

    /**
     * 删除商品sku
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    public ResponseInfo<String> deleteProSku(@RequestParam("ids") Long[] ids) {
        return skuService.deleteProSku(ids, 2);
    }

    /*
     * 根据proId查询
     */
    @PostMapping("/queryByProId")
    public ResponseInfo<List<ProSku>> queryByProId(@RequestBody ProIdDTO proIdDTO) {
        return ResponseInfo.success(skuService.findSkuByProId(proIdDTO));
    }

    /*
     * 分页查询
     */
    @GetMapping("/queryByPage")
    public ResponseInfo<PageVO<ProSku>> queryByPage(ProSku proSku, Integer page, Integer pageSize) {
        return skuService.queryByPage(proSku, page, pageSize);
    }

    /*
     *根据skuId查询商品类型,0普通商品,1秒杀商品,2套餐商品
     */
    @GetMapping("/findProTypeBySkuId")
    public ResponseInfo<Map<Long, Integer>> findProTypeBySkuId(@RequestParam("ids") String ids) {
        return ResponseInfo.success(skuService.findProTypeBySkuId(ids));
    }

    @PostMapping("/subtractStock")
    public Boolean subtractStock(@RequestBody Map<Long, Integer> map) {
        return skuService.subtractStock(map);
    }

    @PostMapping("/addStock")
    public Boolean addStock(@RequestBody Map<Long, Integer> map) {

        return skuService.addStock(map);
    }

    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody ProSku proSku) {
        return skuService.saveOrUpdate(proSku);
    }

    /**
     * 查询所有来源为京东,状态为0的商品的sku
     *
     * @return
     */
    @GetMapping("/queryJDSkuReshelf")
    public ResponseInfo<List<ProSku>> queryJDSkuReshelf() {
        return ResponseInfo.success(skuService.queryJDSkuReshelf());
    }

    @GetMapping("/selectProSkuById/{skuId}")
    public ProSku selectProSkuById(@PathVariable("skuId") Long skuId) {
        return skuService.getById(skuId);
    }

    /**
     * edit by lhpu
     *=====================================================================================
     */
    /**
     * 根据商品id查询sku是否存在
     *
     * @return
     */
    @GetMapping("/checkSkuByProId")
    public ResponseInfo<Boolean> checkSkuByProId(@RequestParam("proId") Long proId) {
        return ResponseInfo.success(skuService.checkSkuByProId(proId));
    }


    /*
     *新增sku
     */
    @PostMapping("/addSku")
    public ResponseInfo<String> addSku(@RequestBody ProSku proSku) {
        if (skuService.addProsku(proSku)) {
            return ResponseInfo.success("添加成功");
        } else {
            return ResponseInfo.error("添加失败");
        }
    }


    /*
     * 修改sku
     */
    @PostMapping("/updateSku")
    public ResponseInfo<String> updateSku(@RequestBody ProSku proSku) {
        return skuService.update(proSku);
    }

    /*
     * 根据proId查询sku，当前设计为1对1
     */
    @GetMapping("/getSkuByProId")
    public ResponseInfo<ProSku> getSkuByProId(@RequestParam("proId") long proId) {
        return ResponseInfo.success(skuService.getSkuByProId(proId));

    }


    /**
     * edit by lhpu
     * ==========================================================================================
     */

    /**
     * 根据skuId查询skuNo
     *
     * @param skuId
     * @return
     */
    @GetMapping("/getSkuNo")
    public ResponseInfo<String> getSkuNoBySkuId(@RequestParam("skuId") Long skuId) {
        String result = skuService.getSkuNoBySkuId(skuId);
        if (result != null) {
            return ResponseInfo.success(result);
        }
        return ResponseInfo.error(null);
    }


    /**
     * 同步jd数据时添加sku
     */
    @GetMapping("/synsAddSku")
    public ResponseInfo<String> addSku(@RequestParam("json") String json) {

        ProSku proSku = JSONObject.parseObject(json, ProSku.class);
        if (skuService.addProsku(proSku)) {
            return ResponseInfo.success("添加成功");
        } else {
            return ResponseInfo.error("添加失败");
        }
    }

    /**
     * 根据 proNo更新sku
     */
    @GetMapping("/updateSkuByProNo")
    public ResponseInfo<String> updateSkuByProNo(@RequestParam("proNo") String proNo) {

        if (skuService.updateSkuByProNo(proNo)) {
            return ResponseInfo.success("更新成功");
        } else {
            return ResponseInfo.error("更新失败");
        }

    }

    /**
     * 根据skuNo编号查询商品id
     *
     * @param skuNo
     * @return
     */
    @GetMapping("/getProIdBySkuNo")
    public ResponseInfo<Long> getProIdBySkuNo(@RequestParam("skuNo") String skuNo) {
        return ResponseInfo.success(skuService.getProIdBySkuNo(skuNo));
    }

    /**
     * 根据proId和groupNo查询skuId
     * @param proId
     * @param groupNo
     * @return
     */
    @GetMapping("/getSkuIdByProIdAndGroupNo")
    ResponseInfo<Long> getSkuIdByProIdAndGroupNo(@RequestParam("proId") Long proId, @RequestParam("groupNo") String groupNo){
        return ResponseInfo.success(skuService.getSkuIdByProIdAndGroupNo(proId,groupNo));
    }




}
