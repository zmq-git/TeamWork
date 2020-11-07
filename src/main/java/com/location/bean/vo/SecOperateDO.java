package com.location.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecOperateDO {
    @ApiModelProperty("操作ID")
    private Integer id;
    private Integer parentId;
    @ApiModelProperty("操作名称")
    private String operateName;
    private String description;
    private Integer creator;
    private Timestamp createTime;
    private Integer modifier;
    private Timestamp updateTime;
    private Integer status;
}
