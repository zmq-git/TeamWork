package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName ProjectUserAddVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/8
 * @Version V1.0
 **/
@Data
@ApiModel("项目成员新增入参")
public class ProjectUserAddVO {
    @ApiModelProperty(name = "projectId",value = "项目ID",required = true)
    private BigDecimal projectId    ;
    @ApiModelProperty(name = "userIdList",value = "用户ID",required = true)
    private List<Integer> userIdList ;
    @ApiModelProperty(name = "projectRole ",value = "角色",required = true)
    private List<Integer> projectRole ;
    @ApiModelProperty(name = "creater",value = "操作员",required = false)
    private BigDecimal creater ;
    @ApiModelProperty(name = "effDate",value = "生效时间",required = false)
    private Timestamp effDate ;
    @ApiModelProperty(name = "expDate",value = "失效时间",required = false)
    private Timestamp expDate ;
}