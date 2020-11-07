package com.location.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName InterationInfoVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/19
 * @Version V1.0
 **/
@Data
@ApiModel("迭代信息")
public class InterationInfoVO {
    @ApiModelProperty(name = "interationId",value = "迭代ID,新增时不传",required = false)
    private BigDecimal interationId    ;
    @ApiModelProperty(name = "projectId",value = "项目ID",required = true)
    private BigDecimal projectId    ;
    @ApiModelProperty(name = "interationName",value = "迭代名称",required = true)
    private String interationName  ;
    @ApiModelProperty(name = "interationDesc ",value = "迭代描述",required = true)
    private String interationDesc ;
    @ApiModelProperty(name = "interationPeriod ",value = "迭代状态",required = true)
    private Integer interationPeriod ;
    @ApiModelProperty(name = "interationType ",value = "迭代类型",required = true)
    private BigDecimal interationType ;
    @ApiModelProperty(name = "creater",value = "创建者id",required = false)
    private BigDecimal  creater ;
    @ApiModelProperty(name = "userName",value = "创建者",required = true)
    private String userName ;
    @ApiModelProperty(name = "interationPicture",value = "迭代图片",required = true)
    private String interationPicture  ;
    @ApiModelProperty(name = "createDate",value = "创建时间",required = false)
    private Timestamp createDate ;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(name = "startDate",value = "开始时间",required = true)
    private Timestamp startDate ;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(name = "endDate",value = "结束时间",required = true)
    private Timestamp endDate ;
    @ApiModelProperty(name = "interationProgress",value = "迭代进度",required = false)
    private Integer interationProgress ;

}