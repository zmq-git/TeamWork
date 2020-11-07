package com.location.bean.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class SecOrgDO {

    private Long id;
    private Long orgId;
    private String orgName;
    private String description;
    private Integer opId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    private String ext;
    private String ext1;
}
