package com.location.service.impl;

import com.location.bean.vo.SecUserDO;
import com.location.bean.vo.SecUserVO;
import com.location.service.impl.SecUserServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by wangcl on 2019/8/22.
 */
@Service
public class SecUserRemoteService {

    @Autowired
    SecUserServie secUserSV;

    public SecUserVO login(String username, String password) throws Exception{
        return secUserSV.login(username,password);
    }

}
