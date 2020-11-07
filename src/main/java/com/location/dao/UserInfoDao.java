package com.location.dao;

import com.location.bean.vo.SecOrgDO;
import com.location.bean.vo.SecStaffDO;
import com.location.bean.vo.SecUserDO;
import com.location.bean.vo.SecUserRoleRelDO;
import com.location.bean.po.*;
import com.location.bean.vo.UserStaffVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Repository
@Mapper
public interface UserInfoDao extends TeamBaseDao{
    @Insert("insert into user_info (USER_ID,BILL_ID,USER_ACCOUNT,USER_PASSWORD,USER_NAME,USER_EMAIL,STATE,DONE_CODE,CREATE_DATE,DONE_DATE,EFF_DATE,EXP_DATE) values (#{userId},#{billId},#{userAccount},#{userPassword},#{userName},#{userEmail},1,#{doneCode},now(),#{doneDate},#{effDate},#{expDate}) ")
    public void addNewUser(UserInfoPO userInfoPO) throws Exception;
    @Select("select NEXTVAL('user_id_sequence')")
    public BigDecimal getUserId()throws Exception;
    @Select("select * from user_info where USER_ACCOUNT =#{userAccount}")
    public UserInfoPO getUserInfo(String userAccount)throws Exception;
    @Select("select USER_NAME as userName from user_info where USER_ID =#{userId}")
    public String getUserNameById(BigDecimal userId)throws Exception;
    @Select("<script>select * from user_info <if test ='userName!=null and state==1'> where USER_NAME like CONCAT('%', #{userName} ,'%')</if></script>")
    public List<UserInfoPO> getUserInfobyUserName(@Param(value = "userName") String userName, @Param(value = "state") int state)throws Exception;


    @Select("select * from sec_user where username = #{userName} and status = #{status}")
    public SecUserDO getSecUserByUserName(@Param("userName") String userName, @Param("status") Integer status) throws Exception;
    @Insert("insert into sec_staff(id,staff_name,portrait_address,org_id,phone_num,address,creator,create_time,modifier,update_time,status,mail) values (#{id},#{staffName},#{portraitAddress},#{orgId},#{phoneNum},#{address},#{creator},CURRENT_TIMESTAMP,#{modifier},CURRENT_TIMESTAMP,2,#{mail})")
    public void addSecStaffInfo(SecStaffDO secStaffDO) throws Exception;
    @Insert("insert into sec_user(id,staff_id,domain_id,username,password,IS_ADMIN,creator,create_time,modifier,update_time,status) values (#{id},#{staffId},#{domainId},#{username},#{password},#{isAdmin},#{creator},CURRENT_TIMESTAMP,#{modifier},CURRENT_TIMESTAMP,2)")
    public void addSecUserInfo(SecUserDO secUserDO) throws Exception;
    @Insert("insert into sec_user_role_rel(id,user_id,role_id,op_id,create_time,update_time,status,ext,ext1) values(#{secUserRoleRelDO.id},#{secUserRoleRelDO.userId},#{secUserRoleRelDO.roleId},#{secUserRoleRelDO.opId},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{status},#{secUserRoleRelDO.ext},#{secUserRoleRelDO.ext1})")
    public void addSecUserRoleRel(@Param("secUserRoleRelDO") SecUserRoleRelDO secUserRoleRelDO, @Param("status") Integer status) throws Exception;
    @Update("update sec_staff set status = #{status} where id = #{id}")
    public void updateSecStaffInfoState(@Param("id") BigDecimal id, @Param("status") Integer status) throws Exception;
    @Update("update sec_user set status = #{status} where id = #{id}")
    public void updateSecUserInfoState(@Param("id") Integer id, @Param("status") Integer status) throws Exception;
    @Update("update sec_user_role_rel set status = #{status} where id = #{id}")
    public void updateSecUserRoleRelState(@Param("id") Integer id, @Param("status") Integer status) throws Exception;
    @Select("select id from sec_user where staff_id = #{staffId} and status = #{status}")
    public Integer getSecUserIdByStaffId(@Param("staffId") BigDecimal staffId, @Param("status") Integer status) throws Exception;
    @Select("select id from sec_user_role_rel where user_id = #{userId} and status = #{status}")
    public Integer getSecUserRoleIDByUserId(@Param("userId") BigDecimal userId, @Param("status") Integer status) throws Exception;
    @Select("select role_id from sec_user_role_rel where user_id = #{userId} and status = #{status}")
    public List<Integer> getUserRoleRelRoleIDByUserId(@Param("userId") Integer userId, @Param("status") Integer status) throws Exception;
    @Select("select user_id from sec_user_role_rel where role_id = #{roleId} and status = #{status}")
    public List<Integer> getUserRoleRelUserIDByRoleId(@Param("roleId") Integer roleId, @Param("status") Integer status) throws Exception;
    @Select("select id as userId,staff_name as userName,org_id as orgId,phone_num as phoneNum,mail from sec_staff where org_id = #{orgId} and status = #{status}")
    public List<UserStaffVO> getStaffInfoByOrgId(@Param("orgId") Integer orgId, @Param("status") Integer status) throws Exception;
    @Select("select * from sec_org where org_id = #{orgId} and status = #{status}")
    public SecOrgDO getOrgInfoByOrgId(@Param("orgId") Integer orgId, @Param("status") Integer status) throws Exception;
    @Select("select id as userId,staff_name as userName,org_id as orgId,phone_num as phoneNum,mail from sec_staff where id = #{id} and status = #{status}")
    public UserStaffVO getStaffInfoById(@Param("id") Integer id, @Param("status") Integer status) throws Exception;
    @Select("select role_name from sec_role where id = #{id} and status = #{status}")
    public String getRoleNameByRoleId(@Param("id") Integer id, @Param("status") Integer status) throws Exception;
    @Select("select * from sec_user where id = #{id} and status = #{status}")
    public SecUserDO getSecUserInfoById(@Param("id") Integer id, @Param("status") Integer status) throws Exception;


    @Select("select id as userId,staff_name as userName from sec_staff where id = #{id} and status = #{status}")
    public UserStaffVO getInfoByUserId(@Param("id") BigDecimal id, @Param("status") Integer status) throws Exception;
    @Select("select staff_name as userName from sec_staff where id = #{id} and status = #{status}")
    public String  getNameByUserId(@Param("id") BigDecimal id, @Param("status") Integer status) throws Exception;


}