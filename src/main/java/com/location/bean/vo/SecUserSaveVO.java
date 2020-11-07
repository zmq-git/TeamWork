package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by shijian on 2019/10/21.
 */
@Data
@ApiModel("权限模块用户新增视图")
public class SecUserSaveVO {
    @ApiModelProperty("用户ID。修改，删除必传，新增不传")
    private Integer userId;
    @ApiModelProperty("归属组织ID")
    private Integer orgId;
    @ApiModelProperty("用户名称")
    private String staffName;
    @ApiModelProperty("用户权限ID，入参roleIds:[roleId1,roleId2]")
    private List<Long> roleIds;
    @ApiModelProperty("手机号")
    private String phoneNum;
    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("状态，启用1，禁用0")
    private Integer status;
    @ApiModelProperty("操作类型，0新增，1删除，2修改")
    private Integer type;
}
