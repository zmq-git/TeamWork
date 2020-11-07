package com.location.dao;

import com.location.bean.po.InterationItemPO;
import com.location.bean.po.ProjectInterationRelPO;
import com.location.bean.vo.InterationInfoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/19
 * @Version V1.0
 **/
@Repository
@Mapper
public interface ProjectIterationRelDao extends TeamBaseDao{
    @Select("select INTERATION_ID from PROJECT_INTERATION_REL where PROJECT_ID = #{projectId} and STATE =#{state}")
    public List<BigDecimal> getIterIdByProjectId(@Param(value = "projectId") BigDecimal projectId, @Param(value = "state") Integer state) throws Exception;

    @Select("select INTERATION_ID as interationId,INTERATION_PERIOD as interationPeriod,INTERATION_TYPE as interationType,USER_NAME as userName,PROJECT_ID as projectId from PROJECT_INTERATION_REL where PROJECT_ID in (select PROJECT_ID from USER_PROJECT_REL where user_id =  #{userId}) and state =1")
    public List<InterationInfoVO> getIterInfoByUserId(@Param(value = "userId") BigDecimal userId, @Param(value = "state") Integer state) throws Exception;

    @Select("SELECT item.*,projectrel.* FROM interation_item item,project_interation_rel projectrel WHERE item.INTERATION_ID = projectrel.INTERATION_ID  and state = #{state} and item.INTERATION_ID in (select INTERATION_ID from project_interation_rel where project_id = #{projectId})")
    public List<InterationInfoVO> getIterInfoByProjectId(@Param(value = "projectId") BigDecimal projectId, @Param(value = "state") Integer state) throws Exception;

    @Select("select * from interation_item where interation_name = #{interationName}")
    public InterationItemPO getInterInfo(String interationName) throws Exception;

    @Select("select INTERATION_PERIOD from project_interation_rel where INTERATION_ID = #{interationId} and state = #{state}")
    public Integer getIntePeriodByInteId(@Param("interationId") BigDecimal interationId, @Param("state") Integer state) throws Exception;

    @Select("select INTERATION_NAME from interation_item where INTERATION_ID = #{interationId}")
    public List<String> getInteNameByInteId(@Param("interationId") BigDecimal interationId) throws Exception;

    @Select("select task_id from interation_task_rel where interation_id = #{interationId} and state = #{state}")
    public List<BigDecimal> getTaskIdByInterationId(@Param("interationId") BigDecimal interationId, @Param("state") Integer state) throws Exception;

    @Select("select task_state from task_item where task_id = #{taskId} and state = #{state}")
    public Integer findStateByTaskId(@Param("taskId") BigDecimal taskId, @Param("state") Integer state) throws Exception;

    @Select("select count(*) from interation_task_rel where INTERATION_ID = #{interationId} and state = #{state}")
    public int findTaskNum(@Param("interationId") BigDecimal interationId, @Param("state") Integer state) throws Exception;

    @Select("select count(*) from task_item where task_state = #{taskState} and state = #{state} and task_id in (select task_id from interation_task_rel where INTERATION_ID = #{interationId} and state = #{state})")
    public int findPerformTaskNum(@Param("interationId") BigDecimal interationId, @Param("taskState") Integer taskState, @Param("state") Integer state) throws Exception;

    @Update("update project_interation_rel set interation_period = #{interationPeriod} where state = #{state} and interation_id = #{interationId}")
    public void updateIterationPeriod(@Param("interationPeriod") Integer interationPeriod, @Param("interationId") BigDecimal interationId, @Param("state") Integer state) throws Exception;

    @Insert("insert into PROJECT_INTERATION_REL(REL_ID,PROJECT_ID," +
            "INTERATION_ID," +
            "USER_NAME," +
            "INTERATION_PERIOD," +
            "STATE," +
            "INTERATION_TYPE," +
            "DONE_CODE," +
            "CREATE_DATE," +
            "DONE_DATE," +
            "EFF_DATE," +
            "EXP_DATE" +
            " ) values (#{relId},#{projectId},#{interationId},#{userName},#{interationPeriod},#{state},#{interationType}," +
            "#{doneCode},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{startDate,jdbcType=TIMESTAMP}," +
            "#{endDate,jdbcType=TIMESTAMP})")
    public int addProjectInterationRel(ProjectInterationRelPO projectInterationRelPO)throws Exception;

    @Select("select NEXTVAL('project_iter_rel_seq')")
    public BigDecimal getNewRelId()throws Exception;

    //获取迭代分类id
    @Select("select INTERATION_TYPE from PROJECT_INTERATION_REL where state = #{state} and interation_id = #{interationId}")
    public BigDecimal getInterationTypeById(@Param("state") Integer state, @Param("interationId") BigDecimal interationId) throws Exception;

    @Select("select project_id from PROJECT_INTERATION_REL where state = #{state} and interation_id = #{interationId}")
    public BigDecimal getProjectIdByInterationId(@Param("state") Integer state, @Param("interationId") BigDecimal interationId)throws Exception;
}