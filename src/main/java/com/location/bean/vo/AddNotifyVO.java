package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AddNotifyVO
 * @Description: TODO
 * @Author yanjh
 * @Date 2020/1/6 0006下午 3:50
 * @Version V1.0
 **/
@Data
@ApiModel("新增消息信息")
public class AddNotifyVO {
    @ApiModelProperty(name = "applicant",value = "申请人",required = false)
    private Integer applicant;
    @ApiModelProperty(name = "auditor",value = "审核人",required = false)
    private Integer auditor;
    @ApiModelProperty(name = "infoType",value = "消息类型",required = false)
    private Integer infoType;
    @ApiModelProperty(name = "projectId",value = "项目id",required = false)
    private Integer projectId;
    @ApiModelProperty(name = "taskName",value = "任务主题",required = false)
    private String taskName;
}
