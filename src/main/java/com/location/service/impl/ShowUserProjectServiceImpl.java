package com.location.service.impl;

import com.location.bean.po.ProjectInfoPO;
import com.location.bean.vo.ProjectInfoVO;
import com.location.dao.ShowUserProjectDao;
import com.location.dao.UserInfoDao;
import com.location.service.ShowUserProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ShowUserProjectServiceImpl
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Service
public class ShowUserProjectServiceImpl implements ShowUserProjectService {

    @Autowired
    ShowUserProjectDao showUserProjectDao;

    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 根据登录用户查询该用户加入的项目
     * @param userAccount
     * @return
     * @throws Exception
     */
    @Override
    public List<ProjectInfoVO> findUserAllProject(String userAccount) throws Exception {
        if(StringUtils.isNotBlank(userAccount)){
            List<ProjectInfoPO> projectInfoPO = showUserProjectDao.findUserProject(userAccount,1);
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
}
