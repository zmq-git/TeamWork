package com.location.dao;

import com.location.bean.vo.SecStaffDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ISecStaffDAO {
    @Select("select * from sec_Staff where id = #{StaffId}")
    public SecStaffDO getSecStaffById(@Param("StaffId") int StaffId);


    @Select("<script>select a.id,b.username,a.staff_name as staffName,a.org_id as orgId,a.phone_num as phoneNum,a.mail as mail from sec_Staff a,sec_user b where a.org_id=#{orgId} and a.id = b.staff_id and b.status = #{status} " +
            "<if test='username!=null'>and b.username like concat('%',#{username},'%')</if> order by b.create_time desc</script>")
    public List<Map> getUsernameByOrgId(@Param(value = "orgId") long orgId, @Param(value = "status") int status, @Param(value = "username") String username)throws Exception;

    @Select("<script>select b.id,b.username,a.org_id as orgId from sec_Staff a,sec_user b where a.id = b.staff_id and b.status = #{status} <if test='username!=null'>and b.username like concat('%',#{username},'%')</if> order by b.create_time desc</script>")
    public List<Map> getUserInfoByUsername(@Param(value = "username") String username, @Param(value = "status") int status)throws Exception;

    @Update("<script>update sec_staff set update_time = CURRENT_TIMESTAMP " +
            "<if test = 'staffName!=null'>,staff_name = #{staffName}</if>" +
            "<if test = 'portraitAddress!=null'>,portrait_address = #{portraitAddress}</if>" +
            "<if test = 'phoneNum!=null'>,phone_num = #{phoneNum}</if>" +
            "<if test = 'address!=null'>,address = #{address}</if>" +
            "<if test = 'orgId!=null'>,org_id = #{orgId}</if>" +
            "<if test = 'modifier!=null'>,modifier = #{modifier}</if>" +
            "<if test = 'mail!=null'>,mail = #{mail}</if>" +
            "<if test = 'status!=null'>,status = #{status}</if> where id = #{id}</script>")
    public void updateSecStaffById(SecStaffDO secStaffDO)throws Exception;

    @Select("select id from sec_staff where org_id = #{orgId}")
    public List<Integer> getSecStaffByOrgId(long orgId)throws Exception;
    @Update("update sec_staff set update_time = CURRENT_TIMESTAMP,status = 0 where org_id = #{orgId}")
    public void delSecStaffByOrgId(long orgId)throws Exception;
}
