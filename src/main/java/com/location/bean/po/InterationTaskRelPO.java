package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName InterationTaskRelPO
 * @Description: 迭代和任务关联表
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class InterationTaskRelPO {
    private BigDecimal relId;
    private BigDecimal interationId;
    private BigDecimal taskId;
    private BigDecimal creater;
    private BigDecimal doneCode;
    private Integer state;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp effDate;
    private Timestamp expDate;
}