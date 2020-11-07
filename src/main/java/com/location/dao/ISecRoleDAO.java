package com.location.dao;

import com.location.bean.vo.SecInsPermissionDO;
import com.location.bean.vo.SecOrgRoleRelDO;
import com.location.bean.vo.SecRoleDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Repository
@Mapper
public interface ISecRoleDAO {
    @Select("select * from sec_role where id = #{id} and status = #{status}")
    public SecRoleDO getSecRoleById(@Param("id") long id, @Param("status") int status);

    @Select("<script>select id,role_name as roleName,description from sec_role where status = #{status} and id in <foreach item='item' index='index' collection='roleIds' open=\"(\" separator=\",\" close=\")\">#{item}</foreach></script>")
    public List<Map> getAllRoleIdAndRoleName(@Param(value = "roleIds") List<Long> roleList, @Param(value = "status") int status)throws Exception;

    @Select("select * from sec_ins_permission where role_id = #{roleId} order by relat_parent_id and status=#{status}")
    public List<SecInsPermissionDO> getInsPermissonByRoleId(@Param(value = "roleId") long roleId, @Param("status") int status)throws Exception;

    @Select("select menu_id from sec_ins_permission where role_id = #{roleId} and status=#{status}")
    public List<Long> getInsPermissonMenuIdsByRoleId(@Param(value = "roleId") long roleId, @Param("status") int status)throws Exception;

    @Select("<script>select menu_Id from sec_ins_permission where role_id in <foreach item='item' index='index' collection='roleIds' open=\"(\" separator=\",\" close=\")\">#{item}</foreach> and status=#{status}</script>")
    public List<Long> getInsPergetInsPermissonByRoleIds(@Param(value = "roleIds") List<Long> roleIds, @Param(value = "status") int status)throws Exception;

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sec_role(" +
            "role_type," +
            "role_name," +
            "role_level," +
            "description," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status)values(" +
            "#{roleType}," +
            "#{roleName}," +
            "#{roleLevel}," +
            "#{description}," +
            "#{opId}," +
            "#{createTime}," +
            "#{updateTime}," +
            "#{status})")
    public void saveSecRole(SecRoleDO secRoleDO)throws Exception;

    @Insert("<script>insert into sec_ins_permission(" +
            "role_id," +
            "menu_id," +
            "description," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status" +
            ")values (" +
            "#{roleId}," +
            "#{menuId}," +
            "#{description}," +
            "#{opId}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status})</script>")
    public void saveSecInsPermission(SecInsPermissionDO secInsPermissionDO)throws Exception;

    @Update("update sec_role set update_time = CURRENT_TIMESTAMP,status = 0 where id =#{id}")
    public void delSecRole(@Param(value = "id") long roleId)throws Exception;

    @Update("update sec_ins_permission set update_time = CURRENT_TIMESTAMP,status = 0 where role_id = #{roleId}")
    public void delSecInsPermission(@Param(value = "roleId") long roleId)throws Exception;

    @Update("<script>update sec_role set update_time = CURRENT_TIMESTAMP " +
            "<if test='roleType!=null'>,role_type =#{roleType}</if>" +
            "<if test='roleName!=null'>,role_name=#{roleName}</if>" +
            "<if test='roleLevel!=null'>,role_level=#{roleLevel}</if>" +
            "<if test='description!=null'>,description=#{description}</if>" +
            "<if test='opId!=null'>,op_id=#{opId}</if> where id = #{id}</script>")
    public void updateSecRole(SecRoleDO secRoleDO)throws Exception;

    @Select("<script>select role_id as roleId,org_id as orgId from sec_org_role_rel where status =#{status} and org_id in <foreach item='item' index='index' collection='orgIds' open=\"(\" separator=\",\" close=\")\">#{item}</foreach></script>")
    public List<Map> getSecOrgRoleRelRoleIds(@Param(value = "orgIds") List<Long> list, @Param(value = "status") int status)throws Exception;

    @Insert("insert into sec_org_role_rel (" +
            "org_id," +
            "role_id," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status," +
            "ext," +
            "ext1)values(" +
            "#{orgId}," +
            "#{roleId}," +
            "#{opId}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status}," +
            "#{ext}," +
            "#{ext1})")
    public void saveSecOrgRoleRel(SecOrgRoleRelDO secOrgRoleRelDO)throws Exception;

    @Update("update sec_org_role_rel set update_time = CURRENT_TIMESTAMP,status = 0 where role_id =#{roleId}")
    public void delSecOrgRoleRel(@Param(value = "roleId") long roleId)throws Exception;

    @Update("update sec_org_role_rel set update_time = CURRENT_TIMESTAMP,org_id = #{orgId},op_id=#{opId} where role_id =#{roleId}")
    public void updateSecOrgRoleRel(@Param(value = "orgId") long orgId, @Param(value = "roleId") long roleId, @Param(value = "opId") int opId)throws Exception;

    @Select("select * from sec_role where status = 1 and role_type = #{roleType}")
    public List<SecRoleDO> getAllRoleType(Integer roleType) throws Exception;
}
