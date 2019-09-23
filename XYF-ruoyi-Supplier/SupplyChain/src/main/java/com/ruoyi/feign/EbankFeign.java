package com.ruoyi.feign;

import com.ruoyi.domain.AccountAddDTO;
import com.ruoyi.domain.AccountUpdateDTO;
import com.ruoyi.domain.EbankAccount;
import com.ruoyi.dto.PageDTO;
import com.ruoyi.dto.TransferMergeCloseParam;
import com.ruoyi.dto.TransferMergePageParamDTO;
import com.ruoyi.dto.TransferMergeResultDTO;
import com.ruoyi.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author tym
 * @date 2019/8/2
 * @Description :
 */
@FeignClient("Ebank")
public interface EbankFeign {
    /**
     * 根据id查询当前用户的内部账户
     *
     * @param id 账户id
     * @return
     */
    @GetMapping("/account/findById")
    public EbankAccount findById(@RequestParam("id") Long id);

    /**
     * 新增内部账户
     *
     * @param ebankAccount
     * @return
     */
    @PostMapping("/AccManger/createdInnerAcct")
    public String saveEbankAccount(@RequestBody EbankAccount ebankAccount);

    /**
     * 提现
     *
     * @param money 提现金额
     * @return
     */
    @GetMapping("/account/drawings")
    public String drawings(@RequestParam("money") BigDecimal money);

    /**
     * 创建内部账户，创建用户时调用此接口
     */
    @PutMapping("/ebank/account")
    ResponseInfo addAccount(@RequestBody AccountAddDTO accountAdd);

    /**
     * 更新账户
     */
    @PostMapping("/ebank/account")
    ResponseInfo updateAccount(@RequestBody AccountUpdateDTO accountUpdate);

    //====================================合并转账=========================================

    /**
     * 分页条件查询合并转账
     * @param transferPageParamDTO
     * @return
     */
    @PostMapping("/ebank/transfer/merge/page")
    public ResponseInfo<PageDTO<TransferMergeResultDTO>> findEbankTransferMergePage(@RequestBody TransferMergePageParamDTO transferMergePageParamDTO);

    /**
     * 批量关闭合并转账
     * @param transferMergeCloseParam
     * @return
     */
    @PostMapping("/ebank/transfer/merge/close")
    public ResponseInfo<Object> closeTranferMerge(@RequestBody TransferMergeCloseParam transferMergeCloseParam);












}
