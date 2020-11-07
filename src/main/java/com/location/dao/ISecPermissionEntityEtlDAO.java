package com.location.dao;

import com.location.bean.vo.SecPermissionEntityEtlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Repository
@Mapper
public interface ISecPermissionEntityEtlDAO {
    @Select("select * from sec_permission_entity_etl  where permission_id = #{permissionId} and status =#{status}")
    public SecPermissionEntityEtlDO getSecPermissionEntityEtlByPermissionId(@Param("permissionId") Integer permissionId, @Param("status") byte status)throws Exception;
}
