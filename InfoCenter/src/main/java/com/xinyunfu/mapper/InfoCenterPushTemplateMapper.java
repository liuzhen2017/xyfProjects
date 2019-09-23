package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InfoCenterPushTemplateMapper extends BaseMapper<InfoCenterPushTemplateDTO> {

    @Select("select * from infocenter_push_template where template_name=#{templateName}")
    InfoCenterPushTemplateDTO findByTemplateName(String templateName);

    @Select("select template_name from infocenter_push_template limit #{pageNo}, #{pageSize}")
    List<String> findLimit(Integer pageNo, Integer pageSize);

    @Select("select count(id) from infocenter_push_template")
    Integer count();

    @Update({"<script>",
            "update infocenter_push_template",
            "   <trim prefix=\"set\" suffixOverrides=','>",
            "       <if test='id != null'>id = #{id},</if>",
            "       <if test='templateName != null'>template_name = #{templateName},</if>",
            "       <if test='templateType != null'>template_type = #{templateType},</if>",
            "       <if test='title != null'>title = #{title},</if>",
            "       <if test='content != null'>content = #{content},</if>",
            "       <if test='enable != null'>enable = #{enable},</if>",
            "       <if test='remark != null'>remark = #{remark},</if>",
            "       <if test='createTime != null'>create_time = #{createTime},</if>",
            "       <if test='lastModifyTime != null'>last_modify_time = #{lastModifyTime},</if>",
            "   </trim>",
            "where id=#{id}",
            "</script>"})
    void update(InfoCenterPushTemplateDTO template);
}
