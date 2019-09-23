package com.xinyunfu.report.dao.proxy;

import com.xinyunfu.report.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.*;

import java.util.*;

/**
 * @author xxb
 * @date 2019/9/2
 * @Description :
 */
@Mapper
public interface TransferMapper {
    /**
     * 获取转入账户信息
     * @return
     */
    @Select(" SELECT   DATE_FORMAT(create_time,'%Y-%m-%d') as name ,SUM(amount) num FROM  ebank_flow  \n" +
            "where flow_type='in' group  by name")
    @Results(value={
            @Result(id =true,property ="name",column ="name",javaType = String.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property ="num",column ="num",javaType = String.class,jdbcType= JdbcType.NUMERIC)
    })
    public List<Map<String,Object>> getTransferInMoney();

    /**
     * 获取转出数据信息
     * @return
     */
    @Select("SELECT   DATE_FORMAT(create_time,'%Y-%m-%d') as name ,SUM(amount) num FROM  ebank_flow  \n" +
            "where flow_type='out' group  by name")
    @Results(value={
            @Result(id =true,property ="name",column ="name",javaType = String.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property ="num",column ="num",javaType = String.class,jdbcType= JdbcType.NUMERIC)
    })
    public  List<Map<String,Object>> getTransferOutMoney();

    /**
     * 查询入账出账金额列表
     *
     * @param EbankFlow
     * @return 入账出账金额集合
     */
    // WHERE 1=1 AND  create_time&gt;=#{startDate} AND create_time&lt;=#{endDate}
    @Select("SELECT   DATE_FORMAT(create_time,'%Y-%m-%d') as createTime ,\n" +
            "case flow_type when 'in' THEN '转入' else '转出' end  as flowType,\n" +
            "sum(amount) amount FROM  ebank_flow  group  by createTime,flowType ORDER BY createTime DESC  ")
    public List<EbankFlow> selectTransferList(EbankFlow ebankFlow);
}
