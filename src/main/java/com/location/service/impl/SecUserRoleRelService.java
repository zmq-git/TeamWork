package com.location.service.impl;

import com.location.bean.vo.SecUserRoleRelDO;
import com.location.common.constants.SecConst;
import com.location.service.impl.SecUserRoleRelSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecUserRoleRelService {
    @Autowired
    @Qualifier("secUserRoleRelSVImpl")
    SecUserRoleRelSVImpl secUserRoleRelSV;

    public List<SecUserRoleRelDO> getSecUserRoleRelById(long userId)throws Exception{
        return secUserRoleRelSV.getSecUserRoleRelById(userId, SecConst.USER_STATUS_VALID);
    }
    public List<Map> getRoleByUserId(long userId, int status)throws Exception{
        return secUserRoleRelSV.getRoleByUserId(userId,status);
    }
    public void saveSecUserRoleRel(SecUserRoleRelDO secUserRoleRelDO)throws Exception{
        secUserRoleRelSV.saveSecUserRoleRel(secUserRoleRelDO);
    }
    public void delUserRoleRelService(long userId)throws Exception{
        secUserRoleRelSV.delUserRoleRelService(userId);
    }

}
