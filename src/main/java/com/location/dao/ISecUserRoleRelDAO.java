package com.location.dao;

import com.location.bean.vo.SecUserRoleRelDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Repository
@Mapper
public interface ISecUserRoleRelDAO {
    @Select("select * from sec_user_role_rel where user_id = #{userId} and status = #{status}")
    public List<SecUserRoleRelDO> getSecUserRoleRelById(@Param("userId") long userId, @Param("status") int status);

    @Select("select b.id,b.role_name as roleName from sec_user_role_rel a ,sec_role b where a.user_id=#{userId} and a.role_id = b.id and b.status = #{status} and a.status = #{status}")
    public List<Map> getRoleInfoByUserId(@Param(value = "userId") long userId, @Param(value = "status") int status)throws Exception;

    @Select("<script>select b.id as roleId,b.role_name as roleName from sec_user_role_rel a ,sec_role b where a.org_id in <foreach item='item' index='index' collection='orgIds' open=\"(\" separator=\",\" close=\")\">#{item}</foreach> and a.role_id = b.id and b.status = #{status}</script>")
    public List<Map> getRoleInfoByOrgId(@Param(value = "orgIds") List<Long> totalList, @Param(value = "status") int status)throws Exception;

    @Insert("insert into sec_user_role_rel(" +
            "user_id," +
            "role_id," +
            "op_id," +
            "create_time," +
            "update_time," +
            "status)values (" +
            "#{userId}," +
            "#{roleId}," +
            "#{opId}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status})")
    public void saveSecUserRoleRel(SecUserRoleRelDO secUserRoleRelDO)throws Exception;

    @Update("update sec_user_role_rel set update_time = CURRENT_TIMESTAMP,status=0 where user_id =#{userId}")
    public void delUserRoleRelService(@Param(value = "userId") long userId);
}
