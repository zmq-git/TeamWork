package com.location.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName NodeInfoVO
 * @Description: 节点信息
 * @Author wanggb
 * @Date 2019/12/17
 * @Version V1.0
 **/
@Data
public class NodeInfoVO {
    @ApiModelProperty(name = "id",value = "节点ID",required = true)
    private BigDecimal id;
    @ApiModelProperty(name = "name",value = "节点名称",required = true)
    private String name;
    @ApiModelProperty(name = "description",value = "节点描述",required = true)
    private String description;
    @ApiModelProperty(name = "creater",value = "序号",required = true)
    private Integer num;
    @ApiModelProperty(name = "reachNode",value = "可达节点信息",required = true)
    private List<NodeInfoVO> reachNode;
}