package com.xinyunfu.report.dao.proxy;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.*;

import java.util.List;
import java.util.Map;

/**
 * @author xxb
 * @date 2019/9/2
 * @Description :
 */
@Mapper
public interface FansMapper {
    /**
     * 获取粉丝数量 推荐排名
     * @return
     */
    @Select("SELECT u.nick_name name,temp.num as num FROM cust_user u  JOIN  (select  referrer_no, IFNULL(count(0),0) num FROM  cust_user GROUP  BY referrer_no\n" +
            "             HAVING count(0) >0 order by count(0) desc limit 10) temp\n" +
            "             on u.user_no = temp.referrer_no ORDER BY num DESC")
    @Results(value={
            @Result(id =true,property ="name",column ="name",javaType = String.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property ="num",column ="num",javaType = String.class,jdbcType= JdbcType.NUMERIC)
    })
    public List<Map<String,Object>> selectFansList();
}
