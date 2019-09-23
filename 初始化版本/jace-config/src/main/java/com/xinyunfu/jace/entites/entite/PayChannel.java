package com.xinyunfu.jace.entites.entite;

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
    private int id;
    private String name;
    private String key;
    private Integer enable;
}
