package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName ItemRelatPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class ItemRelatPO {
    private BigDecimal id;
    private BigDecimal relatId;
    private BigDecimal projectId;
    private BigDecimal itemId;
    private BigDecimal relatItemId;
    private String relatType;
    private Integer state;
    private BigDecimal creater;
    private BigDecimal doneCode;
    private Timestamp createDate;
    private Timestamp doneDate;
    private String describe;
    private Integer state_code;
    private String stateName;
    private String torsionRelation;
}