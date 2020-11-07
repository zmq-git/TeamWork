package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecPermissionEntityEtlDO {
    private Integer id;
    private Integer permissionId;
    private Integer entityId;
    private Integer entityType;
    private String description;
    private Integer creator;
    private Timestamp createTime;
    private Integer modifier;
    private Timestamp updateTime;
    private Integer status;
}
