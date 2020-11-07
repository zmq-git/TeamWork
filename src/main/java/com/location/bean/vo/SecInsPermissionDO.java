package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by shijian on 2019/10/17.
 */
@Data
public class SecInsPermissionDO {
    private Long id;
    private Long roleId;
    private Long menuId;
    private String description;
    private Integer opId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
}
