package com.location.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName ItemRelatVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Data
@ApiModel("任务分类增删改入参")
public class ItemRelatVO {
    @ApiModelProperty(name = "type",value = "操作类型，0新增，1删除，2修改",required = true)
    private Integer type;
    @ApiModelProperty(name = "className",value = "分类名称",required = true)
    private String className;
    @ApiModelProperty(name = "describe",value = "分类描述",required = true)
    private String describe;
    @ApiModelProperty(name = "classId",value = "分类id",required = true)
    private BigDecimal classId;
    @ApiModelProperty(name = "parentClassId",value = "父级类id",required = true)
    private BigDecimal parentClassId;
}