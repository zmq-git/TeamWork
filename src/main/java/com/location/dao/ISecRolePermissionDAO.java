package com.location.dao;

import com.location.bean.vo.SecPermissionEntityEtlDO;
import com.location.bean.vo.SecRolePremissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Repository
@Mapper
public interface ISecRolePermissionDAO {
    @Select("select * from sec_role_permission where role_id = #{roleId} and status = #{status}")
    public List<SecRolePremissionDO> getSecRolePermissionByRoleId(@Param("roleId") Integer roleId, @Param("status") byte status)throws Exception;
    /**
     * 根据角色获取角色所有的权限
     * @param roleId
     * @param status
     * @return
     */
    @Select("select b.* from sec_role_permission a,sec_permission_entity_etl b where a.permission_id = b.permission_id and b.status = #{status} and a.role_id = #{roleId}")
    public List<SecPermissionEntityEtlDO> getPremissionByRoleId(@Param("roleId") Long roleId, @Param("status") byte status);
}
