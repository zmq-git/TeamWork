package com.location.service.impl;

import com.location.bean.vo.SecUserRoleRelDO;
import com.location.dao.ISecUserRoleRelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecUserRoleRelSVImpl {
    @Autowired
    private ISecUserRoleRelDAO iSecUserRoleRelDAO;
    public List<SecUserRoleRelDO> getSecUserRoleRelById(long userId, byte status)throws Exception{
        return iSecUserRoleRelDAO.getSecUserRoleRelById(userId,status);
    }
    public List<Map> getRoleByUserId(long userId, int status)throws Exception{
        return iSecUserRoleRelDAO.getRoleInfoByUserId(userId,status);
    }
    public void saveSecUserRoleRel(SecUserRoleRelDO secUserRoleRelDO)throws Exception{
        iSecUserRoleRelDAO.saveSecUserRoleRel(secUserRoleRelDO);
    }
    public void delUserRoleRelService(long userId)throws Exception{
        iSecUserRoleRelDAO.delUserRoleRelService(userId);
    }
}
