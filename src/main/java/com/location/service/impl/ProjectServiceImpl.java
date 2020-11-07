package com.location.service.impl;

import com.location.bean.po.ProjectInfoPO;
import com.location.bean.po.ProjectTypePO;
import com.location.bean.po.UserProjectRelPO;
import com.location.bean.vo.ProjectInfoVO;
import com.location.bean.vo.ProjectUserAddVO;
import com.location.bean.vo.UserStaffVO;
import com.location.dao.*;
import com.location.service.InfoNotifyService;
import com.location.service.ProjectService;
import com.location.service.ProjectUserQService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ProjectServiceImpl
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/11/13
 * @Version V1.0
 **/

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectInfoDao projectInfoDao;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ProjectUserDao projectUserDao;

    @Autowired
    UserProjectRelDao userProjectRelDao;

    @Autowired
    StaticDataDao staticDataDao;

    @Autowired
    ProjectUserQService projectUserQService;

    @Autowired
    InfoNotifyService infoNotifyService;

    @Autowired
    ProjectIterationRelDao projectIterationRelDao;
    /**
     * 保存项目信息
     * @param projectAddVO
     * @return
     * @throws Exception
     */
    @Override
    public void addProjectInfo(ProjectInfoVO projectAddVO,BigDecimal userId) throws Exception {
        if(projectAddVO != null){
            ProjectInfoPO projectInfoPO = projectInfoDao.getProjectInfoByPreciseName(projectAddVO.getProjectName(),userId,1);
            if(projectInfoPO == null){
                ProjectInfoPO projectInfoPO1 = new ProjectInfoPO();
                BeanUtils.copyProperties(projectAddVO,projectInfoPO1);
                BigDecimal projectCode = projectInfoDao.getProjectCode();
                BigDecimal doneCode = projectInfoDao.getDoneCode();
                projectInfoPO1.setProjectId(projectCode);
                projectInfoPO1.setState(1);
                projectInfoPO1.setDoneCode(doneCode);
                projectInfoPO1.setUserId(userId);
                projectInfoPO1.setCreater(userInfoDao.getInfoByUserId(userId,1).getUserName());
                projectInfoDao.addNewProject(projectInfoPO1);

                //将一组成员加入到项目中
                int loginUserId = Integer.parseInt(userId.toString());
                if (projectAddVO.getOrgId()!=null){
                    List<UserStaffVO> staffInfoByOrgId = userInfoDao.getStaffInfoByOrgId(projectAddVO.getOrgId(), 1);
                    if (staffInfoByOrgId!=null && staffInfoByOrgId.size()>0){
                        for (UserStaffVO userStaffVO : staffInfoByOrgId){
                            if (Integer.parseInt(userStaffVO.getUserId().toString()) != loginUserId){
                                ProjectUserAddVO projectUserAddVO = new ProjectUserAddVO();
                                projectUserAddVO.setProjectId(projectCode);
                                projectUserAddVO.setProjectRole(userInfoDao.getUserRoleRelRoleIDByUserId(Integer.parseInt(userStaffVO.getUserId().toString()),1));
                                projectUserAddVO.setCreater(userId);
                                List<Integer> userList = new ArrayList<>();
                                userList.add(Integer.parseInt(userStaffVO.getUserId().toString()));
                                projectUserAddVO.setUserIdList(userList);
                                projectUserQService.addProjectMember(projectUserAddVO);
                            }
                        }
                    }
                }

                //将项目创建者加入项目中
                UserProjectRelPO userProjectRelPO = new UserProjectRelPO();
                userProjectRelPO.setUserId(userId);
                BigDecimal relId = projectUserDao.getRelId();
                if(relId==null){
                    relId =BigDecimal.ONE;
                }
                userProjectRelPO.setRelId(relId);
                userProjectRelPO.setProjectId(projectCode);
                userProjectRelPO.setProjectRole(1);//表示为管理员
                userProjectRelPO.setState(1);//表示状态位1
                userProjectRelPO.setDoneCode(projectUserDao.getDoneCode());
                userProjectRelPO.setCreater(userId);
                projectUserDao.save(userProjectRelPO);


            }
        }
    }

    /**
     * 根据项目名称精准查询项目是否存在
     * @param projectName
     * @return
     * @throws Exception
     */
    @Override
    public ProjectInfoVO getProjectInfo(String projectName) throws Exception{
        if(StringUtils.isNotBlank(projectName)){
            ProjectInfoPO projectInfoPO = projectInfoDao.getProjectInfo(projectName.trim(),1);
            if(projectInfoPO != null){
                ProjectInfoVO projectInfoVO = new ProjectInfoVO();
                BeanUtils.copyProperties(projectInfoPO,projectInfoVO);
                projectInfoVO.setUserName(userInfoDao.getNameByUserId(projectInfoPO.getUserId(),1));
                projectInfoVO.setProjectTypeName(staticDataDao.getProjectTypeName(projectInfoPO.getProjectType()));
                return projectInfoVO;
            }else {
                return null;
            }
        }
        return null;
    }


    /**
     * 根据项目名称模糊查询
     * @param projectName
     * @return
     * @throws Exception
     */
    @Override
    public List<ProjectInfoVO> getProjectInfoByProjectName(String projectName,BigDecimal userId) throws Exception {
        if(StringUtils.isNotBlank(projectName)){
            List<ProjectInfoPO> projectInfoPO = projectInfoDao.getProjectInfoByVagueName(projectName,userId,1);
            List<ProjectInfoVO> projectInfoVOList = new ArrayList<>();
            if (projectInfoPO != null){
                for (ProjectInfoPO projectInfo : projectInfoPO){
                    ProjectInfoVO projectInfoVO = new ProjectInfoVO();
                    BeanUtils.copyProperties(projectInfo,projectInfoVO);
                    projectInfoVO.setUserName(userInfoDao.getNameByUserId(projectInfo.getUserId(),1));
                    projectInfoVOList.add(projectInfoVO);
                }
                return projectInfoVOList;
            }
        }
        return null;
    }

    /**
     * 查询所有项目
     * @param pageSize,current
     * @return
     * @throws Exception
     */
    @Override
    public List<ProjectInfoVO> getProjectAll(Integer pageSize,Integer current,Integer userId,Integer projectPeriod) throws Exception {
        //修改项目状态
        updateProjectPeriod (userId);

        Integer cur = (current-1)*pageSize;
        List<ProjectInfoPO> allProject = null;
        //1表示进行中项目，2表示全部项目
        if (projectPeriod == 1){
            allProject = projectInfoDao.getAllProject(pageSize,cur,new BigDecimal(userId),1,1);
        }else if (projectPeriod == 2){
            allProject = projectInfoDao.getAllProject(pageSize,cur,new BigDecimal(userId),1,null);
        }
        List<ProjectInfoVO> projectInfoVOList = new ArrayList<>();
        if (allProject != null){
            for (ProjectInfoPO projectInfo : allProject){
                ProjectInfoVO projectInfoVO = new ProjectInfoVO();
                BeanUtils.copyProperties(projectInfo,projectInfoVO);
                projectInfoVO.setProjectTypeName(staticDataDao.getProjectTypeName(projectInfoVO.getProjectType()));
                projectInfoVOList.add(projectInfoVO);
            }
        }
        return projectInfoVOList;
    }

    /**
     * 删除项目
     * @param projectId
     * @throws Exception
     */
    @Override
    public void deleteProjectInfo(BigDecimal projectId) throws Exception {
        if(StringUtils.isNotBlank(projectId.toString())){
            BigDecimal done_code = projectInfoDao.getDoneCode();
            projectInfoDao.deleteProjectInfo(projectId,done_code);
            userProjectRelDao.deleteProjectAllUserInfo(projectId,1);
        }

    }

    /**
     * 修改项目信息
     * @param projectInfoVO
     * @return
     * @throws Exception
     */
    @Override
    public void updateProjectInfo(ProjectInfoVO projectInfoVO) throws Exception {
        if(projectInfoVO != null){
            ProjectInfoPO projectInfoPO = new ProjectInfoPO();
            BeanUtils.copyProperties(projectInfoVO,projectInfoPO);
            projectInfoDao.updateProjectInfo(projectInfoPO,1);
        }
    }

    /**
     * 查询项目数量
     * @return
     * @throws Exception
     */
    @Override
    public Integer getProjectNumber(Integer userId,Integer projectPeriod) throws Exception {
        Integer projectNum = null;
        //1表示进行中项目数量 2表示全部项目数量
        if (projectPeriod == 1){
            projectNum = projectInfoDao.getProjectNumber(userId,1,1);
        }else if (projectPeriod == 2){
            projectNum = projectInfoDao.getProjectNumber(userId,1,null);
        }
        return projectNum;
    }

    /**
     * 查询项目类型
     * @return
     * @throws Exception
     */
    @Override
    public List<ProjectTypePO> getProjectTypeInfo() throws Exception {
        return staticDataDao.getProjectType();
    }

    /**
     * 修改项目状态
     * @throws Exception
     */
    @Override
    public void updateProjectPeriod(Integer userId) throws Exception {
        long curTime = new Date().getTime();
        List<Integer> projectIdByUserId = userProjectRelDao.getProjectIdByUserId(userId, 1);
        for (Integer projectId : projectIdByUserId){
            ProjectInfoPO projectInfo = projectInfoDao.getProjectInfoByProjectId(projectId, 1);
            if (curTime >= projectInfo.getStartDate().getTime()){
                if (curTime <= projectInfo.getEndDate().getTime()){
                    Boolean flag = true;
                    List<BigDecimal> iterIdByProjectId = projectIterationRelDao.getIterIdByProjectId(new BigDecimal(projectId), 1);
                    if (iterIdByProjectId!=null && iterIdByProjectId.size()>0){
                        for (BigDecimal iterationId : iterIdByProjectId) {
                            Integer iterationPeriod = projectIterationRelDao.getIntePeriodByInteId(iterationId, 1);
                            if (iterationPeriod != null){
                                if (iterationPeriod == 2) {
                                    flag = false;
                                } else {
                                    flag = true;
                                    break;
                                }
                        }else{
                                flag = true;
                                break;
                            }
                        }
                    }

                    if (flag){
                        projectInfoDao.updateProjectPeriod(projectId,1);
                    }else {
                        projectInfoDao.updateProjectPeriod(projectId,2);
                    }
                }
            }else {
                projectInfoDao.updateProjectPeriod(projectId,0);
            }
        }
    }

    @Override
    public Integer queryProjectRole(BigDecimal projectId, BigDecimal userId) throws Exception {
        Integer projectRole = userProjectRelDao.queryProjectRole(projectId,userId);
        return projectRole;
    }


}
