package com.location.service;

import com.location.bean.vo.SecRoleDO;
import com.location.bean.vo.RegisterVO;
import com.location.bean.vo.UserInfoVO;
import com.location.bean.vo.UserStaffVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 针对userInfo表的service
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Service
public interface UserService {
    public void addUserInfo(UserInfoVO userAddVO) throws Exception;
    public Boolean getUserInfo(String userAccount) throws Exception;
    public List<UserInfoVO> getUserInfobyUserName(String userName) throws Exception;

    public void addUser(RegisterVO registerVO) throws Exception;
    public Boolean getSecUserByUserName(String userName) throws Exception;
    public List<SecRoleDO> getAllRoleType(Integer roleType) throws Exception;
    public List<UserStaffVO> getStaffInfoByOrgId(Integer orgId) throws Exception;
    public List<UserStaffVO> getStaffInfoByProjectId(Integer projectId) throws Exception;
    public List<UserStaffVO> getAuditorStaffInfo() throws Exception;
}