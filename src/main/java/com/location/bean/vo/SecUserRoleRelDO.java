package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecUserRoleRelDO {
    private Long id;
    private Long userId;
    private Long roleId;
    private Integer opId;
    private Integer status;
/*    private Integer status = 1; //默认生效*/
    private Timestamp createTime;
    private Timestamp updateTime;
    private String ext;
    private String ext1;
}
