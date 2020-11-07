package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName TaskExeInfoPO
 * @Description: 任务处理表
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class TaskExeInfoPO {
    private BigDecimal relatId;
    private BigDecimal taskId;
    private BigDecimal dealPerson;
    private Integer taskState;
    private BigDecimal doneCode;
    private String taskType;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp startDate;
    private Timestamp endDate;
}