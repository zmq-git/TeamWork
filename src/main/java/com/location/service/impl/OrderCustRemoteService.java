package com.location.service.impl;

import com.location.service.impl.OrderCustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCustRemoteService {

    @Autowired
    OrderCustService orderCustService;

    public String getOrderCustName() {
        return orderCustService.getOrderCustName();
    }

}
