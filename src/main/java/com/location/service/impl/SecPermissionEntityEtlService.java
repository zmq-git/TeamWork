package com.location.service.impl;

import com.location.bean.vo.SecPermissionEntityEtlDO;
import com.location.common.constants.SecConst;
import com.location.service.impl.SecPermissionEntityEtlSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Service
public class SecPermissionEntityEtlService {
    @Autowired
    @Qualifier("secPermissionEntityEtlSVImpl")
    SecPermissionEntityEtlSVImpl secPermissionEntityEtlSV;

    public SecPermissionEntityEtlDO getSecPermissionEntityEtlByPermissionId(Integer permissionId)throws Exception{
        return secPermissionEntityEtlSV.getSecPermissionEntityEtlByPermissionId(permissionId, SecConst.USER_STATUS_VALID);
    }
}
