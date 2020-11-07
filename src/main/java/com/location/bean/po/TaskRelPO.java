package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName TaskRelPO
 * @Description: 任务关联表
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class TaskRelPO {
    private BigDecimal relId;
    private BigDecimal taskId;
    private BigDecimal relTaskId;
    private BigDecimal creater;
    private Integer state;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp effDate;
    private Timestamp expDate;
}