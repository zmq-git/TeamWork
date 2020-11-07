package com.location.service.impl;

import com.location.bean.vo.SecMenusDO;
import com.location.bean.vo.SecUserDO;
import com.location.bean.vo.SecMenuVO;
import com.location.bean.vo.SecUserSaveVO;
import com.location.bean.vo.SecUserVO;
import com.location.service.impl.SecUserSVImpl;
import com.location.service.impl.SecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangcl on 2019/8/22.
 */
@Service
public class SecUserServie {

    @Autowired
    @Qualifier(value = "secUserSVImpl")
    SecUserSVImpl secUserSV;

    @Autowired
    @Qualifier(value = "secUserService")
    SecUserService secUserService;

    public String getSecUserNameById(long userId) {
        return secUserSV.getSecUserNameById(userId);
    }

    public String getUserName() {
        return secUserSV.getUserName();
    }

    public List<SecUserDO> getSecUser() {
        return secUserSV.getSecUser();
    }

    public List<Long> getSecUserByName(String userName) {
        return secUserSV.getSecUserByName(userName);
    }

    public int insertSecUser() {
        return secUserSV.insertSecUser();
    }

    public SecUserVO login(String username, String password) throws Exception{
        return secUserService.login(username,password);
    }
    public SecMenuVO getSecUserMenuByUserNmae(String username) throws Exception{
        return secUserService.getSecUserMenuByUserNmae(username);
    }
    public List<SecMenusDO> getAuthByUserId(long userId)throws Exception{
        return secUserService.getAuthByUserId(userId);
    }
    public void saveUserInfo(SecUserSaveVO secUserSaveVO,int opId)throws Exception{
        secUserService.saveUserInfo(secUserSaveVO,opId);
    }
}
