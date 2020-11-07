package com.location.dao;

import com.location.bean.po.TaskItemPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface TaskExeInfoDao {
    @Select("select * from task_item where DEAL_USER_ID=#{id} and STATE = 1 and TASK_STATE =1")
    public List<TaskItemPO>  queryTaskByUserId(Integer id)throws  Exception;

    @Select("select INTERATION_ID from interation_task_rel where TASK_ID = #{taskId} and STATE = 1")
    public BigDecimal queryIterationIdByTaskId(Integer taskId)throws Exception;

    @Select("select PROJECT_ID from project_interation_rel where INTERATION_ID = #{iterationId} and STATE = 1")
    public BigDecimal queryProjectIdByIterationId(Integer iterationId)throws Exception;

    @Select("select PROJECT_NAME from project_info where PROJECT_ID = #{projectId} and STATE = 1")
    public String queryProjectNameByProjectId(Integer projectId) throws Exception;

    @Update("update task_item set TASK_STATE =2 where TASK_ID=#{TaskId}")
    public void submitTaskByTaskId(BigDecimal TaskId) throws Exception;
}