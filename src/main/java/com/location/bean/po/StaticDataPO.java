package com.location.bean.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName StaticDataPO
 * @Description: 静态配置表
 * @Author wanggb
 * @Date 2019/11/12
 * @Version V1.0
 **/
@Data
public class StaticDataPO {
    private BigDecimal id;
    private String codeType;
    private String codeValue;
    private String codeDesc;
    private Integer state;
    private BigDecimal praentId;
    private BigDecimal childId;
    private String extendCodeType;
}