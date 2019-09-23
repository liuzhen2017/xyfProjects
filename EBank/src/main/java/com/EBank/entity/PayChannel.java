package com.EBank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/9
 */
@Data
@NoArgsConstructor
public class PayChannel {
    @TableId(value="ID", type= IdType.AUTO)
    private int id;
    private String openName;
    private String openKey;
    private Integer enable;
    private String payKey;
    private String payName;
    private int orderBys;
    private String channles;
}
