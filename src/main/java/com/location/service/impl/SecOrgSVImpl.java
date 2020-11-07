package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.location.common.utils.TimeUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.location.common.constants.SecConst;
import com.location.common.utils.*;

@Service
public class SecOrgSVImpl {

    @Autowired
    private ISecRoleDAO secRoleDAO;

    @Autowired
    private ISecUserRoleRelDAO secUserRoleRelDAO;

    @Autowired
    private ISecStaffDAO secStaffDAO;

    @Autowired
    private ISecUserDAO secUserDAO;

    @Autowired
    private ISecOrgDAO secOrgDAO;

    public SecOrgDO getSecOrgById(int OrgId) throws Exception{
        return secOrgDAO.getSecOrgById(OrgId);
    }

    /**
     * 根据部门id查询其所在公司
     * @param orgId
     * @return
     * @throws Exception
     */
    public int getTopOrgIdByOrgId(int orgId) throws Exception {
        List<Long> allSecOrgTopId = secOrgDAO.getAllSecOrgTopId();
        Integer topOrgIdByOrgId = secOrgDAO.getTopOrgIdByOrgId(orgId, 1);
        if (allSecOrgTopId.contains(new Long(topOrgIdByOrgId))){
            return  topOrgIdByOrgId;
        }
        return getTopOrgIdByOrgId(topOrgIdByOrgId);
    }
    public Map getSecOrgByOrgId(long orgId)throws Exception{
        Map map =secOrgDAO.getSecOrgByOrgId(orgId,1);
        if(map!=null){
            Map map1 = getChildOrgIds(orgId);
            if(map1!=null){
                map.put("child",map1);
            }
        }
        return map;
    }
    //未知层级深度，递归遍历组装报文，递归效率低，消耗大，慎用
    public Map getChildOrgIds(long orgId) throws Exception{
        Map retMap = new LinkedHashMap();
        List<Long> secOrgRelatByOrgRelatId = secOrgDAO.getSecOrgRelatByOrgRelatId(orgId,1);
        if(secOrgRelatByOrgRelatId != null && !secOrgRelatByOrgRelatId.isEmpty()){
            retMap.put("orgRelatId",orgId);
            List li = new ArrayList();
            retMap.put("result",li);
            for(long childOrgId : secOrgRelatByOrgRelatId){
                Map map =secOrgDAO.getSecOrgByOrgId(childOrgId,1);
                if(map != null){
                    li.add(map);
                    Map map1 = getChildOrgIds(childOrgId);
                    if(map1!=null){
                        map.put("child",map1);
                    }
                }
            }
        }
        return retMap;
    }
    public void saveSecOrg(SecOrgSaveVO secOrgSaveVO,int opId)throws Exception{
        //新增
        if(secOrgSaveVO.getType()==0){
            long orgId = getNewOrgId();
            SecOrgDO secOrgDO = new SecOrgDO();
            secOrgDO.setOpId(opId);
            secOrgDO.setOrgId(orgId);
            secOrgDO.setOrgName(secOrgSaveVO.getOrgName());
            secOrgDO.setStatus(1);
            secOrgDO.setCreateTime(TimeUtils.getCurrentTime());
            secOrgDO.setUpdateTime(TimeUtils.getCurrentTime());
            secOrgDAO.saveSecOrg(secOrgDO);
            if(secOrgSaveVO.getRelatOrgId()!=0){
                SecOrgRelatDO secOrgRelatDO = new SecOrgRelatDO();
                secOrgRelatDO.setOpId(opId);
                secOrgRelatDO.setOrgId(orgId);
                secOrgRelatDO.setRelatParentOrgId(secOrgSaveVO.getRelatOrgId());
                secOrgRelatDO.setStatus(1);
                secOrgRelatDO.setCreateTime(TimeUtils.getCurrentTime());
                secOrgRelatDO.setUpdateTime(TimeUtils.getCurrentTime());
                secOrgDAO.saveSecOrgRelat(secOrgRelatDO);
            }
        }
        //删除
        if(secOrgSaveVO.getType()==1){
            //该组织对应的子组织全部失效
            delSecOrgRelatByOrgId(secOrgSaveVO.getOrgId());
        }
        //修改
        if(secOrgSaveVO.getType()==2){
            if(secOrgSaveVO.getRelatOrgId()==0){
                throw new Exception("组织列表不属于上级组织");
            }
            SecOrgDO secOrgDO = new SecOrgDO();
            secOrgDO.setOrgName(secOrgSaveVO.getOrgName());
            secOrgDO.setOrgId(secOrgSaveVO.getOrgId());
            secOrgDAO.updateSecOrg(secOrgDO);
            SecOrgRelatDO secOrgRelatDO = new SecOrgRelatDO();
            secOrgRelatDO.setOrgId(secOrgSaveVO.getOrgId());
            secOrgRelatDO.setRelatParentOrgId(secOrgSaveVO.getRelatOrgId());
            secOrgDAO.updateSecOrgRelat(secOrgRelatDO);
        }
    }
    public long getNewOrgId()throws Exception{
        return secOrgDAO.getNewId(SecConst.ORG_ID_SEQUENCE);
    }
    public void delSecOrgRelatByOrgId(Long orgId)throws Exception{
        List<Long> childOrgList =secOrgDAO.getSecOrgRelatByOrgRelatId(orgId,1);
        secOrgDAO.delSecOrg(orgId);
        secOrgDAO.delSecOrgRelat(orgId);
        //对应组织的账号全部失效
        List<Integer> ids =secStaffDAO.getSecStaffByOrgId(orgId);
        secStaffDAO.delSecStaffByOrgId(orgId);
        for (int staffId:ids){
            secUserDAO.delSecUserByStaffId(staffId);
        }
        for (long childOrgId:childOrgList){
            delSecOrgRelatByOrgId(childOrgId);
        }
    }
    public List<SecUserManagerVO> getSecAccAuthByOrgIdAndUserId(Long relatOrgId,String userName, int status,int page ,int pageSize)throws Exception{
        userName=BeanTransUtil.getAsNull(userName);
        List<SecUserManagerVO> retList = new ArrayList<>();
        if(relatOrgId!=null){
            //获取组织ID和组织名称
            Map orgMap = secOrgDAO.getSecOrgByOrgId(relatOrgId,1);
            //获取该组织的有效账号信息
            List<Map> usernameList = secStaffDAO.getUsernameByOrgId(relatOrgId,status,userName);
            if(usernameList!=null&&usernameList.size()>0){
                for (Map userMap:usernameList){
                    userMap.remove("ORDER_BY_DERIVED_0");//shardingJdbc组合查询带出来的key，move掉
                    SecUserManagerVO secUserManagerVo = new SecUserManagerVO();
                    secUserManagerVo.setOrgMap(orgMap);
                    secUserManagerVo.setUserMap(userMap);
                    //获取该账号下的所有生效角色
//                    Long userId = secUserDAO.getUserIdByStaffId(Long.parseLong(StringUtil.getAsString(userMap,"id")),1);
                    Long userId = Long.parseLong(StringUtil.getAsString(userMap,"id"));
                    List<Map> roleList = secUserRoleRelDAO.getRoleInfoByUserId(userId,1);
                    secUserManagerVo.setRoleList(roleList);
                    secUserManagerVo.setStatus(status);
                    retList.add(secUserManagerVo);
                }
            }
            getOrgInfoByOrgId(relatOrgId,status,userName,retList);
        }else{
            retList =getOrgInfoByUsername(userName);
        }
        int totalCount= retList.size();
        if(page>0&&pageSize>0){
            int fromIndex = (page-1)*pageSize;
            int toIndex = fromIndex+pageSize;
            if(toIndex>totalCount){
                toIndex=totalCount;
            }
            if(page>(totalCount/pageSize+1)){
                fromIndex=0;
                toIndex=0;
            }
            retList = retList.subList(fromIndex,toIndex);
        }
        if(retList.size()>0){
            retList.get(0).setTotalCount(totalCount);
        }
        return retList;
    }
    //组织对多个子组织，一个组织对多个账号，一个账号对多个角色，递归遍历该组织下所有子组织的账号角色信息并平铺给页面
    public void getOrgInfoByOrgId(long relatOrgId ,int status,String userName,List<SecUserManagerVO> retList)throws Exception{
        //根据父组织获取下面所有的子组织
        List<Long> list=secOrgDAO.getSecOrgRelatByOrgRelatId(relatOrgId,1);
        for (long orgId:list){
            //获取组织ID和组织名称
            Map orgMap = secOrgDAO.getSecOrgByOrgId(orgId,1);
            //获取该组织的有效账号信息
            List<Map> usernameList = secStaffDAO.getUsernameByOrgId(orgId,status,userName);
            if(usernameList!=null&&usernameList.size()>0){
                for (Map userMap:usernameList){
                    userMap.remove("ORDER_BY_DERIVED_0");//shardingJdbc组合查询带出来的key，move掉
                    SecUserManagerVO secUserManagerVo = new SecUserManagerVO();
                    secUserManagerVo.setOrgMap(orgMap);
                    secUserManagerVo.setUserMap(userMap);
                    //获取该账号下的所有生效角色
//                    Long userId = secUserDAO.getUserIdByStaffId(Long.parseLong(StringUtil.getAsString(userMap,"id")),1);
                    Long userId = Long.parseLong(StringUtil.getAsString(userMap,"id"));
                    List<Map> roleList = secUserRoleRelDAO.getRoleInfoByUserId(userId,1);
                    secUserManagerVo.setRoleList(roleList);
                    secUserManagerVo.setStatus(status);
                    retList.add(secUserManagerVo);
                }
            }
            getOrgInfoByOrgId(orgId,status,userName,retList);
        }
    }
    public List getOrgInfoByUsername(String username)throws Exception{
        List<SecUserManagerVO> retList =new ArrayList<>();
        //获取该组织的有效账号信息
        List<Map> usernameList = secStaffDAO.getUserInfoByUsername(username,1);
        if(usernameList!=null&&usernameList.size()>0){
            for (Map userMap:usernameList){
                SecUserManagerVO secUserManagerVo = new SecUserManagerVO();
                long orgId=StringUtil.getAsString(userMap,"orgId").equals("")?0L:Long.parseLong(StringUtil.getAsString(userMap,"orgId"));
                secUserManagerVo.setOrgMap(secOrgDAO.getSecOrgByOrgId(orgId,1));
                secUserManagerVo.setUserMap(userMap);
                //获取该账号下的所有生效角色
                //               Long userId = secUserDAO.getUserIdByStaffId(Long.parseLong(StringUtil.getAsString(userMap,"id")),1);
                Long userId = Long.parseLong(StringUtil.getAsString(userMap,"id"));
                List<Map> roleList = secUserRoleRelDAO.getRoleInfoByUserId(userId,1);
                secUserManagerVo.setRoleList(roleList);
                secUserManagerVo.setStatus(1);
                retList.add(secUserManagerVo);
            }
        }
        return retList;
    }
    public List<Map> getAllRoles(long orgId)throws Exception{
        //查询组织下所有子组织
        List<Map> orgList = new ArrayList<>();
        List<Map> roleList = new ArrayList<>();
        List<Map> list = new ArrayList<>();
        Map secOrgMap = secOrgDAO.getSecOrgByOrgId(orgId,SecConst.ROLE_STATUS_VALID);
        if(secOrgMap!=null){
            orgList.add(secOrgMap);
            getAllOrgIds(orgId,orgList);
            List<Long> orgIdList = new ArrayList<>();
            for (Map map :orgList){
                orgIdList.add(Long.parseLong(map.get("orgId").toString()));
            }
            //根据组织ID查询所有角色ID
            if(orgList.size()>0){
                roleList=secRoleDAO.getSecOrgRoleRelRoleIds(orgIdList,SecConst.ROLE_STATUS_VALID);
            }
            List<Long> roleIdList = new ArrayList<>();
            for (Map map :roleList){
                roleIdList.add(Long.parseLong(map.get("roleId").toString()));
            }
            if(roleList.size()>0){
                //查询所有角色ID和角色名称
                list =secRoleDAO.getAllRoleIdAndRoleName(roleIdList,SecConst.ROLE_STATUS_VALID);
                if(list!=null&&list.size()>0){
                    for (Map map :list){
                        for (Map map1:roleList){
                            if(map1.get("roleId").toString().equals(map.get("id").toString())){
                                map.put("orgId",map1.get("orgId").toString());
                                for (Map map2 :orgList){
                                    if(map2.get("orgId").toString().equals(map1.get("orgId").toString())){
                                        map.put("orgName",map2.get("orgName").toString());
                                    }
                                }
                            }
                        }
                        map.put("menuIds",secRoleDAO.getInsPermissonMenuIdsByRoleId(Long.parseLong(map.get("id").toString()),1));
                    }
                }
            }
        }
        return list;
    }
    //递归查询所有组织的ID
    public void getAllOrgIds(long orgRelatId,List<Map> list)throws Exception{
        List<Map> orgIds = secOrgDAO.getSecOrgRelatNameByOrgRelatId(orgRelatId,1);
        for (Map orgMap:orgIds){
            list.add(orgMap);
            getAllOrgIds(Long.parseLong(orgMap.get("orgId").toString()),list);
        }
    }
}
