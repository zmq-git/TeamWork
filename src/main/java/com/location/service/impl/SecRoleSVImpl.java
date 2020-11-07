package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.bean.vo.SecMenusDO;
import com.location.bean.vo.SecOrgRoleRelDO;
import com.location.bean.vo.SecRoleDO;
import com.location.dao.ISecRoleDAO;
import com.location.dao.SecMenuDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecRoleSVImpl {
    @Autowired
    private ISecRoleDAO iSecRoleDAO;
    @Autowired
    SecMenuDAO secMenuDAO;

    public List<SecMenusDO> getAuthByRoleId(List<Long> roleId)throws Exception{
        List<SecMenusDO> secList = new ArrayList<>();
        List<SecMenusDO> operaList = new ArrayList<>();
        //获取多个角色下的所有权限
        System.out.println("------------------------------------");
        System.out.println(roleId);
        List<Long> menuIds = iSecRoleDAO.getInsPergetInsPermissonByRoleIds(roleId,1);
        System.out.println("------------------------------------");
        System.out.println(menuIds);
        if(menuIds!=null&&menuIds.size()>0){
            //角色与角色间权限重复，进行去重
            menuIds = removeDuplicateOrder(menuIds);
            //根据权限ID查询权限数据
            List<SecMenusDO> list =secMenuDAO.getMenuInfoByIds(menuIds,1);
            System.out.println("------------------------------------");
            System.out.println(list);
            //数据组装,先获取根级数据
            for (SecMenusDO secMenusDO :list){
                if(secMenusDO.getParentId()==0){
                    secMenusDO.setSecMenuDOS(getPerMissionChild(list,secMenusDO.getId()));
                    secList.add(secMenusDO);
                }
            }
        }
        return secList;
    }
    private  List<Long> removeDuplicateOrder(List<Long> orderList) {
        Set<Long> set = new TreeSet<Long>(new Comparator<Long>() {
            @Override
            public int compare(Long a, Long b) {
                // 字符串则按照asicc码升序排列
                return a.compareTo(b);
            }
        });
        set.addAll(orderList);
        return new ArrayList<Long>(set);
    }
    //递归整理层级结构
    public List<SecMenusDO> getPerMissionChild(List<SecMenusDO> list ,long menuId){
        List<SecMenusDO> retList = new ArrayList<>();
        for (SecMenusDO secMenusDO:list){
            if(secMenusDO.getParentId()==menuId){
                secMenusDO.setSecMenuDOS(getPerMissionChild(list,secMenusDO.getId()));
                retList.add(secMenusDO);
            }
        }
        return retList;
    }
    public void saveSecRole(SecRoleInfoVO secRoleInfoVO,int opId)throws Exception{
        //新增
        if(secRoleInfoVO.getType()==0){
            if(secRoleInfoVO.getOrgId()==null){
                throw new Exception("角色所属组织不能为空");
            }
            //新增角色
            SecRoleDO secRoleDO = new SecRoleDO();
            if(secRoleInfoVO!=null){
                BeanUtils.copyProperties(secRoleInfoVO,secRoleDO);
                secRoleDO.setCreateTime(new Timestamp(new Date().getTime()));
                secRoleDO.setUpdateTime(new Timestamp(new Date().getTime()));
                secRoleDO.setStatus(1);
                secRoleDO.setRoleType(1);
                secRoleDO.setRoleLevel(1);
                secRoleDO.setOpId(opId);
                iSecRoleDAO.saveSecRole(secRoleDO);
            }
            //插入组织表
            SecOrgRoleRelDO secOrgRoleRelDO = new SecOrgRoleRelDO();
            secOrgRoleRelDO.setOrgId(secRoleInfoVO.getOrgId());
            secOrgRoleRelDO.setRoleId(secRoleDO.getId());
            secOrgRoleRelDO.setOpId(opId);
            secOrgRoleRelDO.setCreateTime(new Timestamp(new Date().getTime()));
            secOrgRoleRelDO.setUpdateTime(new Timestamp(new Date().getTime()));
            iSecRoleDAO.saveSecOrgRoleRel(secOrgRoleRelDO);
            //插入ins_permission表
            List msgList = secRoleInfoVO.getResult();
            //sharding不支持foreach，循环插入
            insertSecInsPermission(msgList,secRoleDO.getId(),opId);
        }
        //删除
        if(secRoleInfoVO.getType()==1){
            iSecRoleDAO.delSecRole(secRoleInfoVO.getRoleId());
            iSecRoleDAO.delSecOrgRoleRel(secRoleInfoVO.getRoleId());
            iSecRoleDAO.delSecInsPermission(secRoleInfoVO.getRoleId());
        }
        //修改
        if(secRoleInfoVO.getType()==2){
            SecRoleDO secRoleDO = new SecRoleDO();
            secRoleDO.setId(secRoleInfoVO.getRoleId());
            secRoleDO.setRoleName(secRoleInfoVO.getRoleName());
            secRoleDO.setDescription(secRoleInfoVO.getDescription());
            secRoleDO.setOpId(opId);
            iSecRoleDAO.updateSecRole(secRoleDO);
            if(secRoleInfoVO.getOrgId()!=null){
                iSecRoleDAO.updateSecOrgRoleRel(secRoleInfoVO.getOrgId(),secRoleInfoVO.getRoleId(),opId);
            }
            //先删除原有权限，再新增权限
            if(secRoleInfoVO.getResult()!=null&&secRoleInfoVO.getResult().size()>0){
                iSecRoleDAO.delSecInsPermission(secRoleInfoVO.getRoleId());
                insertSecInsPermission(secRoleInfoVO.getResult(),secRoleInfoVO.getRoleId(),opId);
            }

        }
    }
    public void insertSecInsPermission(List<Number> list,long roleId,int opId)throws Exception{
        for (Number menuId:list){
            SecInsPermissionDO secInsPermissionDO = new SecInsPermissionDO();
            secInsPermissionDO.setRoleId(roleId);
            secInsPermissionDO.setMenuId(menuId.longValue());
            secInsPermissionDO.setDescription("角色权限配置关系");
            secInsPermissionDO.setCreateTime(new Timestamp(new Date().getTime()));
            secInsPermissionDO.setUpdateTime(new Timestamp(new Date().getTime()));
            secInsPermissionDO.setOpId(opId);
            secInsPermissionDO.setStatus(1);
            iSecRoleDAO.saveSecInsPermission(secInsPermissionDO);
        }
    }
    /**
     *  获取角色信息以及角色下的所有权限
     * */
    public SecRoleInfoVO getRoleInfoByRoleId(long roleId)throws Exception{
        //查询角色信息
        SecRoleDO secRoleDO =getSecRoleById(roleId,1);
        if(secRoleDO==null){
            return new SecRoleInfoVO();
        }
        SecRoleInfoVO secRoleInfoVO = new SecRoleInfoVO();
        BeanUtils.copyProperties(secRoleDO,secRoleInfoVO);
        //查询角色下的权限
        List<Map> resultList = new ArrayList();
        secRoleInfoVO.setResult(resultList);
        //List<SecInsPermissionDO> list = iSecRoleDAO.getInsPermissonByRoleId(roleId,1);
        List<Long> menuIds = iSecRoleDAO.getInsPermissonMenuIdsByRoleId(roleId,1);
        //根据ID查询有效的原子表数据
        if(menuIds!=null&&menuIds.size()>0){
            List<SecMenusDO> list = secMenuDAO.getMenuInfoByIds(menuIds,1);
            if(list!=null&&list.size()>0){
                //数据组装,先获取根级数据
                for (SecMenusDO secMenusDO :list){
                    Map map = new HashMap();
                    if(secMenusDO.getParentId()==0){
                        map.put("menuId",secMenusDO.getId());
                        map.put("menuName",secMenusDO.getMenuName());
                        map.put("menuType",secMenusDO.getMenuType());
                        map.put("relatParentId",secMenusDO.getParentId());
                        map.put("child",getPerMissionChild(list,secMenusDO.getId()));
                        resultList.add(map);
                    }
                }
            }
        }
        return secRoleInfoVO;
    }
    public SecRoleDO getSecRoleById(long id,int status)throws Exception{
        return iSecRoleDAO.getSecRoleById(id,status);
    }
}
