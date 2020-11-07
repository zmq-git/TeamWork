package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.service.impl.SecRoleSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecRoleService {
    @Autowired
    @Qualifier("secRoleSVImpl")
    SecRoleSVImpl secRoleSV;


    public List<SecMenusDO> getAuthByRoleId(List<Long> roleId)throws Exception{
        return secRoleSV.getAuthByRoleId(roleId);
    }
    public void saveSecRole(SecRoleInfoVO secRoleInfoVO,int opId)throws Exception{
        secRoleSV.saveSecRole(secRoleInfoVO,opId);
    }
    public SecRoleInfoVO getRoleInfoByRoleId(long roleId)throws Exception{
        return secRoleSV.getRoleInfoByRoleId(roleId);
    }
}
