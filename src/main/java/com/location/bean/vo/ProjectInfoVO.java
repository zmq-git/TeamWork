package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName ProjectInfoVO
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Data
@ApiModel("项目新增入参||项目信息查询出参")
public class ProjectInfoVO {
    @ApiModelProperty(name = "projectId",value = "项目编号",required = false)
    private BigDecimal projectId;
    @ApiModelProperty(name = "projectName",value = "项目名称",required = true)
    private String projectName;
    @ApiModelProperty(name = "projectDetail",value = "项目描述",required = false)
    private String projectDetail;
    @ApiModelProperty(name = "projectPicture",value = "项目图片",required = false)
    private String projectPicture;
    @ApiModelProperty(name = "projectPeriod",value = "项目阶段",required = false)
    private Integer projectPeriod;
    @ApiModelProperty(name = "state",value = "项目状态",required = false)
    private Integer state;
    @ApiModelProperty(name = "projectType",value = "项目类型",required = false)
    private Integer projectType;
    @ApiModelProperty(name = "projectTypeName",value = "项目类型名字",required = false)
    private String projectTypeName;
    @ApiModelProperty(name = "doneCode",value = "操作编码",required = false)
    private BigDecimal doneCode;
    @ApiModelProperty(name = "creater",value = "项目创建人",required = false)
    private String creater;
    @ApiModelProperty(name = "userId",value = "项目创建人ID",required = false)
    private BigDecimal userId;
    @ApiModelProperty(name = "createDate",value = "创建时间",required = false)
    private Timestamp createDate;
    @ApiModelProperty(name = "doneDate",value = "操作时间",required = false)
    private Timestamp doneDate;
/*    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")*/
    @ApiModelProperty(name = "startDate",value = "计划开始时间",required = false)
    private Timestamp startDate;
/*    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")*/
    @ApiModelProperty(name = "endDate",value = "计划结束时间",required = false)
    private Timestamp endDate;
    @ApiModelProperty(name = "userName",value = "用户名字",required = false)
    private String userName;
    @ApiModelProperty(name = "orgId",value = "组织id",required = false)
    private Integer orgId;

}
