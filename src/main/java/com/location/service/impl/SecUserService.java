package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.bean.vo.SecMenuVO;
import com.location.bean.vo.SecUserSaveVO;
import com.location.bean.vo.SecUserVO;
import com.location.common.AssertLocal;
import com.location.common.constants.SecConst;
import com.location.common.enums.ResultCodeEnum;
import com.location.common.utils.ObjectUtils;
import com.location.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.location.service.impl.SecUserRoleRelService;
import com.location.service.impl.SecRoleService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubj on 2019/8/29.
 */
@Service
public class SecUserService {
    @Autowired
    SecUserSVImpl secUserSV;

    @Autowired
    ISecRolePermissionDAO secRolePremissionDAO;

    @Autowired
    SecMenuDAO secMenuDAO;

    @Autowired
    ISecOperateDAO secOperateDAO;

    @Autowired
    ISecUserDAO secUserDAO;

    @Autowired
    private SecStaffService secStaffService;

    @Autowired
    private SecOrgService secOrgService;

    @Autowired
    ISecStaffDAO secStaffDAO;

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    SecRoleService secRoleService;

    @Autowired
    SecUserRoleRelService secUserRoleRelService;

    public SecUserVO login(String username, String password) throws Exception{
        AssertLocal.hasLength(username, "用户名不能为空");
        AssertLocal.hasLength(password, "密码不能为空");
        //password = SecurityUtil.getMD5Code(password);
        //password= Base64Util.encode(password);
        SecUserDO secUserDO = secUserDAO.getSecUserByUsernameAndPasswordAndStatus(username, password, SecConst.USER_STATUS_VALID);
        AssertLocal.isNotNull(secUserDO, ResultCodeEnum.USERNAME_OR_PASSWORD_ERROR);

        SecUserVO SecUserVO = new SecUserVO();
        SecUserVO.setCompanyId(secOrgService.getTopOrgIdByOrgId(secStaffDAO.getSecStaffById(secUserDO.getStaffId()).getOrgId()));
        SecUserVO.setCompanyName(userInfoDao.getOrgInfoByOrgId(SecUserVO.getCompanyId(),1).getOrgName());
        SecUserVO.setMail(secStaffDAO.getSecStaffById(secUserDO.getStaffId()).getMail());
        SecUserVO.setPhoneNum(secStaffDAO.getSecStaffById(secUserDO.getStaffId()).getPhoneNum());
        SecUserVO.setPortraitAddress(secStaffDAO.getSecStaffById(secUserDO.getStaffId()).getPortraitAddress());
        SecUserVO.setId(secUserDO.getId());
        if(secUserDO.getDomainId()!=null){
            SecUserVO.setDomainId(secUserDO.getDomainId());
        }
        SecUserVO.setStaffId(secUserDO.getStaffId());
        SecUserVO.setUsername(secUserDO.getUsername());
        SecUserVO.setPassword(secUserDO.getPassword());
        if(secUserDO.getIsAdmin()!=null){
            SecUserVO.setIsAdmin(secUserDO.getIsAdmin());
        }
        SecUserVO.setCreator(secUserDO.getCreator());
        SecUserVO.setCreateTime(secUserDO.getCreateTime());
        SecUserVO.setModifier(secUserDO.getModifier());
        SecUserVO.setUpdateTime(secUserDO.getUpdateTime());
        SecUserVO.setStatus(secUserDO.getStatus());
        SecStaffDO secStaffDO = secStaffService.getStaffById(secUserDO.getStaffId());
        if (ObjectUtils.isNotEntity(secStaffDO)) {
            SecUserVO.setStaffName(secStaffDO.getStaffName());
            SecUserVO.setOrgId(secStaffDO.getOrgId());
            SecOrgDO SecOrgEntity = secOrgService.getSecOrgById(secStaffDO.getOrgId());
            if (ObjectUtils.isNotEntity(SecOrgEntity)) {
                SecUserVO.setOrgName(SecOrgEntity.getOrgName());
            }

        }
        //获取登录用户的权限
/*        SecMenuVO secMenuVO = getSecUserMenuByUserNmae(username);
        SecUserVO.setSecMenuVO(secMenuVO);*/
        List<SecMenusDO> list = getAuthByUserId(secUserDO.getId());
        SecMenuVO secMenuVO = new SecMenuVO();
        secMenuVO.setSecMenuDOS(list);
        SecUserVO.setSecMenuVO(secMenuVO);
        return SecUserVO;
    }
    public List<SecMenusDO> getAuthByUserId(long userId)throws Exception {
        //根据用户获取所有角色ID
        List<SecUserRoleRelDO> roleList = secUserRoleRelService.getSecUserRoleRelById(userId);
        if (roleList != null && roleList.size() > 0) {
            List<Long> idList = new ArrayList<>();
            for (SecUserRoleRelDO secUserRoleRelDO : roleList) {
                idList.add(secUserRoleRelDO.getRoleId());
            }
            //根据角色ID获取所有的权限
            return secRoleService.getAuthByRoleId(idList);
        }
        return new ArrayList<>();

    }
    public SecMenuVO getSecUserMenuByUserNmae(String username) throws Exception{
        AssertLocal.hasLength(username, "用户名不能为空");
        //获取用户所有的角色
        List<Long> roleIds= secUserDAO.getRoleIdsByUserName(username.trim());
        //获取角色对应的所有权限
        SecMenuVO secMenuVO = new SecMenuVO();
        if(null!=roleIds&&roleIds.size()>0){
            List <SecMenuDO> list = new ArrayList<SecMenuDO>();
            List <SecOperateDO> list1 = new ArrayList<SecOperateDO>();
            for(Long roleId:roleIds){
                List<SecPermissionEntityEtlDO> secPermissionEntityEtlDOS = secRolePremissionDAO.getPremissionByRoleId(roleId,SecConst.USER_STATUS_VALID);
                for(SecPermissionEntityEtlDO secPermissionEntityEtlDO:secPermissionEntityEtlDOS){
                    switch(secPermissionEntityEtlDO.getEntityType()){
                        //菜单
                        case 0 :
                            SecMenuDO secMenuDO = secMenuDAO.getMenuById(secPermissionEntityEtlDO.getEntityId());
                            list.add(secMenuDO);
                            break;
                        //操作
                        case 1 :
                            SecOperateDO secOperateDO = secOperateDAO.getSecOperateById(secPermissionEntityEtlDO.getEntityId());
                            list1.add(secOperateDO);
                            break;
                        //按钮
                        case 2 :
                            break;
                        default:

                    }
                }
            }
            int id = 0;

            for(int i =0;i<list.size();i++){
       /*         if(list.get(i).getParentId()==0||list.get(i).getParentId()==id){
*//*                    list.remove(i);
                    i--;*//*
                    continue;
                }*/
                List<SecMenuDO> arrList = null;
                id = list.get(i).getId();
                List secMenus = secMenuDAO.getMenuByParenId(list.get(i).getId());
                for (int j=0;j<secMenus.size();j++){
                    if(list.contains(secMenus.get(j))){
                        if(list.get(i).getSecMenuDOS()==null){
                            arrList = new ArrayList<SecMenuDO>();
                            list.get(i).setSecMenuDOS(arrList);
                        }
                        arrList.add((SecMenuDO)secMenus.get(j));
                        list.remove(secMenus.get(j));

                    }
                }
            }
            //secMenuVO.setSecMenuDOS(list);
            secMenuVO.setSecOperateDOS(list1);
        }


        return secMenuVO;
    }
    public void saveUserInfo(SecUserSaveVO secUserSaveVO,int opId)throws Exception{
        if(secUserSaveVO.getType()==0){
            secUserSV.insetUserInfo(secUserSaveVO,opId);
        }
        if(secUserSaveVO.getType()==1){
            //删除在修改的编辑禁用按钮，不再具有删除操作
            secUserSV.delUserInfo(secUserSaveVO.getUserId());
        }
        if(secUserSaveVO.getType()==2){
            secUserSV.updateUserInfo(secUserSaveVO,opId);
        }
    }
}
