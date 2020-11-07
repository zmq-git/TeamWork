package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LoginVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/28
 * @Version V1.0
 **/
@Data
@ApiModel("用户登录请求入参")
public class LoginVO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String authCode;
}