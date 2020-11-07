package com.location.service.impl;

import com.location.bean.vo.SecRolePremissionDO;
import com.location.common.constants.SecConst;
import com.location.service.impl.SecRolePermissionSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecRolePermissionService {
    @Autowired
    @Qualifier("secRolePermissionSVImpl")
    SecRolePermissionSVImpl secRolePremissionSV;

    public List<SecRolePremissionDO> getSecRolePermissionByRoleId(Integer roleId)throws Exception{
        return secRolePremissionSV.getSecRolePermissionByRoleId(roleId, SecConst.USER_STATUS_VALID);
    }
}
