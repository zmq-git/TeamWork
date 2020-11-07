package com.location.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Mapper
public interface TeamBaseDao {
    @Select("select NEXTVAL('done_code_sequence')")
    public BigDecimal getDoneCode()throws Exception;
}