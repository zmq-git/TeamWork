package com.location.dao;

import com.location.bean.vo.SecOperateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by ${Sunjt} on 2019/9/20.
 */
@Repository
@Mapper
public interface ISecOperateDAO {
    @Select("select operate_name from sec_operate where id=#{id}")
    public String getSecOperateNameById(@Param("id") Integer id)throws Exception;

    @Select("select * from sec_operate where id=#{id}")
    public SecOperateDO getSecOperateById(@Param("id") Integer id)throws Exception;
}
