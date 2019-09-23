package com.xinyunfu.report.dao.proxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.report.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jace
 * @since 2019-08-28
 */
@Mapper
public interface CusUserMapper extends BaseMapper<CustUser> {

    /**
     * 根据创建时间获取注册用户 注册量
     * @return
     */
    @Select(" SELECT DATE_FORMAT(create_time, '%Y-%m-%d ') as name,count(0) num  from  cust_user GROUP  BY DATE_FORMAT(create_time, '%Y-%m-%d ')")
    @Results(value={
            @Result(id =true,property ="name",column ="name",javaType = String.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property ="num",column ="num",javaType = String.class,jdbcType= JdbcType.NUMERIC)
    })
    public List<Map<String,Object>> getUserRegistCount();


    /**
     * 查询用户列表
     *
     * @param custUser 用户信息
     * @return 用户集合
     */
    //WHERE 1=1 AND  create_time&gt;=#{startDate} AND create_time&lt;=#{endDate}
    @Select("SELECT DATE_FORMAT(create_time, '%Y-%m-%d ') as createTime,count(0) registNum  from  cust_user GROUP  BY createTime " +
            "ORDER BY createTime DESC ")
    public List<CustUser> selectCustUserList(CustUser custUser);

}
