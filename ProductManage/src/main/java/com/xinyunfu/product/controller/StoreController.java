package com.xinyunfu.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.enums.Res;
import com.xinyunfu.product.model.Store;
import com.xinyunfu.product.service.StoreService;
import com.xinyunfu.product.utils.ResInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tym
 * @since 2019/7/10
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /*
     * 新增商铺
     */
    @PostMapping("/add")
    public ResponseInfo<String> add(@RequestBody Store store) {
        return storeService.saveStore(store);
    }

    /*
     * 修改商铺
     */
    @PostMapping("/update")
    public ResponseInfo<String> update(Store store) {
        return storeService.updateStore(store);
    }

    /*
     * 启用
     */
    @GetMapping("/enabled")
    public ResponseInfo<String> enabled(Long[] ids) {
        int status = 0;   //启用
        return storeService.updateStatus(ids, status);
    }

    /*
     * 禁用
     */
    @GetMapping("/disable")
    public ResponseInfo<String> disable(Long[] ids) {
        int status = 1;   //禁用
        return storeService.updateStatus(ids, status);
    }

    /*
     * 删除商铺
     */
    @GetMapping("/delete")
    public ResponseInfo<String> delete(Long[] ids) {
        int status = 2;
        return storeService.updateStatus(ids, status);
    }

    /*
     * 分页查询
     */
    @PostMapping("/page")
    public ResponseInfo<IPage<Store>> queryByPage(@RequestBody Store store, @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) {
        return storeService.queryByPage(store, page, pageSize);
    }

    /**
     * 根据当前登录用户的企业id查询商家id
     * @param ownerId
     * @return
     */
    @PostMapping("/findStoreIdByOwnerId")
    public ResponseInfo<Long> findStoreIdByOwnerId(@RequestBody String ownerId){
        if (ownerId==null){
            return new ResInfo(Res.ERROR_PARAM);
        }else {
            if (storeService.findStoreIdByOwnerId(ownerId)==null){
                return new ResInfo(Res.NO_DATA);
            }else {
                return ResponseInfo.success(storeService.findStoreIdByOwnerId(ownerId));
            }
        }
    }

    /**
     * 根据当前登录用户的企业id查询商家名称
     * @param ownerId
     * @return
     */
    @PostMapping("/findStoreNameByOwnerId")
    public ResponseInfo<String> findStoreNameByOwnerId(@RequestBody String ownerId){
        if (ownerId==null){
            return new ResInfo(Res.ERROR_PARAM);
        }else {
            if (storeService.findStoreNameByOwnerId(ownerId)==null){
                return new ResInfo(Res.NO_DATA);
            }else {
                return ResponseInfo.success(storeService.findStoreNameByOwnerId(ownerId));
            }
        }
    }


}
