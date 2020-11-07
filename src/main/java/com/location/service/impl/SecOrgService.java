package com.location.service.impl;

import com.location.bean.vo.*;
import com.location.service.impl.SecOrgSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SecOrgService {
    @Autowired
    @Qualifier(value = "secOrgSVImpl")
    SecOrgSVImpl secOrgSV;

    public SecOrgDO getSecOrgById(int OrgId) throws Exception{
        return secOrgSV.getSecOrgById(OrgId);
    }

    public int getTopOrgIdByOrgId(int oid) throws Exception{
        return secOrgSV.getTopOrgIdByOrgId(oid);
    }
    public Map getSecOrgByOrgId(long orgId) throws Exception{
        return secOrgSV.getSecOrgByOrgId(orgId);
    }
    public void saveSecOrg(SecOrgSaveVO secOrgSaveVO,int opId)throws Exception{
        secOrgSV.saveSecOrg(secOrgSaveVO,opId);
    }
    public List<SecUserManagerVO> getSecAccAuthByOrgIdAndUserId(long relatOrgId,String userName, int status,int page,int pageSize)throws Exception{
        return secOrgSV.getSecAccAuthByOrgIdAndUserId(relatOrgId,userName,status,page,pageSize);
    }
    public List<Map> getAllRoles(long orgId)throws Exception{
        return secOrgSV.getAllRoles(orgId);
    }
}
