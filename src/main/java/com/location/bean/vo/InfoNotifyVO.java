package com.location.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName InfoNotifyVO
 * @Description: TODO
 * @Author yanjh
 * @Date 2019/12/19 0019下午 4:11
 * @Version V1.0
 **/
@Data
@ApiModel("消息通知入参")
public class InfoNotifyVO {
    @ApiModelProperty(name = "id",value = "消息信息id",required = false)
    private BigDecimal id;
    @ApiModelProperty(name = "infoTypeName",value = "消息类型:1,普通消息,2：注册消息,3:任务分配消息",required = false)
    private String infoTypeName;
    @ApiModelProperty(name = "infoType",value = "消息类型:1,普通消息,2：注册消息,3:任务分配消息",required = false)
    private Integer infoType;
    @ApiModelProperty(name = "subject",value = "消息主题",required = false)
    private String subject;
    @ApiModelProperty(name = "applicant",value = "申请人",required = false)
    private BigDecimal applicant;
    @ApiModelProperty(name = "applicantName",value = "申请人名字",required = false)
    private String applicantName;
    @ApiModelProperty(name = "auditor",value = "审核人",required = false)
    private BigDecimal auditor;
    @ApiModelProperty(name = "auditorName",value = "审核人名字",required = false)
    private String auditorName;
    @ApiModelProperty(name = "state",value = "消息状态",required = false)
    private Integer state;
    @ApiModelProperty(name = "userId",value = "消息通知人",required = false)
    private BigDecimal userId;
    @ApiModelProperty(name = "userName",value = "消息通知人名字",required = false)
    private String userName;
    @ApiModelProperty(name = "url",value = "消息相关url",required = false)
    private String url;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(name = "applyTime",value = "申请时间",required = false)
    private Timestamp applyTime;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(name = "approvalTime",value = "审核时间",required = false)
    private Timestamp approvalTime;
    @ApiModelProperty(name = "dealState",value = "消息处理状态",required = false)
    private String dealState;


}
