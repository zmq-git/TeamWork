package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by shijian on 2019/10/29.
 */
@Data
public class SecOrgRoleRelDO {
    private Long id;
    private Long orgId;
    private Long roleId;
    private Integer opId;
    private Integer status = 1; //默认生效
    private Timestamp createTime;
    private Timestamp updateTime;
    private String ext;
    private String ext1;
}
