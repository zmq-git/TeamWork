package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by shijian on 2019/10/18.
 */
@Data
public class SecMenusDO {
    private Long id;
    private Integer menuType;
    private String menuName;
    private Long parentId;
    private Long businessId;
    private Integer menuLevel;
    private String menuLink;
    private String menuIcon;
    private String description;
    private Integer opId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    private String ext;
    private String ext1;
    private List<SecMenusDO> secMenuDOS;
}
