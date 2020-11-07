package com.location.service.impl;


import com.location.dao.IOrdCustDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdCustSVImpl {

    @Autowired
    IOrdCustDAO ordCustDAO;

    public String getOrderCustName() {
        return ordCustDAO.getOrderCustName();
    }

}
