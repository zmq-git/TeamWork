package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName ProjectInfoPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class ProjectInfoPO {
    private BigDecimal projectId;
    private String projectName;
    private String projectDetail;
    private String projectPicture;
    private Integer projectPeriod;
    private Integer state;
    private Integer projectType;
    private BigDecimal doneCode;
    private String creater;
    private BigDecimal userId;
    private Timestamp createDate;
    private Timestamp doneDate;
    private Timestamp startDate;
    private Timestamp endDate;

}