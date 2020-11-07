package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecRolePremissionDO {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private Integer creator;
    private Timestamp createTime;
    private Integer modifier;
    private Timestamp updateTime;
    private Integer status;
}
