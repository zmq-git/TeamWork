package com.location.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @ClassName UserStaffVO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/12/9
 * @Version V1.0
 **/
@Data
@ApiModel("成员用户名ID")
public class UserStaffVO {
    private BigDecimal userId;
    private String userName;
    private Integer orgId;
    private String orgName;
    private String phoneNum;
    private String mail;
    private String roleName;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Timestamp createDate;
    private Map roleNameList;
}