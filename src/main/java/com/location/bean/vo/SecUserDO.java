package com.location.bean.vo;

import lombok.Data;


@Data
public class SecUserDO {

    private Integer id;
    private Integer domainId;
    private Integer staffId;
    private String username;
    private String password;
    private Integer isAdmin;
    private Integer creator;
    private java.sql.Timestamp createTime;
    private Integer modifier;
    private java.sql.Timestamp updateTime;
    private byte status = 1;

}
