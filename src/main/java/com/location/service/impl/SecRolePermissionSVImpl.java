package com.location.service.impl;

import com.location.bean.vo.SecPermissionEntityEtlDO;
import com.location.bean.vo.SecRolePremissionDO;
import com.location.dao.ISecRolePermissionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecRolePermissionSVImpl {
    @Autowired
    ISecRolePermissionDAO secRolePremissionDAO;

    public List<SecRolePremissionDO> getSecRolePermissionByRoleId(Integer roleId, byte status)throws Exception{
        return secRolePremissionDAO.getSecRolePermissionByRoleId(roleId,status);
    }
    public List<SecPermissionEntityEtlDO> getPremissionByRoleId(Long roleId, byte status)throws Exception{
        return secRolePremissionDAO.getPremissionByRoleId(roleId,status);

    }

}
