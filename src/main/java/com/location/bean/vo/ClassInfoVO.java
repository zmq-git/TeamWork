package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClassInfoVO
 * @Description: 分类单元信息
 * @Author wanggb
 * @Date 2019/11/26
 * @Version V1.0
 **/
@Data
@ApiModel("分类单元信息")
public class ClassInfoVO {
    @ApiModelProperty(name = "id",value = "分类id",required = false)
    private BigDecimal id;
    @ApiModelProperty(name = "className",value = "分类名称",required = true)
    private String className;
    @ApiModelProperty(name = "tempId",value = "分类id",required = false)
    private BigDecimal tempId;
    @ApiModelProperty(name = "tempName",value = "分类名称",required = true)
    private String tempName;
    @ApiModelProperty(name = "stateList",value = "状态值：如[{0:\"未开始\"},{1:\"进行中\"}]",required = true)
    private List<Map> stateList;
}