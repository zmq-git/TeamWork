package com.location.bean.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ${Sunjt} on 2019/9/18.
 */
@Data
public class SecMenuDO {
    private Integer id;
    private Integer parentId;
    private Integer menuLevel;
    private Integer isLeaf;
    private String menuName;
    private String menuLink;
    private String menuIcon;
    private String description;
    private Integer creator;
    private Timestamp createTime;
    private Integer modifier;
    private Timestamp updateTime;
    private Integer status;
    private List<SecMenuDO> secMenuDOS;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SecMenuDO other = (SecMenuDO) obj;
        if (other.id == null) {
            return false;
        }
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
