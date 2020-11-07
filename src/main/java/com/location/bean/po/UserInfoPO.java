package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @ClassName UserInfoPO
 * @Description: TODO
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class UserInfoPO {
    private BigDecimal userId;
    private BigDecimal billId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userEmail;
    private Integer state;
    private BigDecimal doneCode;
    private Timestamp doneDate;
    private Timestamp effDate;
    private Timestamp expDate;
}