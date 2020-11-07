package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName ProjectInterationRelPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class ProjectInterationRelPO {
    private BigDecimal relId;
    private BigDecimal projectId;
    private BigDecimal interationId;
    private String userName;
    private Integer interationPeriod;
    private Integer state;
    private BigDecimal interationType;
    private BigDecimal doneCode;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp startDate;
    private Timestamp endDate;
}