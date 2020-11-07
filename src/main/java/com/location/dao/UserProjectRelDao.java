package com.location.dao;

import com.location.bean.po.UserProjectRelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/12/10
 * @Version V1.0
 **/
@Repository
@Mapper
public interface UserProjectRelDao  {
    @Select("select user_id from user_project_rel where project_id = #{projectId} and state = #{state}")
    public Set<Integer> getUserIdByProjectId(@Param("projectId") Integer projectId, @Param("state") Integer state) throws Exception;;
    @Select("select * from user_project_rel where project_id = #{projectId} and user_id = #{userId} and state = #{state}")
    public List<UserProjectRelPO> getUserProRelInfoByUserId(@Param("userId") Integer userId, @Param("projectId") Integer projectId, @Param("state") Integer state) throws Exception;
    @Update("update user_project_rel set state = 0 where project_id = #{projectId} and user_id = #{userId} and state = #{state}")
    public void deleteUserProRelInfo(@Param("projectId") Integer projectId, @Param(value = "userId") Integer userId, @Param("state") Integer state) throws Exception;
    @Update("update user_project_rel set state = 0 where project_id = #{projectId} and state = #{state}")
    public void deleteProjectAllUserInfo(@Param("projectId") BigDecimal projectId, @Param("state") Integer state) throws Exception;
    @Select("select project_id from user_project_rel where user_id = #{userId} and state = #{state}")
    public List<Integer> getProjectIdByUserId(@Param("userId") Integer userId, @Param("state") Integer state) throws Exception;

    @Select("select PROJECT_ROLE from user_project_rel where user_id = #{userId} and project_id = #{projectId}  and state =1")
    public Integer queryProjectRole(@Param("projectId")BigDecimal projectId,@Param("userId")BigDecimal userId)throws  Exception;
}