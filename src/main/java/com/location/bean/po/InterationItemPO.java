package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName InterationItemPO
 * @Description: 迭代单元表
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class InterationItemPO {
    private BigDecimal interationId;
    private String interationName;
    private String interationDesc;
    private BigDecimal creater;
    private String interationPicture;
    private BigDecimal doneCode;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp startDate;
    private Timestamp endDate;
}