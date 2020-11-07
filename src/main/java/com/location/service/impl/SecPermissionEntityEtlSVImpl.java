package com.location.service.impl;

import com.location.bean.vo.SecPermissionEntityEtlDO;
import com.location.dao.ISecPermissionEntityEtlDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecPermissionEntityEtlSVImpl {
    @Autowired
    ISecPermissionEntityEtlDAO iSecPermissionEntityEtlDAO;

    public SecPermissionEntityEtlDO getSecPermissionEntityEtlByPermissionId(Integer permissionId,byte status)throws Exception{
        return iSecPermissionEntityEtlDAO.getSecPermissionEntityEtlByPermissionId(permissionId,status);
    }
}
