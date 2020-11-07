package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName UserProjectRelPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/7
 * @Version V1.0
 **/
@Data
public class UserProjectRelPO {
    private BigDecimal relId;
    private BigDecimal userId;
    private BigDecimal projectId;
    private Integer state;
    private Integer projectRole;
    private Timestamp createDate;
    private BigDecimal creater;
    private Timestamp doneDate;
    private BigDecimal doneCode;
    private Timestamp effDate;
    private Timestamp expDate;
}