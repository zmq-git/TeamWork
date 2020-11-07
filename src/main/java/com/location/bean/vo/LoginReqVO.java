package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 登录请求参数对象
 * Created by bawy on 2018/7/6 13:48.
 */
/*@Setter
@Getter*/
@ApiModel("用户登录请求入参")
public class LoginReqVO implements Serializable{

    private static final long serialVersionUID = 8128408640322358619L;

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
