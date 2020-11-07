package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by shijian on 2019/10/16.
 */

@Data
public class SecOrgRelatDO {
    private Long id;
    private Long orgId;
    private Long relatParentOrgId;
    private String description;
    private Integer status;
    private Integer opId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String ext;
    private String ext1;
}
