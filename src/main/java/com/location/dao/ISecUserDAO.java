package com.location.dao;

import com.location.bean.vo.SecStaffDO;
import com.location.bean.vo.SecUserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ISecUserDAO {

    @Select("select id  from sec_user where staff_id = #{staffId} and status=#{status}")
    public long getUserIdByStaffId(@Param("staffId") long staffId, @Param("status") int status);

    @Select("select username from sec_user where id = #{userId}")
    public String getSecUserNameById(@Param("userId") long userId);

    @Select("select username from sec_user where user_id = 1")
    public String getUserName();

/*    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name")
    })*/
    @Select("select * from sec_user where id = 1")
    public List<SecUserDO> getSecUser();


    public SecUserDO getSecUserByName(String userName);
    @Select("select * from sec_user where username=#{username} and password=#{password} and status=#{status}")
    public SecUserDO getSecUserByUsernameAndPasswordAndStatus(
            @Param("username") String username, @Param("password") String password, @Param("status") byte status);

    @Select("select * from sec_staff where id= #{id}")
    public SecStaffDO getSecStaffById(@Param(value = "id") long id)throws Exception;


    @Select("select b.role_id from sec_user a,sec_user_role_rel b where a.id =b.user_id and username = #{username}")
    public List<Long> getRoleIdsByUserName(@Param("username") String username);

    @Update("update sec_user set password=#{password} where username=#{username}")
    public void modifyPassword(@Param("username") String username, @Param("password") String password);

    @Select("select a.* from sec_user b,sec_staff a where b.username = #{username} and a.id = b.staff_id")
    public List<SecStaffDO> queryStaffId(@Param("username") String username);
    @Update("<script>update sec_staff set <if test='staffName!=null'>staff_name = #{staffName},</if><if test='portraitAddress!=null'>portrait_address = #{portraitAddress},</if>" +
            "<if test='mail!=null'>mail = #{mail},</if><if test='phoneNum!=null'>phone_num = #{phoneNum}</if> where id = #{id}</script>")
    public void modifyStaffName(@Param("staffName") String staffName, @Param("portraitAddress") String portraitAddress, @Param("id") Integer id, @Param("mail") String mail, @Param("phoneNum") String phoneNum);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sec_staff(staff_name,org_id,creator,create_time,update_time,status)values(" +
            "#{staffName}," +
            "#{orgId}," +
            "#{creator}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status})")
    public void saveSecStaff(SecStaffDO secStaffDO)throws Exception;

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into sec_user(staff_id,domain_id,username,password,is_admin,creator,create_time,update_time,status)values(" +
            "#{staffId}," +
            "#{domainId}," +
            "#{username}," +
            "#{password}," +
            "#{isAdmin}," +
            "#{creator}," +
            "#{createTime,jdbcType=TIMESTAMP}," +
            "#{updateTime,jdbcType=TIMESTAMP}," +
            "#{status})")
    public void saveSecUser(SecUserDO secUserDO)throws Exception;

    @Select("select username from sec_user where username like concat(#{username},'%') order by create_time desc limit 1")
    public String getLastUserNameByUserName(@Param(value = "username") String userName)throws Exception;

    @Update("update sec_user set update_time = CURRENT_TIMESTAMP,status = 0 where id = #{id}")
    public void delSecUser(@Param(value = "id") int id)throws Exception;

    @Update("update sec_user set update_time = CURRENT_TIMESTAMP,status = 0 where staff_id = #{staffId}")
    public void delSecUserInfo(@Param(value = "staffId") int staffId)throws Exception;

    @Update("update sec_user set update_time = CURRENT_TIMESTAMP,status = 0 where staff_id = #{staffId}")
    public void delSecUserByStaffId(@Param(value = "staffId") int staffId)throws Exception;

    @Update("update sec_user set update_time = CURRENT_TIMESTAMP,status = #{status} where id = #{id}")
    public void updateSecUser(@Param(value = "id") int id, @Param(value = "status") int status)throws Exception;

    @Update("update sec_user set update_time = CURRENT_TIMESTAMP,status = #{status} where staff_id = #{staffId}")
    public void updateSecUserInfo(@Param(value = "staffId") int staffId, @Param(value = "status") int status)throws Exception;

    @Select("select staff_id as staffId from sec_user where id = #{id}")
    public Integer getStaffIdByUserId(@Param(value = "id") long id)throws Exception;
}
