package com.location.dao;

import com.location.bean.po.InterationTaskRelPO;
import com.location.bean.vo.AddTaskVO;
import com.location.bean.vo.EditTaskVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Repository
@Mapper
public interface InterationTaskRelDao extends TeamBaseDao {
    @Select("select TASK_ID from INTERATION_TASK_REL where INTERATION_ID = #{interationId} and STATE =#{status}")
    public List<BigDecimal> getTaskIdByIterationId(@Param(value = "interationId") BigDecimal interationId, @Param(value = "status") Integer status) throws Exception;

    @Select("select " +
            "TASK_ID as taskId," +
            "TASK_NAME as taskName," +
            "TASK_DESC as taskDesc," +
            "TASK_PRIORITY as taskPriority," +
            "CREATER as taskCreater," +
            "DEAL_USER_ID as dealUser," +
            "STATE as state," +
            "TASK_STATE as taskState," +
            "TASK_TRIP as taskClass," +
            "CREATE_DATE as createDate," +
            "START_DATE as startDate," +
            "EDN_DATE as endDate " +
            " from TASK_ITEM where TASK_ID = #{taskId} and STATE =#{status}")
    public Map getTaskInfo(@Param(value = "taskId") BigDecimal taskId, @Param(value = "status") Integer status) throws Exception;

    @Update("update TASK_ITEM set DONE_DATE = CURRENT_TIMESTAMP,STATE=0,DONE_CODE=#{doneCode} where TASK_ID = #{id}")
    public void deleteTaskItem(@Param(value = "id") BigDecimal id, @Param(value = "doneCode") BigDecimal doneCode)throws Exception;

    @Update("<script>update TASK_ITEM set DONE_DATE = CURRENT_TIMESTAMP,<if test='state!=null'>STATE=#{state},</if> "+
            " <if test='taskName!=null'>TASK_NAME = #{taskName},</if> <if test='dealUser!=null'>DEAL_USER_ID = #{dealUser},</if><if test='taskDesc!=null'>TASK_DESC=#{taskDesc},</if> <if test='taskPriority!=null'>TASK_PRIORITY=#{taskPriority},</if>"+
            " <if test='taskName!=null'>TASK_FILE = #{taskFile},</if> <if test='creater!=null'>CREATER = #{creater},</if> <if test='taskTrip!=null'>TASK_TRIP=#{taskTrip},</if> <if test='createDate!=null'>CREATE_DATE=#{createDate},</if> <if test='startDate!=null'>START_DATE=#{startDate},</if> <if test='endDate!=null'>EDN_DATE=#{endDate},</if> DONE_CODE=#{doneCode}, TASK_STATE = #{taskState} where TASK_ID = #{id}</script>")
    public void editTask(EditTaskVO editTaskVO)throws Exception;

    @Insert("insert into TASK_ITEM(TASK_ID," +
            "TASK_NAME," +
            "TASK_DESC," +
            "TASK_PRIORITY," +
            "CREATER," +
            "REL_TASK_ID," +
            "STATE," +
            "TASK_TRIP," +
            "TASK_FILE," +
            "DEAL_USER_ID," +
            "DONE_CODE," +
            "CREATE_DATE," +
            "DONE_DATE," +
            "START_DATE,EDN_DATE,TASK_STATE) values (#{id},#{taskName},#{taskDesc},#{taskPriority},#{creater},#{parentId},#{state}," +
            "#{taskTrip},#{taskFile},#{dealUser},#{doneCode}," +
            "CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{startDate,jdbcType=TIMESTAMP},#{endDate,jdbcType=TIMESTAMP},#{taskState})")
    public int addTask(AddTaskVO addTaskVO)throws Exception;

    @Select("select NEXTVAL('task_id_sequence')")
    public BigDecimal getTaskId()throws Exception;

    @Select("select TASK_ID from TASK_ITEM where REL_TASK_ID = #{taskId} and STATE =#{status}")
    public List<BigDecimal> getTaskChildIdByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "status") Integer status) throws Exception;


    @Select("select TASK_STATE as taskState from TASK_ITEM where REL_TASK_ID = #{taskId} and STATE =#{status}")
    public List<Integer> getTaskChildIdStateByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "status") Integer status) throws Exception;

    @Select("select TASK_STATE as taskState from TASK_ITEM where TASK_ID = #{taskId} and STATE =#{status}")
    public Integer getTaskStateByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "status") Integer status) throws Exception;

    @Select("select REL_TASK_ID from TASK_ITEM where TASK_ID = #{taskId} and STATE =#{status}")
    public BigDecimal getParentTaskIdByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "status") Integer status) throws Exception;


    @Update("update TASK_ITEM set DONE_DATE = CURRENT_TIMESTAMP,TASK_STATE=#{stateCode},DONE_CODE=#{doneCode} where TASK_ID = #{id}")
    public void updateTaskState(@Param(value = "id") BigDecimal id, @Param(value = "stateCode") Integer stateCode, @Param(value = "doneCode") BigDecimal doneCode)throws Exception;

    @Select("select NEXTVAL('inter_task_rel_seq')")
    public BigDecimal getInterationTaskRelId() throws Exception;


    @Insert("insert into interation_task_rel(REL_ID,INTERATION_ID,TASK_ID,CREATER,DONE_CODE,STATE,CREATE_DATE,DONE_DATE,EFF_DATE,EXP_DATE) " +
            "values(#{relId},#{interationId},#{taskId},#{creater},#{doneCode},#{state},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,#{effDate,jdbcType=TIMESTAMP},#{expDate,jdbcType=TIMESTAMP})")
    public void addInterationTask(InterationTaskRelPO interationTaskRelPO) throws Exception;

    @Select("<script>select " +
            "TASK_ID as taskId," +
            "TASK_NAME as taskName," +
            "TASK_DESC as taskDesc," +
            "TASK_PRIORITY as taskPriority," +
            "CREATER as taskCreater," +
            "DEAL_USER_ID as dealUser," +
            "STATE as state," +
            "TASK_STATE as taskState," +
            "TASK_TRIP as taskClass," +
            "CREATE_DATE as createDate," +
            "START_DATE as startDate," +
            "EDN_DATE as endDate " +
            "  from TASK_ITEM where TASK_ID = #{taskId} <if test ='dealUserId!=null'>and DEAL_USER_ID = #{dealUserId}</if> and  STATE =#{status}</script>")
    public Map getTaskInfoByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "dealUserId") BigDecimal dealUserId, @Param(value = "status") Integer status) throws Exception;

    @Select("select task_id from interation_task_rel where INTERATION_ID = #{interationId} and state = #{status}")
    public List<BigDecimal> getTaskIdByInteIdAndCreater(@Param(value = "interationId") BigDecimal interationId, @Param(value = "status") Integer status) throws Exception;

    /**
     * 修改任务与迭代挂靠关系
     * @param taskId
     * @param iterId
     * @param doneCode
     * @throws Exception
     */
    @Update("update INTERATION_TASK_REL set DONE_DATE = CURRENT_TIMESTAMP,INTERATION_ID=#{iterId},DONE_CODE=#{doneCode} where TASK_ID = #{taskId} and STATE =1")
    public void updateTaskIter(@Param(value = "taskId") BigDecimal taskId, @Param(value = "iterId") BigDecimal iterId, @Param(value = "doneCode") BigDecimal doneCode)throws Exception;

    @Select("select TASK_ID from TASK_ITEM where TASK_TRIP = #{classId} and STATE =#{status}")
    public List<BigDecimal> getTaskIdByClassId(@Param(value = "classId") BigDecimal classId, @Param(value = "status") Integer status) throws Exception;

    @Select("select TASK_TRIP from TASK_ITEM where TASK_ID = #{taskId} and state = #{state}")
    public BigDecimal getTaskTripByTaskId(@Param(value = "taskId") BigDecimal taskId, @Param(value = "state") Integer state) throws Exception;

    @Update("update INTERATION_TASK_REL set DONE_DATE = CURRENT_TIMESTAMP,DONE_CODE=#{doneCode},state = 0 where TASK_ID = #{taskId} and STATE =1")
    public void updateTaskIterState(@Param(value = "taskId") BigDecimal taskId, @Param(value = "doneCode") BigDecimal doneCode)throws Exception;

    @Select("select DEAL_USER_ID from TASK_ITEM where TASK_ID = #{id}")
    public BigDecimal getTaskDelUserByTaskId(BigDecimal id) throws  Exception;
}