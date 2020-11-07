package com.location.service.impl;

import com.location.bean.vo.SecStaffDO;
import com.location.dao.ISecStaffDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecStaffSVImpl {

    @Autowired
    private ISecStaffDAO secStaffDAO;
    public SecStaffDO getStaffById(int staffId) throws Exception{
        return secStaffDAO.getSecStaffById(staffId);
    }
    public void updateSecStaffById(SecStaffDO secStaffDO)throws Exception{
        secStaffDAO.updateSecStaffById(secStaffDO);
    }
}
