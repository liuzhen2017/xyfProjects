package com.xingyunfu.ebank.web.res.transfer;

import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.transfer.*;
import com.xingyunfu.ebank.service.transfer.TransferMergeOfflineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 合并转账管理
 */
@Slf4j
@RestController
@RequestMapping("/ebank/transfer/merge")
public class TransferMergeMangerResource {

    @Autowired private TransferMergeOfflineService transferMergeOfflineService;

    /**
     * 创建合并转账
     */
    @PostMapping("/create")
    public void createMerge(@RequestBody@Validated TransferMergeCreateDTO create){
        log.info("REST request to create merge. create: {}", create);

        transferMergeOfflineService.createMerge(create);

        log.info("REST request to create merge. success!");
    }

    /**
     * 批量关闭合并转账
     */
    @PostMapping("/close")
    public void closeMerge(@RequestBody@Validated TransferMergeClosedDTO ebankOrderNoList){
        log.info("REST request to close merge transfer. ebankOrderNoList: {}",
                ebankOrderNoList.getList().size());
        transferMergeOfflineService.closeMerge(ebankOrderNoList.getList());
        log.info("REST request to close merge  transfer. success!");
    }

    /**
     * 分页查询合并转账
     */
    @PostMapping("/page")
    public PageDTO<TransferMergeResultDTO> pageMerge(@RequestBody@Validated TransferMergePageQueryDTO pageQuery){
        log.info("REST request to page query merge transfer. pageQuery: {}", pageQuery);

        PageDTO<TransferMergeResultDTO> result = transferMergeOfflineService.pageQuery(pageQuery);

        log.info("REST request to page query merge transfer. success! totalCount: {}", result.getTotalCount());
        return result;
    }

    /**
     * 查询所有合并转账
     */
    @PostMapping("/all")
    public List<TransferMergeResultDTO> allMerge(@RequestBody@Validated TransferMergeQueryDTO mergeQuery){
        log.info("REST request to get all merge transfer. mergeQuery: {}", mergeQuery);

        List<TransferMergeResultDTO> result = transferMergeOfflineService.allMerge(mergeQuery);

        log.info("REST request to get all merge transfer.success! size: {}", result.size());
        return result;
    }
}
