package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijian on 2019/10/17.
 */
@Data
@ApiModel("权限模块角色视图")
public class SecRoleInfoVO {
    @ApiModelProperty("角色ID，修改删除必传，新增不传")
    private Long roleId;
    @ApiModelProperty("角色名称，新增修改传")
    private String roleName;
    @ApiModelProperty("所属组织，新增修改传")
    private Long orgId;
    @ApiModelProperty("描述，新增修改传")
    private String description;
    @ApiModelProperty("角色下的权限ID集合，新增修改传,数据模式result:[menuId1,menuId2]")
    private List result = new ArrayList();
    @ApiModelProperty("操作类型，0新增，1删除，2修改")
    private Integer type;
}
