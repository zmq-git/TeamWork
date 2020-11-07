package com.location.service;

import com.location.bean.po.TaskItemPO;
import com.location.bean.vo.AddTaskVO;
import com.location.bean.vo.EditTaskVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Service
public interface TaskDealService {
    /**
     * 获取迭代下所有任务
     * @param iterationId
     * @return
     * @throws Exception
     */
    public Map queryTaskByIterationId(BigDecimal iterationId) throws Exception;
    public void deleteTask(BigDecimal taskId)throws Exception;
    public void editTask(EditTaskVO editTaskVO)throws Exception;
    public void addTask(AddTaskVO addTaskVO, BigDecimal creatorId)throws Exception;
    public void updateTaskState(BigDecimal taskId, Integer stateCode)throws Exception;
    public void addInterationTask(AddTaskVO addTaskVO, BigDecimal iterationId, BigDecimal userId) throws Exception;
    public void updateTaskIter(BigDecimal taskId, BigDecimal iterId)throws Exception;

    /**
     * 获取第三级任务
     * @param iterationId
     * @return
     * @throws Exception
     */
    public Map query3TaskByIterationId(BigDecimal iterationId) throws Exception;

    public List<TaskItemPO> queryTaskByUserId(Integer id)throws Exception;

    public void submitTask(BigDecimal taskId) throws Exception;
    }