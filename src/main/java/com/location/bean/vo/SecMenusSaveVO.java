package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by shijian on 2019/10/18.
 */
@Data
@ApiModel("权限新增，删除，修改视图")
public class SecMenusSaveVO {
    @ApiModelProperty(value = "修改和删除的主键id", required = true)
    private Long menuId;
    @ApiModelProperty(value = "权限类型，0是页面，1是操作", required = true)
    private Long menuType;
    @ApiModelProperty(value = "页面名称或操作名称", required = true)
    private String menuName;
    @ApiModelProperty(value = "当前menuId父ID", required = true)
    private Long parentId;
    @ApiModelProperty(value = "操作ID", required = true)
    private Long businessId;
    @ApiModelProperty(value = "link地址", required = true)
    private String menuLink;
    @ApiModelProperty(value = "操作方式（0：新增 1：删除：2修改）", required = true)
    private Integer type;

    private Integer menuLevel;
}
