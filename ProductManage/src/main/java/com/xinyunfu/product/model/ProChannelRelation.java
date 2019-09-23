package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("pro_channel_relation")
@NoArgsConstructor
public class ProChannelRelation extends BaseModel{
	
	@TableId(type = IdType.AUTO)
    /**主键id */
	private Long id;
	/**商品id */
	private Long proId;
	/**商品名称 */
	private String proName;
	/**分类id */
	private Long channelId;
	/**分类名称 */
	private String channelName;
    /**商品展示图片 */
	private String imgUrl;
	/**图片背景颜色 (放在轮播图位置时需要)*/
	private String color;
    /**排序号 */
	private Integer sortNumber;

    public ProChannelRelation(Long proId,String proName, Long channelId,String channelName) {
        this.proId = proId;
        this.proName=proName;
        this.channelId = channelId;
        this.channelName=channelName;
    }
}
