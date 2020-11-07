package com.location.service.impl;


import com.location.service.impl.OrdCustSVImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderCustService {

    @Autowired
    OrdCustSVImpl ordCustSV;

    public String getOrderCustName() {
        return ordCustSV.getOrderCustName();
    }


}
