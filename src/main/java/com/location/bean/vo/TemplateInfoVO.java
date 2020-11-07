package com.location.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName TemplateInfoVO
 * @Description: 模板信息查询
 * @Author wanggb
 * @Date 2019/12/17
 * @Version V1.0
 **/
@Data
public class TemplateInfoVO {
    @ApiModelProperty(name = "id",value = "模板id",required = false)
    private BigDecimal id;
/*    @ApiModelProperty(name = "relatId",value = "关联状态的模板",required = false)
    private BigDecimal relatId;*/
    @ApiModelProperty(name = "templateName",value = "模板名称",required = true)
    private String templateName;
    @ApiModelProperty(name = "des",value = "描述信息",required = true)
    private String des;
    @ApiModelProperty(name = "creater",value = "创建人ID",required = false)
    private BigDecimal creater;
    @ApiModelProperty(name = "creator",value = "创建人名称",required = false)
    private String creatorName;
    @ApiModelProperty(name = "createDate",value = "创建时间",required = false)
    private Timestamp createDate;
    @ApiModelProperty(name = "state",value = "状态",required = false)
    private Integer state;
    @ApiModelProperty(name = "nodeList",value = "节点信息",required = false)
    private List<NodeInfoVO> nodeList;
}