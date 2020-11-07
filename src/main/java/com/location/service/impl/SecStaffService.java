package com.location.service.impl;

import com.location.bean.vo.SecStaffDO;
import com.location.service.impl.SecStaffSVImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SecStaffService {

    @Autowired
    @Qualifier(value = "secStaffSVImpl")
    SecStaffSVImpl secStaffSV;
    /**
     * @description 根据员工编号信息获取员工信息
     * @param staffId
     * @return
     * @throws Exception
     */
    public SecStaffDO getStaffById(int staffId) throws Exception{
        return secStaffSV.getStaffById(staffId);
    }
    public void updateSecStaffById(SecStaffDO secStaffDO)throws Exception{
        secStaffSV.updateSecStaffById(secStaffDO);
    }
}
