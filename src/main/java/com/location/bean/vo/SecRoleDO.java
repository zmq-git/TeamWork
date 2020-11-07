package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecRoleDO {
    private Long id;
    private Integer roleType;
    private String roleName;
    private Integer roleLevel;
    private String description;
    private Integer opId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    private String ext;
    private String ext1;
}
