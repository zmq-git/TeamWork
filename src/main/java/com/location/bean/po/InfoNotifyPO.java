package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName InfoNotifyPO
 * @Description: 消息通知表
 * @Author yanjh
 * @Date 2019/12/18 0018上午 9:24
 * @Version V1.0
 **/
@Data
public class InfoNotifyPO {
    private BigDecimal id;
    private Integer infoType;
    private String subject;
    private BigDecimal applicant;
    private BigDecimal auditor;
    private Timestamp applyTime;
    private Timestamp approvalTime;
    private Integer state;
    private BigDecimal userId;
    private String url;
    private Integer dealState;
}
