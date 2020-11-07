package com.location.dao;

import com.location.bean.po.ProjectInfoPO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Repository
@Mapper
public interface ProjectInfoDao extends TeamBaseDao{
    @Insert("insert into project_info (PROJECT_ID,PROJECT_NAME,PROJECT_DETAIL,PROJECT_PICTURE,PROJECT_PERIOD,STATE,PROJECT_TYPE,DONE_CODE,CREATER,USER_ID,CREATE_DATE,DONE_DATE,START_DATE,END_DATE) values(#{projectId},#{projectName},#{projectDetail},#{projectPicture},#{projectPeriod},#{state},#{projectType},#{doneCode},#{creater},#{userId},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{startDate},#{endDate})")
    public void addNewProject(ProjectInfoPO projectInfoPO) throws Exception;
    @Select("select * from project_info where PROJECT_NAME = #{projectName} and state = #{status}")
    public ProjectInfoPO getProjectInfo(@Param(value = "projectName") String projectName, @Param("status") Integer status)throws Exception;
    @Select("select * from project_info  where user_id = #{userId} and state = #{status} and PROJECT_NAME like CONCAT('%', #{projectName} ,'%')")
    public List<ProjectInfoPO> getProjectInfobyProjectName(@Param(value = "projectName") String projectName, @Param("userId") BigDecimal userId, @Param(value = "status") Integer status)throws Exception;
    @Select("select NEXTVAL('project_id_sequence')")
    public BigDecimal getProjectCode() throws Exception;
    @Select("<script>select * from project_info where <if test='projectPeriod!=null'>PROJECT_PERIOD = #{projectPeriod,jdbcType=INTEGER} and</if> state = #{status} and project_id  in (select project_id from user_project_rel where user_id = #{userId} and state = 1) limit #{current},#{pageSize} </script>")
    public List<ProjectInfoPO> getAllProject(@Param(value = "pageSize") Integer pageSize, @Param(value = "current") Integer current, @Param("userId") BigDecimal userId, @Param(value = "status") Integer status, @Param("projectPeriod") Integer projectPeriod) throws Exception;
    @Update("update project_info set state = 0,DONE_DATE = CURRENT_TIMESTAMP,DONE_CODE=#{doneCode} where PROJECT_ID = #{projectId}")
    public void deleteProjectInfo(@Param("projectId") BigDecimal projectId, @Param(value = "doneCode") BigDecimal doneCode) throws Exception;
    @Update("update project_info set DONE_DATE = CURRENT_TIMESTAMP,PROJECT_DETAIL = #{projectInfoPO.projectDetail},START_DATE = #{projectInfoPO.startDate},END_DATE = #{projectInfoPO.endDate} where PROJECT_ID = #{projectInfoPO.projectId} and state = #{status}")
    public void updateProjectInfo(@Param("projectInfoPO") ProjectInfoPO projectInfoPO, @Param(value = "status") Integer status) throws Exception;
    @Select("<script>select count(*) from project_info where state = #{state} and <if test='projectPeriod!=null'>PROJECT_PERIOD = #{projectPeriod,jdbcType=INTEGER} and</if> PROJECT_ID in (select  project_id from user_project_rel where user_id = #{userId} and state = #{state})</script>")
    public Integer getProjectNumber(@Param("userId") Integer userId, @Param(value = "state") Integer status, @Param("projectPeriod") Integer projectPeriod) throws Exception;
    @Select("select pro.* from user_project_rel userPro,project_info pro where userPro.project_id = pro.project_id and userPro.user_id = #{userId} and pro.project_name = #{projectName}")
    public ProjectInfoPO getProjectInfoByPreciseName(@Param(value = "projectName") String projectName, @Param("userId") BigDecimal userId, @Param("status") Integer status) throws Exception;
    @Select("select pro.* from user_project_rel userPro,project_info pro where userPro.project_id = pro.project_id and userPro.user_id = #{userId} and pro.project_name like CONCAT('%', #{projectName} ,'%')")
    public List<ProjectInfoPO> getProjectInfoByVagueName(@Param(value = "projectName") String projectName, @Param("userId") BigDecimal userId, @Param("status") Integer status) throws Exception;
    @Select("select * from project_info where PROJECT_ID = #{projectId} and state = #{status}")
    public ProjectInfoPO getProjectInfoByProjectId(@Param(value = "projectId") Integer projectId, @Param("status") Integer status)throws Exception;
    @Update("update project_info set PROJECT_PERIOD = #{projectPeriod} where PROJECT_ID = #{projectId}")
    public void updateProjectPeriod(@Param("projectId") Integer projectId, @Param("projectPeriod") Integer projectPeriod) throws Exception;
}
