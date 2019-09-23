package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单发票表
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderInvoice extends Base{

    /**
     * 唯一的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String itemNo;

    /**
     * 发票ID
     */
    private String invoiceId;

    /**
     * 开票状态（未开=0，供应商已开=1，总后台已开=2）
     */
    private Integer status;

    /**
     * 开票日期
     */
    private String billingDate;

    /**
     * 发票类型（电子发票=0，纸只发票=1）
     */
    private Integer type;


    public OrderInvoice(String itemNo, String invoiceId,String createdBy,String updatedBy,Integer type) {
        this.itemNo = itemNo;
        this.invoiceId = invoiceId;
        super.createdBy = createdBy;
        super.updatedBy = updatedBy;
        this.type = type;
    }
}
