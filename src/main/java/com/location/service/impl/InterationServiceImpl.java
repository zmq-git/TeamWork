package com.location.service.impl;

import com.location.bean.po.InterationItemPO;
import com.location.bean.po.ProjectInterationRelPO;
import com.location.bean.vo.InterationInfoVO;
import com.location.bean.vo.UserStaffVO;
import com.location.dao.*;
import com.location.service.InterationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @ClassName InterationServiceImpl
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/20
 * @Version V1.0
 **/
@Service
public class InterationServiceImpl implements InterationService {
    @Autowired
    ProjectIterationRelDao projectIterationRelDao;
    @Autowired
    IterationItemDao iterationItemDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    ProjectUserDao projectUserDao;
    @Autowired
    InterationTaskRelDao interationTaskRelDao;
    @Autowired
    ItemRelatDao itemRelatDao;
    @Override
    public Map findInterInfoByUserId(BigDecimal userId) throws Exception{
        Map retMap = new HashMap();
        List<InterationInfoVO> list = projectIterationRelDao.getIterInfoByUserId(userId,1);
        retMap.put("result",list);
        if (list!=null&&list.size()>0){
            for (InterationInfoVO interationInfoVO :list){
                InterationInfoVO info = iterationItemDao.getIterInfoDescById(interationInfoVO.getInterationId());
                if (info!=null){
                    interationInfoVO.setEndDate(info.getEndDate());
                    interationInfoVO.setInterationName(info.getInterationName());
                    interationInfoVO.setInterationDesc(info.getInterationDesc());
                    interationInfoVO.setInterationPicture(info.getInterationPicture());
                    interationInfoVO.setStartDate(info.getCreateDate());
                    interationInfoVO.setCreateDate(info.getCreateDate());
                    interationInfoVO.setUserName(userInfoDao.getNameByUserId(info.getCreater(),1));
                }
            }
        }
        return retMap;
    }

    /**
     * 迭代新增
     * @param interationInfoVO
     * @param userId
     * @throws Exception
     */
    public void addInterInfo(InterationInfoVO interationInfoVO, BigDecimal userId) throws Exception{
        ProjectInterationRelPO projectInterationRelPO = new ProjectInterationRelPO();
        BigDecimal doneCode = projectIterationRelDao.getDoneCode();
        BeanUtils.copyProperties(interationInfoVO,projectInterationRelPO);
        BigDecimal rel_id = projectIterationRelDao.getNewRelId();
        projectInterationRelPO.setDoneCode(doneCode);
        projectInterationRelPO.setRelId(rel_id);
        BigDecimal iteraitonId = iterationItemDao.getNewInterationId();
        projectInterationRelPO.setInterationId(iteraitonId);
        String userName = userInfoDao.getNameByUserId(userId,1);
        projectInterationRelPO.setUserName(userName);
        projectInterationRelPO.setState(1);
        projectIterationRelDao.addProjectInterationRel(projectInterationRelPO);
        InterationItemPO interationItemPO = new InterationItemPO();
        interationItemPO.setStartDate(interationInfoVO.getStartDate());
        interationItemPO.setInterationPicture(interationInfoVO.getInterationPicture());
        //doneCode
        interationItemPO.setDoneCode(projectInterationRelPO.getDoneCode());
        interationItemPO.setCreater(userId);
        interationItemPO.setEndDate(projectInterationRelPO.getEndDate());
        interationItemPO.setInterationName(interationInfoVO.getInterationName());
        interationItemPO.setInterationId(iteraitonId);
        interationItemPO.setInterationDesc(interationInfoVO.getInterationDesc());
        iterationItemDao.saveIterationItem(interationItemPO);
    }


    @Override
    public void deleteInterInfo(BigDecimal interationId, BigDecimal userId) throws Exception{

    }

    /**
     * 根据项目id查询迭代信息
     * @param projectId
     * @return
     * @throws Exception
     */
    @Override
    public List<InterationInfoVO> findInterInfoByProjectId(BigDecimal projectId) throws Exception {
            List<InterationInfoVO> reIterInfoByProjectId = projectIterationRelDao.getIterInfoByProjectId(projectId, 1);
            if (reIterInfoByProjectId != null) {
                for(InterationInfoVO interationInfoVO:reIterInfoByProjectId){
                    Map<String,Integer> map = new HashMap<>();
                    int taskNum = projectIterationRelDao.findTaskNum(interationInfoVO.getInterationId(), 1);
                    List<BigDecimal> taskIdByInterationId
                            = projectIterationRelDao.getTaskIdByInterationId(interationInfoVO.getInterationId(),1);
                    if(taskIdByInterationId ==null || taskIdByInterationId.size()==0){
                        projectIterationRelDao.updateIterationPeriod(0,interationInfoVO.getInterationId(),1);
                        interationInfoVO.setInterationPeriod(0);
                        interationInfoVO.setInterationProgress(0);
                        interationInfoVO.setUserName(userInfoDao.getNameByUserId(interationInfoVO.getCreater(),1));

                    }
                    else if (taskIdByInterationId != null && taskIdByInterationId.size() > 0){
                        for (BigDecimal taskId : taskIdByInterationId) {
                            Integer state =  projectIterationRelDao.findStateByTaskId(taskId,1);
                            if(state !=null){
                            if(state == 0 ){
                                if(map.containsKey("0")){
                                    int num = 0;
                                    num = map.get("0");
                                    map.put("0",++num);
                                }else{
                                    map.put("0",1);
                                }
                            }else if(state == 1){
                                if(map.containsKey("1")){
                                    int num = 0;
                                    num = map.get("1");
                                    map.put("1",++num);
                                }else{
                                    map.put("1",1);
                                }
                            }else if(state == 2) {
                                if (map.containsKey("2")) {
                                    int num = 0;
                                    num = map.get("2");
                                    map.put("2", ++num);
                                } else {
                                    map.put("2", 1);
                                }
                            }
                            }
                        }
                        System.out.println("------------");
                        System.out.println(map);
                        if(map.get("2")!=null && map.get("2") == taskNum){
                            projectIterationRelDao.updateIterationPeriod(2,interationInfoVO.getInterationId(),1);
                            interationInfoVO.setInterationPeriod(2);
                        }else if(map.get("1") !=null && map.get("1") > 0){
                            projectIterationRelDao.updateIterationPeriod(1,interationInfoVO.getInterationId(),1);
                            interationInfoVO.setInterationPeriod(1);
                        }
                        else if(map.get("1") !=null && map.get("0") == taskNum){
                            projectIterationRelDao.updateIterationPeriod(0,interationInfoVO.getInterationId(),1);
                            interationInfoVO.setInterationPeriod(0);
                        }
                        int performTaskNum = projectIterationRelDao.findPerformTaskNum(interationInfoVO.getInterationId(), 2,1);
                        double result =  performTaskNum/(double)taskNum*100;
                        interationInfoVO.setInterationProgress((int)result);
                        interationInfoVO.setUserName(userInfoDao.getNameByUserId(interationInfoVO.getCreater(),1));
                    }


                }
                return reIterInfoByProjectId;
            }
        return null;
        }






    /**
     * 根据迭代名称精确查询
     * @param interationName
     * @return
     * @throws Exception
     */
    @Override
    public Boolean getInterInfo(String interationName,BigDecimal projectId) throws Exception {
        List<BigDecimal> iterIdByProjectId = projectIterationRelDao.getIterIdByProjectId(projectId, 1);
        if (iterIdByProjectId != null){
            for (BigDecimal interationId : iterIdByProjectId){
                List<String> inteNameByInteId = projectIterationRelDao.getInteNameByInteId(interationId);
                for (String findInteName : inteNameByInteId){
                    if (interationName.equals(findInteName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param endDate
     * @return
     * @throws Exception
     */
    @Override
    public Boolean checkInterEndPeriod(Timestamp endDate) throws Exception {
        long curTime = new Date().getTime();
        if (curTime < endDate.getTime()){
            return true;
        }else {
            throw new Exception("迭代已经结束");
        }
    }

    /**
     * 根据迭代id获取项目成员信息
     * @param interationId
     * @return
     * @throws Exception
     */
    @Override
    public List<UserStaffVO> getUsersByInterationId(BigDecimal interationId) throws Exception{
        List<UserStaffVO> list = new ArrayList<UserStaffVO>();
        BigDecimal projectId = projectIterationRelDao.getProjectIdByInterationId(1,interationId);
        if (projectId!=null){
            List<BigDecimal> userIds = projectUserDao.getUserIdsByprojectId(projectId);
            if (userIds!=null&&userIds.size()>0){
                for (BigDecimal userId:userIds){
                    UserStaffVO userStaffVO = userInfoDao.getInfoByUserId(userId,1);
                    if (userStaffVO!=null){
                        list.add(userStaffVO);
                    }
                }
            }

        }
        return list;
    }


}