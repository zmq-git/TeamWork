package com.location.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by shijian on 2019/10/16.
 */
@Data
public class SecOrgSaveVO {
    @ApiModelProperty("组织ID")
    private Long orgId;
    @ApiModelProperty("组织名称")
    private String orgName;
    @ApiModelProperty("该组织的上层组织ID")
    private Long relatOrgId;
    @ApiModelProperty("操作类型，0新增，1删除，2修改")
    private Integer type;
}
