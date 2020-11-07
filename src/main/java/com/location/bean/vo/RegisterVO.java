package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName RegisterVO
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/12/4 0004下午 2:51
 * @Version V1.0
 **/
@Data
@ApiModel("成员新增入参")
public class RegisterVO {
    @ApiModelProperty(name = "staffId",value = "成员编号",required = false)
    private Integer staffId;
    @ApiModelProperty(name = "staffName",value = "姓名",required = true)
    private String staffName;
    @ApiModelProperty(name = "role",value = "角色",required = false)
    private Integer role;
    @ApiModelProperty(name = "companyName",value = "公司名字",required = false)
    private String companyName;
    @ApiModelProperty(name = "orgId",value = "部门id",required = false)
    private Integer orgId;
    @ApiModelProperty(name = "mail",value = "邮箱",required = false)
    private String mail;
    @ApiModelProperty(name = "phoneNum",value = "手机号",required = false)
    private String phoneNum;
    @ApiModelProperty(name = "username",value = "账号",required = true)
    private String username;
    @ApiModelProperty(name = "password",value = "密码",required = true)
    private String password;
    @ApiModelProperty(name = "auditor",value = "审核人",required = false)
    private BigDecimal auditor;

}
