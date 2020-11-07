package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by shijian on 2019/10/16.
 */
@Data
@ApiModel("权限模块用户视图")
public class SecUserManagerVO {
    @ApiModelProperty("用户对象实体")
    private Map userMap;
    @ApiModelProperty("组织对象实体")
    private Map orgMap;
    @ApiModelProperty("角色对象实体集合")
    private List roleList;
    private int status;
    @ApiModelProperty("总页数")
    private int totalCount;
}
