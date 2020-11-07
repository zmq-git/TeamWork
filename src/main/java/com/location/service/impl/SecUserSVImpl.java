package com.location.service.impl;

import com.location.dao.SecUserDOMapper;
import com.location.common.utils.*;
import com.location.bean.vo.SecStaffDO;
import com.location.bean.vo.SecUserDO;
import com.location.bean.vo.SecUserRoleRelDO;
import com.location.bean.vo.SecUserSaveVO;
import com.location.bean.vo.SecUserVO;
import com.location.common.constants.SecConst;
import com.location.dao.ISecUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SecUserSVImpl {
    @Autowired
    OrderCustRemoteService orderCustRemoteService;

    @Autowired
    SecUserDOMapper secUserDOMapper;

    @Autowired
    private SecStaffService secStaffService;

    @Autowired
    ISecUserDAO secUserDAO;

    @Autowired
    private SecUserRoleRelService secUserRoleRelService;

    public List<SecUserDO> getSecUser() {
        return secUserDAO.getSecUser();
    }
    public List<Long> getSecUserByName(String userName) {
        return secUserDAO.getRoleIdsByUserName(userName);
    }
    public int insertSecUser() {
        SecUserDO record = new SecUserDO();
       /* record.setId(10);
        record.setStaffId(10);
        record.setDomainId(10);
        String userName = "呵呵哒";
        record.setUsername("呵呵哒");
        record.setPassword("123456");
        record.setIsAdmin(1L);
        record.setCreator(1);
        record.setCreateTime(new Date());
        record.setModifier(1);
        record.setUpdateTime(record.getCreateTime());
        record.setStatus(true);*/
        return secUserDOMapper.insert(record);
    }
    public void modifyPassword(String username,String password) throws Exception {
        if (username!=null&&password!=null&&username.length()>1&&password.length()>1){
            //去掉密码的前后空格
            password.trim();
            //   password= Base64Util.encode(password);
            secUserDAO.modifyPassword(username,password);
            return;
        }
        throw  new Exception("用户名或密码为空！");
    }
    public void modifyStaffName(SecUserVO secUserVO) throws Exception {
        List<SecStaffDO> list = secUserDAO.queryStaffId(secUserVO.getUsername());
        for (SecStaffDO secStaffDO : list){
            secUserDAO.modifyStaffName(secUserVO.getStaffName(),secUserVO.getPortraitAddress(),secStaffDO.getId(),secUserVO.getMail(),secUserVO.getPhoneNum());
            return;
        }
    }

    public String getSecUserNameById(long userId) {
        return secUserDAO.getSecUserNameById(userId);
    }
    public void insetUserInfo(SecUserSaveVO secUserSaveVO, int opId)throws Exception{
        String username;
        //获取用户名拼音;
        String oriName = StringUtil.getPinyin(secUserSaveVO.getStaffName());
        //去掉用户写错拼音夹带数字
        oriName = StringUtil.splitNotNumber(oriName);
        //查询最后一个该拼音的账号
        String dbUserName = secUserDAO.getLastUserNameByUserName(oriName);
        if(dbUserName==null||dbUserName.equals("")){
            username=oriName;
        }else{
            //获取数据库里该用户账号的数字
            if(dbUserName.substring(oriName.length()).equals("")){
                username = oriName+1;
            }else{
                username = oriName+ (Integer.parseInt(dbUserName.substring(oriName.length()))+1);
            }
        }
        SecStaffDO secStaffDO = new SecStaffDO();
        secStaffDO.setStaffName(secUserSaveVO.getStaffName());
        secStaffDO.setOrgId(secUserSaveVO.getOrgId());
        secStaffDO.setCreator(opId);
        secStaffDO.setModifier(opId);
        secStaffDO.setCreateTime(new Timestamp(new Date().getTime()));
        secStaffDO.setUpdateTime(new Timestamp(new Date().getTime()));
        secStaffDO.setMail(secUserSaveVO.getMail());
        secStaffDO.setPhoneNum(secUserSaveVO.getPhoneNum());
        secUserDAO.saveSecStaff(secStaffDO);
        SecUserDO secUserDO = new SecUserDO();
        secUserDO.setStaffId(secStaffDO.getId());
        secUserDO.setUsername(username);
        secUserDO.setPassword(Base64Util.encode(SecConst.DEFAULT_PASSWORD));
        secUserDO.setCreator(opId);
        secUserDO.setModifier(opId);
        secUserDO.setCreateTime(new Timestamp(new Date().getTime()));
        secUserDO.setUpdateTime(new Timestamp(new Date().getTime()));
        secUserDAO.saveSecUser(secUserDO);
        //保存该用户的角色
        List<Long> roleIds =secUserSaveVO.getRoleIds();
//        insertSecUserRoleRel(roleIds,secUserDO.getId().longValue(),opId,secUserSaveVO.getStatus());
        insertSecUserRoleRel(roleIds,secStaffDO.getId().longValue(),opId,secUserSaveVO.getStatus());
    }
    public void insertSecUserRoleRel(List<Long> roleIds,long userId,int opId,int status)throws Exception{
        if(roleIds!=null&&roleIds.size()>0){
            for (long roleId:roleIds){
                SecUserRoleRelDO secUserRoleRelDO = new SecUserRoleRelDO();
                secUserRoleRelDO.setUserId(userId);
                secUserRoleRelDO.setRoleId(roleId);
                secUserRoleRelDO.setOpId(opId);
                secUserRoleRelDO.setStatus(status);
                secUserRoleRelDO.setCreateTime(new Timestamp(new Date().getTime()));
                secUserRoleRelDO.setUpdateTime(new Timestamp(new Date().getTime()));
                secUserRoleRelService.saveSecUserRoleRel(secUserRoleRelDO);
            }
        }
    }
    public String getUserName() {
        return secUserDAO.getUserName() + ":" + orderCustRemoteService.getOrderCustName();
    }
    public void delUserInfo(Integer userId)throws Exception{
        //删除用户
        secUserDAO.delSecUserInfo(userId);
        //删除用户下的所有角色
        secUserRoleRelService.delUserRoleRelService(userId);
    }
    public void updateUserInfo(SecUserSaveVO secUserSaveVO,int opId)throws Exception{
        //修改名称及组织
        SecStaffDO secStaffDO = new SecStaffDO();
        secStaffDO.setId(secUserSaveVO.getUserId());//secUserDAO.getStaffIdByUserId(secUserSaveVO.getUserId())
        secStaffDO.setStaffName(secUserSaveVO.getStaffName());
        secStaffDO.setOrgId(secUserSaveVO.getOrgId());
        secStaffDO.setModifier(opId);
        secStaffDO.setStatus(secUserSaveVO.getStatus().byteValue());
        secStaffDO.setPhoneNum(secUserSaveVO.getPhoneNum());
        secStaffDO.setMail(secUserSaveVO.getMail());
        secStaffService.updateSecStaffById(secStaffDO);
        secUserDAO.updateSecUserInfo(secUserSaveVO.getUserId(),secUserSaveVO.getStatus());
        //修改角色
        secUserRoleRelService.delUserRoleRelService(secUserSaveVO.getUserId());
        insertSecUserRoleRel(secUserSaveVO.getRoleIds(),secUserSaveVO.getUserId().longValue(),opId,secUserSaveVO.getStatus());
    }

}
