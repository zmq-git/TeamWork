package com.location.service.impl;

import com.location.dao.ISecStaffDAO;
import com.location.bean.po.InterationTaskRelPO;
import com.location.bean.po.TaskItemPO;
import com.location.bean.vo.AddNotifyVO;
import com.location.bean.vo.AddTaskVO;
import com.location.bean.vo.EditTaskVO;
import com.location.bean.vo.TemplateInfoVO;
import com.location.common.constants.TeamConst;
import com.location.dao.*;
import com.location.service.InfoNotifyService;
import com.location.service.ItemRelatService;
import com.location.service.TaskDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TaskDealServiceImpl
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Service
public class TaskDealServiceImpl implements TaskDealService{
    @Autowired
    InterationTaskRelDao iterationTaskRelDao;

    @Autowired
    ProjectIterationRelDao projectIterationRelDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ItemRelatService itemRelatService;

    @Autowired
    ISecStaffDAO iSecStaffDAO;

    @Autowired
    InfoNotifyService infoNotifyService;

    @Autowired
    ItemRelatDao itemRelatDao;

    @Autowired
    TaskExeInfoDao taskExeInfoDao;

    @Override
    public Map queryTaskByIterationId(BigDecimal iterationId) throws Exception{
        Map retMap = new HashMap();
        List list = new ArrayList();
        retMap.put("result",list);
        List<BigDecimal> taskIds = iterationTaskRelDao.getTaskIdByIterationId(iterationId,1);
        if(taskIds!=null&&taskIds.size()>0){
            for(BigDecimal id : taskIds){
                Map map =iterationTaskRelDao.getTaskInfo(id,1);
                if(map ==null){
                    continue;
                }
                if(map.get("dealUser")!=null){
                    String userName = userInfoDao.getNameByUserId((BigDecimal) map.get("dealUser"),1);
                    map.put("dealUserName",userName);
                }
                if(map.get("taskCreater")!=null){
                    String userName = userInfoDao.getNameByUserId((BigDecimal) map.get("taskCreater"),1);
                    map.put("createrUserName",userName);
                }
                BigDecimal taskClass = (BigDecimal)map.get("taskClass");
                Integer taskState = (Integer)map.get("taskState");
                if(taskClass!=null){
                    String taskClassName = itemRelatService.getTaskClassificationById(taskClass)==null?null:(String)itemRelatService.getTaskClassificationById(taskClass).get("className");
                    map.put("taskClassName",taskClassName);
                }
                if(taskState!=null&&taskState ==0){
                    map.put("taskStateName","未开始");
                }else if(taskState!=null&&taskState ==1){
                    map.put("taskStateName","开发中");
                }else if(taskState!=null&&taskState ==2){
                    map.put("taskStateName","已完成");
                }
                if(map!=null){
                    list.add(map);
                    List childList = getChildren(id);
                    map.put("children",childList);
                }

            }

        }
        return retMap;
    }

    /**
     * 只获取第三层任务
     * @param iterationId
     * @return
     * @throws Exception
     */
    public Map query3TaskByIterationId(BigDecimal iterationId) throws Exception{
        Map retMap = new HashMap();
        List list = new ArrayList();
        retMap.put("result",list);
        //第一级任务id
        List<BigDecimal> taskIds = iterationTaskRelDao.getTaskIdByIterationId(iterationId,1);
        if(taskIds!=null&&taskIds.size()>0){
            for(BigDecimal id : taskIds){
                //第二级任务id
                List<BigDecimal> Ids = iterationTaskRelDao.getTaskChildIdByTaskId(id,1);
                if(Ids!=null&&Ids.size()>0){
                    for (BigDecimal id2:Ids){
                        //第三级任务id
                        List<BigDecimal> id3s = iterationTaskRelDao.getTaskChildIdByTaskId(id2,1);
                        if(id3s!=null&&id3s.size()>0){
                            for(BigDecimal id3:id3s){
                                Map map =iterationTaskRelDao.getTaskInfo(id3,1);
                                if(map ==null){
                                    continue;
                                }
                                if(map.get("dealUser")!=null){
                                    String userName = userInfoDao.getNameByUserId((BigDecimal) map.get("dealUser"),1);
                                    map.put("dealUserName",userName);
                                }
                                if(map.get("taskCreater")!=null){
                                    String userName = userInfoDao.getNameByUserId((BigDecimal) map.get("taskCreater"),1);
                                    map.put("createrUserName",userName);
                                }
                                BigDecimal taskClass = (BigDecimal)map.get("taskClass");
                                Integer taskState = (Integer)map.get("taskState");
                                if(taskClass!=null){
                                    String taskClassName = itemRelatService.getTaskClassificationById(taskClass)==null?null:(String)itemRelatService.getTaskClassificationById(taskClass).get("className");
                                    map.put("taskClassName",taskClassName);
                                }
                                if(taskState!=null&&taskClass!=null){
                                    String taskStateName = itemRelatService.getStateNameByStateCode(taskClass,taskState);
                                    map.put("taskStateName",taskStateName);
                                }
                                list.add(map);
                            }
                            }
                    }
                }
            }
        }
        return retMap;
    }

    @Override
    public List<TaskItemPO> queryTaskByUserId(Integer id) throws Exception {
        List<TaskItemPO> list = taskExeInfoDao.queryTaskByUserId(id);
        System.out.println("list：---------------     "+list);
        for(int i = 0;i<list.size();i++){
            TaskItemPO taskItemPO  =  list.get(i);
            BigDecimal taskId = (BigDecimal) taskItemPO.getTaskId();
            BigDecimal iterationId = taskExeInfoDao.queryIterationIdByTaskId(taskId.intValue());
            if(iterationId == null){
               list.remove(list.get(i));
            }
            else {
                BigDecimal projectId = taskExeInfoDao.queryProjectIdByIterationId(iterationId.intValue());
                String projectName = taskExeInfoDao.queryProjectNameByProjectId(projectId.intValue());
                taskItemPO.setProjectName(projectName);
            }

        }
        return list;
    }

    @Override
    public void submitTask(BigDecimal taskId) throws Exception {
        taskExeInfoDao.submitTaskByTaskId(taskId);
    }

    /**
     * 根据任务id删除任务
     * @param taskId
     * @throws Exception
     */
    public void deleteTask(BigDecimal taskId)throws Exception{
        checkTaskState(taskId,null);
        BigDecimal done_code = iterationTaskRelDao.getDoneCode();
        iterationTaskRelDao.deleteTaskItem(taskId,done_code);

        //删除迭代下关联的任务
        BigDecimal parentTaskIdByTaskId = iterationTaskRelDao.getParentTaskIdByTaskId(taskId, 1);
        if (parentTaskIdByTaskId == null){
            iterationTaskRelDao.updateTaskIterState(taskId,done_code);
        }
    }

    /**
     * 修改任务
     * @param editTaskVO
     * @throws Exception
     */
    @Override
    public void editTask(EditTaskVO editTaskVO)throws Exception{
        BigDecimal done_code = iterationTaskRelDao.getDoneCode();
        BigDecimal delUserId = iterationTaskRelDao.getTaskDelUserByTaskId(editTaskVO.getId());
        editTaskVO.setDoneCode(done_code);
        if(editTaskVO.getDealUser() == null){
            editTaskVO.setTaskState(0);
        }else if(editTaskVO.getDealUser() != null){
            editTaskVO.setTaskState(1);
            if(delUserId == null){
                AddNotifyVO addNotifyVO = new AddNotifyVO();
                addNotifyVO.setApplicant(Integer.parseInt(editTaskVO.getCreater().toString()));
                addNotifyVO.setAuditor(Integer.parseInt(editTaskVO.getDealUser().toString()));
                addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_TASK);
                addNotifyVO.setTaskName(editTaskVO.getTaskName());
                addNotifyVO.setProjectId(Integer.parseInt(projectIterationRelDao.getProjectIdByInterationId(1, editTaskVO.getInterationId()).toString()));
                infoNotifyService.addNotifyInfo(addNotifyVO);
            }else if(delUserId != null && delUserId!= editTaskVO.getDealUser()){
                AddNotifyVO addNotifyVO = new AddNotifyVO();
                addNotifyVO.setApplicant(Integer.parseInt(editTaskVO.getCreater().toString()));
                addNotifyVO.setAuditor(Integer.parseInt(editTaskVO.getDealUser().toString()));
                addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_TASK);
                addNotifyVO.setTaskName(editTaskVO.getTaskName());
                addNotifyVO.setProjectId(Integer.parseInt(projectIterationRelDao.getProjectIdByInterationId(1, editTaskVO.getInterationId()).toString()));
                infoNotifyService.addNotifyInfo(addNotifyVO);
            }
        }


        iterationTaskRelDao.editTask(editTaskVO);
    }

    /**
     * 新增任务
     * @param addTaskVO,creatorId
     * @throws Exception
     */
    @Override
    public void addTask(AddTaskVO addTaskVO, BigDecimal creatorId)throws Exception{
        BigDecimal done_code = iterationTaskRelDao.getDoneCode();
        addTaskVO.setDoneCode(done_code);
        addTaskVO.setCreater(creatorId);
        addTaskVO.setDealUser(addTaskVO.getDealUser());
        addTaskVO.setState(1);//初始状态为1：生效状态
        BigDecimal taskId = iterationTaskRelDao.getTaskId();
        addTaskVO.setId(taskId);
        //分类是否已经挂靠模板校验，如果没挂靠不允许创建
        TemplateInfoVO templateInfoVO =itemRelatService.queryTemplateByclassificationId(addTaskVO.getTaskTrip());
        if(templateInfoVO ==null){
            throw new Exception("选取的任务分类: "+addTaskVO.getTaskTrip()+" 未关联模板，不允许使用当前分类");
        }
        addTaskVO.setTaskTrip(addTaskVO.getTaskTrip());
        //BigDecimal taskTrip = projectIterationRelDao.getInterationTypeById(1,addTaskVO.getInterationId());
        //addTaskVO.setTaskTrip(taskTrip);
        //默认任务初始状态为0：未开始状态
        if(addTaskVO.getDealUser() == null){
            addTaskVO.setTaskState(0);
        }else if(addTaskVO.getDealUser() != null){
            addTaskVO.setTaskState(1);
        }
        iterationTaskRelDao.addTask(addTaskVO);

        //新增任务分配消息
        if(addTaskVO.getDealUser() != null) {
            AddNotifyVO addNotifyVO = new AddNotifyVO();
            addNotifyVO.setApplicant(Integer.parseInt(creatorId.toString()));
            addNotifyVO.setAuditor(Integer.parseInt(addTaskVO.getDealUser().toString()));
            addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_TASK);
            addNotifyVO.setTaskName(addTaskVO.getTaskName());
            addNotifyVO.setProjectId(Integer.parseInt(projectIterationRelDao.getProjectIdByInterationId(1, addTaskVO.getInterationId()).toString()));
            infoNotifyService.addNotifyInfo(addNotifyVO);
        }
    }

    /**
     * 根据任务id修改任务状态
     * @param taskId
     * @throws Exception
     */
    @Override
    public void updateTaskState(BigDecimal taskId,Integer stateCode)throws Exception{
        Integer taskState  = iterationTaskRelDao.getTaskStateByTaskId(taskId,1);
        BigDecimal tasktrip = iterationTaskRelDao.getTaskTripByTaskId(taskId,1);
        TemplateInfoVO templateInfoVO =itemRelatService.queryTemplateByclassificationId(tasktrip);
        String torsionRelation = itemRelatDao.getTorsionRelation(templateInfoVO.getId(),1, TeamConst.TYPE_NAME,taskState);
        if(torsionRelation==null||torsionRelation.length()<=0){
            throw new Exception("当前状态不可变更");
        }else {
            String[] str = torsionRelation.split(",");
            for(String relation:str){
                if(Integer.valueOf(relation)==stateCode){
                    BigDecimal done_code = iterationTaskRelDao.getDoneCode();
                    iterationTaskRelDao.updateTaskState(taskId,stateCode,done_code);
                    checkTaskParentState(taskId,stateCode,done_code);
                    return;
                }
            }
            throw new Exception("当前任务节点不支持修改到此节点");
        }
        //checkTaskState(taskId,stateCode);

    }



    /**
     * 在迭代下新增任务
     * @param addTaskVO,iterationId
     * @throws Exception
     */
    @Override
    public void addInterationTask(AddTaskVO addTaskVO, BigDecimal iterationId,BigDecimal userId) throws Exception {
        addTaskVO.setCreater(userId);
        BigDecimal doneCode = iterationTaskRelDao.getDoneCode();
        InterationTaskRelPO interationTaskRelPO = new InterationTaskRelPO();
        BigDecimal interationTaskRelId = iterationTaskRelDao.getInterationTaskRelId();
        BigDecimal taskId = iterationTaskRelDao.getTaskId();
        interationTaskRelPO.setRelId(interationTaskRelId);
        interationTaskRelPO.setInterationId(iterationId);
        interationTaskRelPO.setTaskId(taskId);
        interationTaskRelPO.setCreater(addTaskVO.getCreater());
        interationTaskRelPO.setState(1);
        interationTaskRelPO.setCreateDate(addTaskVO.getCreateDate());
        interationTaskRelPO.setEffDate(addTaskVO.getStartDate());
        interationTaskRelPO.setExpDate(addTaskVO.getEndDate());
        interationTaskRelPO.setDoneCode(doneCode);
        iterationTaskRelDao.addInterationTask(interationTaskRelPO);
//        BigDecimal taskTrip = projectIterationRelDao.getInterationTypeById(1,iterationId);
        addTaskVO.setTaskTrip(addTaskVO.getTaskTrip());
        addTaskVO.setId(taskId);
        addTaskVO.setDoneCode(doneCode);
        addTaskVO.setState(1);//默认初始状态1：生效
        if(addTaskVO.getDealUser() == null){
            addTaskVO.setTaskState(0);
        }else if(addTaskVO.getDealUser() != null){
            addTaskVO.setTaskState(1);
        }
        iterationTaskRelDao.addTask(addTaskVO);

        int intePeriodByInteId = projectIterationRelDao.getIntePeriodByInteId(iterationId, 1);
        if (intePeriodByInteId == 2){
            projectIterationRelDao.updateIterationPeriod(1,iterationId,1);
        }
        if(addTaskVO.getDealUser() != null) {
            //新增任务分配消息
            AddNotifyVO addNotifyVO = new AddNotifyVO();
            addNotifyVO.setApplicant(Integer.parseInt(userId.toString()));
            addNotifyVO.setAuditor(Integer.parseInt(addTaskVO.getDealUser().toString()));
            addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_TASK);
            addNotifyVO.setTaskName(addTaskVO.getTaskName());
            addNotifyVO.setProjectId(Integer.parseInt(projectIterationRelDao.getProjectIdByInterationId(1, iterationId).toString()));
            infoNotifyService.addNotifyInfo(addNotifyVO);
        }
    }




    //未知层级深度，递归遍历组装报文，递归效率低，消耗大，慎用
    public List getChildren(BigDecimal id) throws Exception{
        List li = new ArrayList();
        List<BigDecimal> Ids = iterationTaskRelDao.getTaskChildIdByTaskId(id,1);
        if(Ids != null && !Ids.isEmpty()){
            //retMap.put("classificationId",id);
            //retMap.put("result",li);
            for(BigDecimal childId : Ids){
                Map map =iterationTaskRelDao.getTaskInfo(childId,1);
                if(map != null){
                    li.add(map);
                    if(map.get("dealUser")!=null){
                        String userName = userInfoDao.getNameByUserId((BigDecimal)map.get("dealUser"),1);
                        map.put("dealUserName",userName);
                    }
                    if(map.get("taskCreater")!=null){
                        String userName = userInfoDao.getNameByUserId((BigDecimal) map.get("taskCreater"),1);
                        map.put("createrUserName",userName);
                    }
                    BigDecimal taskClass = (BigDecimal)map.get("taskClass");
                    Integer taskState = (Integer)map.get("taskState");
                    if(taskClass!=null){
                        String taskClassName = itemRelatService.getTaskClassificationById(taskClass)==null?null:(String)itemRelatService.getTaskClassificationById(taskClass).get("className");
                        map.put("taskClassName",taskClassName);
                    }
                    if(taskState!=null&&taskClass!=null){
                        String taskStateName = itemRelatService.getStateNameByStateCode(taskClass,taskState);
                        map.put("taskStateName",taskStateName);
                    }
                    List list = getChildren(childId);
                    if(list!=null){
                        map.put("children",list);
                    }
                }
            }
        }
        return li;
    }

    /**
     * 检查子任务状态
     * @param taskId
     * @throws Exception
     */
    public void checkTaskState(BigDecimal taskId,Integer stateCode) throws Exception{
        List<Integer>childStates = iterationTaskRelDao.getTaskChildIdStateByTaskId(taskId,1);
        if(childStates!=null&&childStates.size()>0){
            for(Integer state:childStates){
                if (stateCode==null){
                    if(state.intValue()!=4){
                        throw new Exception("有相关子任务存在且未完成");
                    }
                }else if(state.intValue()<stateCode.intValue()){
                        throw new Exception("当前任务存在子任务为进行到当前状态");
                }

            }
        }
    }

    /**
     * 修改当前任务状态检查父类状态,递归到最上面父类
     * @param taskId
     * @throws Exception
     */
    public void checkTaskParentState(BigDecimal taskId,Integer stateCode,BigDecimal donceCode) throws Exception{
        BigDecimal parentId = iterationTaskRelDao.getParentTaskIdByTaskId(taskId,1);
        if(parentId!=null&&parentId.compareTo(BigDecimal.ZERO)!=0){
            List<Integer>childStates=iterationTaskRelDao.getTaskChildIdStateByTaskId(parentId,1);
            if(childStates!=null&&childStates.size()>0){
                for(Integer state:childStates){
                    if(state.intValue()<stateCode){
                        return;
                    }
                }
                iterationTaskRelDao.updateTaskState(parentId,stateCode,donceCode);
                checkTaskParentState(parentId,stateCode,donceCode);
            }
        }
    }

    /**
     * 修改任务和迭代挂靠关系
     * @param taskId
     * @param iterId
     * @throws Exception
     */
    public void updateTaskIter(BigDecimal taskId,BigDecimal iterId)throws Exception{
        BigDecimal doneCode = iterationTaskRelDao.getDoneCode();
        iterationTaskRelDao.updateTaskIter(taskId,iterId,doneCode);
    }
}