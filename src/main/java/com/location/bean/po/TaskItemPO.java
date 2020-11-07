package com.location.bean.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName TaskItemPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/14
 * @Version V1.0
 **/
@Data
public class TaskItemPO {
    private BigDecimal taskId;
    private String taskName;
    private String taskDesc;
    private Integer taskPriority;
    private BigDecimal creater;
    private BigDecimal relTaskId;
    private Integer state;
    private Integer taskState;
    private String taskTrip;
    private String taskFile;
    private BigDecimal dealUserId;
    private BigDecimal doneCode;
    private Timestamp createDate;
    private Timestamp doneDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Timestamp ednDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Timestamp startDate;
    private String projectName;
}