package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单搜索记录表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchHistory extends Base{

    /**
     * 主键id
     */
    @TableId(value = "search_id", type = IdType.AUTO)
    private Long searchId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 搜索的关键字
     */
    private String key;


    public SearchHistory(String userId, String key, String createdBy, String updatedBy) {
        this.userId = userId;
        this.key = key;
        super.createdBy = createdBy;
        super.updatedBy = updatedBy;
    }
}
