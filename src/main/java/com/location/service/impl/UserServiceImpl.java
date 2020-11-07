package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.common.utils.SecurityUtil;
import com.location.dao.ISecOrgDAO;
import com.location.dao.ISecRoleDAO;
import com.location.dao.ISecStaffDAO;
import com.location.bean.po.*;
import com.location.bean.vo.AddNotifyVO;
import com.location.bean.vo.RegisterVO;
import com.location.bean.vo.UserInfoVO;
import com.location.bean.vo.UserStaffVO;
import com.location.common.constants.TeamConst;
import com.location.dao.UserInfoDao;
import com.location.dao.UserProjectRelDao;
import com.location.service.InfoNotifyService;
import com.location.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName UserServiceImpl
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    ISecRoleDAO iSecRoleDAO;

    @Autowired
    ISecStaffDAO iSecStaffDAO;

    @Autowired
    InfoNotifyService infoNotifyService;

    @Autowired
    UserProjectRelDao userProjectRelDao;

    @Autowired
    ISecOrgDAO iSecOrgDAO;

    /**
     * 成员注册新增
     * @param userAddVO
     * @throws Exception
     */
    @Override
    public void addUserInfo(UserInfoVO userAddVO) throws Exception{
        if(userAddVO !=null){
            UserInfoPO userInfoPO = userInfoDao.getUserInfo(userAddVO.getUserAccount());
            if(userInfoPO!=null){
                throw new Exception("用户已经存在");
            }else {
                BeanUtils.copyProperties(userInfoPO,userAddVO);
                BigDecimal userId = userInfoDao.getUserId();
                BigDecimal donecode = userInfoDao.getDoneCode();
                userInfoPO.setUserId(userId);
                userInfoPO.setDoneCode(donecode);
                userInfoDao.addNewUser(userInfoPO);
            }
        }
    }

    /**
     * 根据账号精准查询用户是否存在
     * @param userAccount
     * @return
     * @throws Exception
     */
    @Override
    public Boolean getUserInfo(String userAccount) throws Exception{
        if(StringUtils.isNotBlank(userAccount)){
            UserInfoPO userInfoPO = userInfoDao.getUserInfo(userAccount.trim());
            if(userInfoPO !=null){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据用户名称模糊查询
     * @param userName
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfoVO> getUserInfobyUserName (String userName) throws Exception{
        if(StringUtils.isNotBlank(userName)){
            List userInfoPO= userInfoDao.getUserInfobyUserName(userName.trim(),0);
            if (userInfoPO!=null){
                UserInfoVO userInfoVO = new UserInfoVO();
                BeanUtils.copyProperties(userInfoPO,userInfoVO);
                return userInfoPO;
            }
        }
        return null;
    }



    /**
     * 成员注册新增
     * @param registerVO
     * @throws Exception
     */
    @Override
    public void addUser(RegisterVO registerVO) throws Exception {
        if (registerVO != null){
            SecUserDO secUserByUserName = userInfoDao.getSecUserByUserName(registerVO.getUsername(),1);
            if (secUserByUserName == null){
                registerVO.setStaffId(Integer.parseInt(userInfoDao.getUserId().toString()));
                SecStaffDO secStaffDO = new SecStaffDO();
                secStaffDO.setStaffName(registerVO.getStaffName());
                secStaffDO.setOrgId(registerVO.getOrgId());
                secStaffDO.setPhoneNum(registerVO.getPhoneNum());
                secStaffDO.setCreator(registerVO.getStaffId());
                secStaffDO.setMail(registerVO.getMail());
                userInfoDao.addSecStaffInfo(secStaffDO);

                SecUserDO secUserDO = new SecUserDO();
                secUserDO.setStaffId(registerVO.getStaffId());
                secUserDO.setUsername(registerVO.getUsername());
                secUserDO.setPassword(SecurityUtil.getMD5Code(registerVO.getPassword()));
                secUserDO.setCreator(registerVO.getStaffId());
                secUserDO.setModifier(registerVO.getStaffId());
                userInfoDao.addSecUserInfo(secUserDO);

                SecUserRoleRelDO secUserRoleRelDO = new SecUserRoleRelDO();
                secUserRoleRelDO.setUserId((long)registerVO.getStaffId());
                secUserRoleRelDO.setRoleId((long)registerVO.getRole());
                secUserRoleRelDO.setOpId(registerVO.getStaffId());
                userInfoDao.addSecUserRoleRel(secUserRoleRelDO,2);

                AddNotifyVO addNotifyVO = new AddNotifyVO();
                addNotifyVO.setApplicant(registerVO.getStaffId());
                addNotifyVO.setAuditor(Integer.parseInt(registerVO.getAuditor().toString()));
                addNotifyVO.setInfoType(TeamConst.INFO_NOTIFY_TYPE_REGISTER);
                infoNotifyService.addNotifyInfo(addNotifyVO);
            }
        }
    }



    /**
     * 根据账号查询用户是否存在
     * @param userName
     * @return
     * @throws Exception
     */
    @Override
    public Boolean getSecUserByUserName(String userName) throws Exception {
        if(StringUtils.isNotBlank(userName)){
            SecUserDO secUserByUserName1 = userInfoDao.getSecUserByUserName(userName,1);
            SecUserDO secUserByUserName2 = userInfoDao.getSecUserByUserName(userName, 2);
            if(secUserByUserName1 !=null || secUserByUserName2 != null){
                return true;
            }
        }
        return false;
    }


    /**
     * 查询所有角色名字
     * @return
     * @throws Exception
     */
    @Override
    public List<SecRoleDO> getAllRoleType(Integer roleType) throws Exception{
        List<SecRoleDO> allRoleType = iSecRoleDAO.getAllRoleType(roleType);
        if (allRoleType != null) {
            return allRoleType;
        }
        return null;
    }

    /**
     * 查询组织下的所有用户
     * @param orgId
     * @return
     * @throws Exception
     */
    @Override
    public List<UserStaffVO> getStaffInfoByOrgId(Integer orgId) throws Exception {
        List<UserStaffVO> list = new ArrayList<>();
        List<UserStaffVO> staffInfoByOrgId = userInfoDao.getStaffInfoByOrgId(orgId, 1);
        if (staffInfoByOrgId!=null && staffInfoByOrgId.size()>0){
            for (UserStaffVO userStaffVO : staffInfoByOrgId){
                userStaffVO.setOrgName(userInfoDao.getOrgInfoByOrgId(userStaffVO.getOrgId(),1).getOrgName());
                list.add(userStaffVO);
            }
        }
        getChildrenOrg(new Long(orgId),list);
        return list;
    }

    /**
     * 未知层级深度，递归遍历组织层级,寻找组织下的用户
     * @param orgId
     * @return
     * @throws Exception
     */
    public void getChildrenOrg(Long orgId, List<UserStaffVO> childId) throws Exception{
        List<Long> secOrgRealChildId = iSecOrgDAO.getSecOrgRelatByOrgRelatId(orgId,1);
        if (secOrgRealChildId != null){
            for (Long childOrgId : secOrgRealChildId){
                List<UserStaffVO> staffInfoByOrgId = userInfoDao.getStaffInfoByOrgId(childOrgId.intValue(), 1);
                if (staffInfoByOrgId!=null && staffInfoByOrgId.size()>0){
                    for (UserStaffVO userStaffVO : staffInfoByOrgId){
                        userStaffVO.setOrgName(userInfoDao.getOrgInfoByOrgId(userStaffVO.getOrgId(),1).getOrgName());
                        childId.add(userStaffVO);
                    }
                }
                getChildrenOrg(childOrgId,childId);
            }
        }
    }

    /**
     * 查询项目下的所有用户
     * @param projectId
     * @return
     * @throws Exception
     */
    @Override
    public List<UserStaffVO> getStaffInfoByProjectId(Integer projectId) throws Exception {
        List<UserStaffVO> list = new ArrayList<>();
        Set<Integer> userIdByProjectId = userProjectRelDao.getUserIdByProjectId(projectId, 1);
        if (userIdByProjectId!=null && userIdByProjectId.size()>0){
            for (Integer staffId : userIdByProjectId){
                UserStaffVO userStaffVO = userInfoDao.getStaffInfoById(staffId, 1);
                userStaffVO.setOrgName(userInfoDao.getOrgInfoByOrgId(userStaffVO.getOrgId(),1).getOrgName());
                List<UserProjectRelPO> userProRelInfoByUserId = userProjectRelDao.getUserProRelInfoByUserId(staffId, projectId, 1);
                Map roleMap = new HashMap();
                for (UserProjectRelPO userProjectRelPO : userProRelInfoByUserId){
                    roleMap.put(userProjectRelPO.getProjectRole(),userInfoDao.getRoleNameByRoleId(userProjectRelPO.getProjectRole(),1));
                    userStaffVO.setCreateDate(userProjectRelPO.getCreateDate());
                }
                userStaffVO.setRoleNameList(roleMap);
          /*      Integer userId = userInfoDao.getSecUserIdByStaffId(new BigDecimal(staffId), 1);
                List<Integer> roleIdList = userInfoDao.getUserRoleRelRoleIDByUserId(userId, 1);
                for (Integer roleId : roleIdList){
                    String roleName = userInfoDao.getRoleNameByRoleId(roleId, 2, 1);
                    if (roleName!=null){
                        userStaffVO.setRoleName(roleName);
                    }
                }*/
                list.add(userStaffVO);
            }
            return list;
        }
        return null;
    }

    /**
     * 查询审核人的信息
     * @return
     * @throws Exception
     */
    @Override
    public List<UserStaffVO> getAuditorStaffInfo() throws Exception {
        List<UserStaffVO> list = new ArrayList<>();
        List<Integer> userIdList = userInfoDao.getUserRoleRelUserIDByRoleId(27, 1);
        if (userIdList!=null && userIdList.size()>0){
            for (Integer userId : userIdList){
                UserStaffVO staffInfoById = userInfoDao.getStaffInfoById(userId, 1);
                list.add(staffInfoById);
            }
            return list;
        }
        return null;
    }


}