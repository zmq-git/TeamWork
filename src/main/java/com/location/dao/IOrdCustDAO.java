package com.location.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IOrdCustDAO {

    @Select("select cust_name from ord_cust where id = 1")
    public String getOrderCustName();

}
