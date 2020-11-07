package com.location.service.impl;

import com.location.bean.po.UserProjectRelPO;
import com.location.bean.vo.AddNotifyVO;
import com.location.bean.vo.ProjectUserAddVO;
import com.location.common.constants.TeamConst;
import com.location.dao.ProjectUserDao;
import com.location.dao.UserInfoDao;
import com.location.dao.UserProjectRelDao;
import com.location.service.InfoNotifyService;
import com.location.service.ProjectUserQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName ProjectUserQServiceImpl
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/7
 * @Version V1.0
 **/
@Service
public class ProjectUserQServiceImpl implements ProjectUserQService {
    @Autowired
    ProjectUserDao projectUserDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserProjectRelDao userProjectRelDao;
    @Autowired
    InfoNotifyService infoNotifyService;

    @Override
    public List<String> addProjectMember(ProjectUserAddVO projectUserAddVO)throws Exception{
        Set<Integer> allUserId = userProjectRelDao.getUserIdByProjectId(Integer.parseInt(projectUserAddVO.getProjectId().toString()), 1);
        List<String> existUser = new ArrayList<>();
        Boolean flag = true;
        for (Integer userId : projectUserAddVO.getUserIdList()){
            if (allUserId.contains(userId)){
                flag = false;
                existUser.add(userInfoDao.getNameByUserId(new BigDecimal(userId),1));
            }
        }
        if (flag){
            for (Integer userId : projectUserAddVO.getUserIdList()){
                for (Integer roleId : projectUserAddVO.getProjectRole()){
                    UserProjectRelPO userProjectRelPO = new UserProjectRelPO();
                    userProjectRelPO.setProjectId(projectUserAddVO.getProjectId());
                    userProjectRelPO.setUserId(new BigDecimal(userId));
                    userProjectRelPO.setProjectRole(roleId);
                    BigDecimal relId = projectUserDao.getRelId();
                    if(relId==null){
                        relId =BigDecimal.ONE;
                    }
                    userProjectRelPO.setRelId(relId);
                    userProjectRelPO.setState(1);//表示状态位1
                    userProjectRelPO.setDoneCode(projectUserDao.getDoneCode());
                    userProjectRelPO.setCreater(projectUserAddVO.getCreater());
                    projectUserDao.save(userProjectRelPO);
                }

                //新增分配项目成员消息信息
                AddNotifyVO addNotifyVO = new AddNotifyVO();
                addNotifyVO.setApplicant(Integer.parseInt(projectUserAddVO.getCreater().toString()));
                addNotifyVO.setAuditor(userId);
                addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_COMMON);
                addNotifyVO.setProjectId(Integer.parseInt(projectUserAddVO.getProjectId().toString()));
                infoNotifyService.addNotifyInfo(addNotifyVO);
            }
        }
        return existUser;
    }


    public Map getIterInfoByUserId(BigDecimal userId)throws Exception{
        Map retMap = new HashMap();
        List list = projectUserDao.getIterInfoByUserId(userId);
        retMap.put("result",list);
        return retMap;
    }

    /**
     * 移除项目成员
     * @param projectId
     * @param userIdList
     * @throws Exception
     */
    @Override
    public void deleteProjectMember(Integer projectId, List<Integer> userIdList) throws Exception {
        if (userIdList !=null && userIdList.size()>0){
            for (Integer userId : userIdList){
                userProjectRelDao.deleteUserProRelInfo(projectId,userId,1);
            }
        }
    }

    /**
     * 修改项目成员角色
     * @param projectId
     * @param userId
     * @param roleIdList
     * @throws Exception
     */
    @Override
    public void updateProjectMenberRole(Integer projectId, Integer userId, List<Integer> roleIdList,Integer operator) throws Exception {
        userProjectRelDao.deleteUserProRelInfo(projectId,userId,1);
        for (Integer roleId : roleIdList){
            UserProjectRelPO userProjectRelPO = new UserProjectRelPO();
            userProjectRelPO.setProjectId(new BigDecimal(projectId));
            userProjectRelPO.setUserId(new BigDecimal(userId));
            userProjectRelPO.setProjectRole(roleId);
            BigDecimal relId = projectUserDao.getRelId();
            if(relId==null){
                relId =BigDecimal.ONE;
            }
            userProjectRelPO.setRelId(relId);
            userProjectRelPO.setState(1);//表示状态位1
            userProjectRelPO.setDoneCode(projectUserDao.getDoneCode());
            userProjectRelPO.setCreater(new BigDecimal(operator));
            projectUserDao.save(userProjectRelPO);
        }
    }


}