package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName EditTaskVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/15
 * @Version V1.0
 **/
@Data
@ApiModel("任务改入参")
public class EditTaskVO {
    @ApiModelProperty(name = "id",value = "任务id",required = true)
    private BigDecimal id;
    @ApiModelProperty(name = "taskName",value = "任务主题",required = true)
    private String taskName;
    @ApiModelProperty(name = "taskDesc",value = "任务描述",required = true)
    private String taskDesc;
    @ApiModelProperty(name = "taskPriority",value = "任务优先级",required = true)
    private Integer taskPriority;
    @ApiModelProperty(name = "creater",value = "任务创建人",required = true)
    private BigDecimal creater;
    @ApiModelProperty(name = "state",value = "任务状态",required = true)
    private Integer state;
    @ApiModelProperty(name = "taskTrip",value = "任务分类",required = true)
    private BigDecimal taskTrip;
    @ApiModelProperty(name = "taskFile",value = "任务关联文件",required = true)
    private String taskFile;
    @ApiModelProperty(name = "dealUser",value = "任务处理人",required = true)
    private BigDecimal dealUser;
    @ApiModelProperty(name = "createDate",value = "创建时间",required = true)
    private Timestamp createDate;
    @ApiModelProperty(name = "startDate",value = "开始时间",required = true)
    private Timestamp startDate;
    @ApiModelProperty(name = "endDate",value = "结束时间",required = true)
    private Timestamp endDate;
    @ApiModelProperty(name = "doneCode",value = "操作编码",required = false)
    private BigDecimal doneCode;
    @ApiModelProperty(name = "parentId",value = "任务依赖id",required = false)
    private BigDecimal parentId;
    @ApiModelProperty(name = "taskState",value = "任务运行状态",required = false)
    private int taskState;
    @ApiModelProperty(name = "interationId",value = "迭代id",required = false)
    private BigDecimal interationId;
}