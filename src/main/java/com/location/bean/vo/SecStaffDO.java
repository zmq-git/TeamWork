package com.location.bean.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class SecStaffDO {

    private Integer id;
    private String staffName;
    private String portraitAddress;
    private String phoneNum;
    private String address;
    private Integer orgId;
    private Integer creator;
    private Timestamp createTime;
    private Integer modifier;
    private Timestamp updateTime;
    private byte status = 1;
    private String mail;

}
