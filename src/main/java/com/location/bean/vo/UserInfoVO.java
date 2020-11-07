package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName UserAddVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
@ApiModel("成员新增查询入参||成员信息查询出参")
public class UserInfoVO {
    private BigDecimal userId;
    @ApiModelProperty(name = "billId",value = "手机号",required = true)
    private BigDecimal billId;
    @ApiModelProperty(name = "userAccount",value = "账号",required = true)
    private String userAccount;
    @ApiModelProperty(name = "userPassword",value = "密码",required = true)
    private String userPassword;
    @ApiModelProperty(name = "userName",value = "中文名称",required = true)
    private String userName;
    @ApiModelProperty(name = "userEmail",value = "邮箱",required = true)
    private String userEmail;
    private Integer state;
    private BigDecimal doneCode;
    private Timestamp doneDate;
    @ApiModelProperty(name = "effDate",value = "生效时间",required = false)
    private Timestamp effDate;
    @ApiModelProperty(name = "effDate",value = "失效时间",required = false)
    private Timestamp expDate;
}